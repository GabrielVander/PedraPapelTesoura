package vander.gabriel.pedrapapeltesoura.models

import vander.gabriel.pedrapapeltesoura.models.enums.Hand
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus
import vander.gabriel.pedrapapeltesoura.models.enums.Result
import kotlin.random.Random

class RockPaperScissorsMatch(
    var numberOfPlayers: Int?,
    var humanPlayerHand: Hand?,
    var status: MatchStatus = MatchStatus.NOT_STARTED
) {
    private var players: MutableList<Player> = mutableListOf()

    fun play(): MatchResult {
        setupPlayers()

        val result: Result = getResult(humanPlayerHand!!)

        return MatchResult(result = result, players = players)
    }

    private fun setupPlayers() {
        val humanPlayer = Player(humanPlayerHand!!, isHuman = true)

        players.add(humanPlayer)

        for (i in (1 until numberOfPlayers!!)) {
            val aiPlayer = Player(hand = getPseudoRandomHand(), playerName = "Computer $i")
            players.add(aiPlayer)
        }
    }

    private fun getResult(humanPlayerHand: Hand): Result {
        val aiHands: List<Hand> = players.filter { !it.isHuman }.map { it.hand }

        val cyclicDraw = numberOfPlayers == 3 && checkForCyclicDraw(players.map { it.hand })
        val humanCountersAllOpponents = aiHands.all { it == humanPlayerHand.counters() }
        val humanGetsCounteredByAtLeastOneOpponent =
            aiHands.any { it == humanPlayerHand.isCounteredBy() }

        return when {
            cyclicDraw -> Result.DRAW
            humanCountersAllOpponents -> Result.HUMAN_WIN
            humanGetsCounteredByAtLeastOneOpponent -> Result.HUMAN_LOSE
            else -> Result.DRAW
        }
    }

    private fun checkForCyclicDraw(hands: List<Hand>): Boolean {
        return hands.containsAll(Hand.values().toList())
    }

    private fun getPseudoRandomHand(): Hand {
        val until = Hand.values().size
        val randomIndex = Random.nextInt(until)

        return Hand.values()[randomIndex]
    }
}