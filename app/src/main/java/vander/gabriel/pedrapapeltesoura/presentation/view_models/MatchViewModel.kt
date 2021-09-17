package vander.gabriel.pedrapapeltesoura.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vander.gabriel.pedrapapeltesoura.models.MatchResult
import vander.gabriel.pedrapapeltesoura.models.Player
import vander.gabriel.pedrapapeltesoura.models.Round
import vander.gabriel.pedrapapeltesoura.models.RoundResult
import vander.gabriel.pedrapapeltesoura.models.enums.GameResult
import vander.gabriel.pedrapapeltesoura.models.enums.Hand
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus
import kotlin.random.Random

typealias FinishedRounds = LinkedHashMap<Round, RoundResult>

class MatchViewModel : ViewModel() {
    private val humanPlayer = Player(isHuman = true)
    private var currentRoundModel: Round = Round(index = 1)
    private var finishedRoundsModel: FinishedRounds = LinkedHashMap()
    private var numberOfRounds: Int = 1
    private var numberOfPlayers: Int = 2

    private val _finishedRounds: MutableLiveData<FinishedRounds> = MutableLiveData(LinkedHashMap())
    private var _players: MutableList<Player> = mutableListOf(humanPlayer)
    private val _matchStatus: MutableLiveData<MatchStatus> =
        MutableLiveData(MatchStatus.NOT_STARTED)
    private val _currentRound: MutableLiveData<Round> = MutableLiveData(currentRoundModel)

    val matchStatus: LiveData<MatchStatus>
        get() = _matchStatus
    val currentRound: LiveData<Round>
        get() = _currentRound
    val finishedRounds: LiveData<FinishedRounds>
        get() = _finishedRounds

    fun onStartMatch(numberOfPlayers: Int, numberOfRounds: Int) {
        this.numberOfRounds = numberOfRounds
        this.numberOfPlayers = numberOfPlayers
        setupAiPlayers()
        _matchStatus.value = MatchStatus.ONGOING
    }

    fun onHumanPlayerHandSelection(hand: Hand) {
        currentRoundModel.addHand(humanPlayer, hand)
        _players.filter { !it.isHuman }
            .forEach { currentRoundModel.addHand(it, getPseudoRandomHand()) }
        finishRound(currentRoundModel)
        _currentRound.postValue(currentRoundModel)
    }

    fun onNextRound() {
        currentRoundModel = Round(index = currentRoundModel.index + 1)
        _currentRound.postValue(currentRoundModel)
    }

    fun onFinishMatch() {
        if (currentRoundModel.index == this.numberOfRounds) {
            _matchStatus.value = MatchStatus.FINISHED
        }
    }

    fun onRematch() {
        resetRounds()
        resetPlayers()

        onStartMatch(this.numberOfPlayers, this.numberOfRounds)
    }

    fun getMatchResult(): MatchResult {
        val winStreakThreshold = numberOfRounds.toFloat() / numberOfPlayers.toFloat()
        val results = finishedRoundsModel.values
        val roundsThatEndedInDraw = results.filter { it.result == GameResult.DRAW }
        val roundsWithWinner = results.filter { it.result == GameResult.WINNER }
        val playersWinStreak: List<Pair<Player, Int>> = _players.map { player ->
            Pair(
                player,
                roundsWithWinner.filter { round -> round.winner == player }.size
            )
        }

        val wasAnOverallDraw = roundsThatEndedInDraw.size >= winStreakThreshold
        val noClearWinner = playersWinStreak.all { pair -> pair.second <= winStreakThreshold }
        val winner = playersWinStreak.maxByOrNull { it.second }

        return when {
            wasAnOverallDraw || noClearWinner -> {
                MatchResult(
                    result = GameResult.DRAW
                )
            }
            else -> {
                MatchResult(
                    result = GameResult.WINNER,
                    winner = winner!!.first
                )
            }
        }
    }

    private fun setupAiPlayers() {
        for (i in (1 until this.numberOfPlayers)) {
            val aiPlayer = Player(playerName = "Computer $i")
            _players.add(aiPlayer)
        }
    }

    private fun getPseudoRandomHand(): Hand {
        val until = Hand.values().size
        val randomIndex = Random.nextInt(until)

        return Hand.values()[randomIndex]
    }

    private fun finishRound(round: Round) {
        round.hasFinished = true
        finishedRoundsModel[round] = round.getRoundResult()
        _finishedRounds.value = finishedRoundsModel
    }

    private fun resetRounds() {
        resetCurrentRound()
        resetFinishedRounds()
    }

    private fun resetCurrentRound() {
        currentRoundModel = Round(index = 1)
        _currentRound.value = Round(index = 1)
    }

    private fun resetFinishedRounds() {
        finishedRoundsModel = LinkedHashMap()
        _finishedRounds.value = finishedRoundsModel
    }

    private fun resetPlayers() {
        _players = mutableListOf(humanPlayer)
    }
}