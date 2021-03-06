package vander.gabriel.pedrapapeltesoura.models

import vander.gabriel.pedrapapeltesoura.models.enums.GameResult

data class MatchResult(
    val result: GameResult,
    val winner: Player? = null,
)
