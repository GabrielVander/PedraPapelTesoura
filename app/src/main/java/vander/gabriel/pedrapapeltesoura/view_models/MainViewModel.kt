package vander.gabriel.pedrapapeltesoura.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vander.gabriel.pedrapapeltesoura.models.MatchResult
import vander.gabriel.pedrapapeltesoura.models.RockPaperScissorsMatch
import vander.gabriel.pedrapapeltesoura.models.enums.Hand
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus

class MainViewModel : ViewModel() {
    private val _match: MutableLiveData<RockPaperScissorsMatch> =
        MutableLiveData(RockPaperScissorsMatch(numberOfPlayers = null, humanPlayerHand = null))

    val matchStatus: MutableLiveData<MatchStatus> = MutableLiveData(_match.value!!.status)
    val matchResult: MutableLiveData<MatchResult> = MutableLiveData<MatchResult>()

    fun onPlayerCountSelection(playerNumber: Int) {
        _match.value!!.numberOfPlayers = playerNumber
        matchStatus.postValue(MatchStatus.ONGOING)
    }

    fun onHumanPlayerHandSelection(hand: Hand) {
        _match.value!!.humanPlayerHand = hand
        matchResult.postValue(_match.value!!.play())
        matchStatus.postValue(MatchStatus.FINISHED)

    }

    fun onRematch() {
        _match.postValue(RockPaperScissorsMatch(numberOfPlayers = null, humanPlayerHand = null))
        matchStatus.postValue(_match.value!!.status)
        matchResult.postValue(null)
    }
}