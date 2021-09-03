package vander.gabriel.pedrapapeltesoura.models

import vander.gabriel.pedrapapeltesoura.models.enums.Result

data class MatchResult(
    val result: Result,
    val players: List<Player>,
)
