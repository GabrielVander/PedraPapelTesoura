package vander.gabriel.pedrapapeltesoura.models

import vander.gabriel.pedrapapeltesoura.models.enums.Hand

data class Player(val hand: Hand, val playerName: String? = null, val isHuman: Boolean = false)