package vander.gabriel.pedrapapeltesoura.models

import vander.gabriel.pedrapapeltesoura.models.enums.GameResult

data class RoundResult(
    val result: GameResult,
    val winner: Player?,
)
