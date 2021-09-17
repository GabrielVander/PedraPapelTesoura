package vander.gabriel.pedrapapeltesoura.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigurationViewModel : ViewModel() {
    private val _numberOfPlayers = MutableLiveData(2)
    private val _numberOfRounds = MutableLiveData(1)

    val numberOfPlayers: LiveData<Int>
        get() = _numberOfPlayers

    val numberOfRounds: LiveData<Int>
        get() = _numberOfRounds

    fun onNumberOfPlayersSelected(numberOfPlayers: Int) {
        _numberOfPlayers.postValue(numberOfPlayers)
    }

    fun onNumberOfRoundsSelected(numberOfRounds: Int) {
        _numberOfRounds.postValue(numberOfRounds)
    }
}