package vander.gabriel.pedrapapeltesoura.models

import vander.gabriel.pedrapapeltesoura.models.enums.GameResult
import vander.gabriel.pedrapapeltesoura.models.enums.Hand

data class Round(
    val index: Int,
    val history: LinkedHashMap<Player, Hand> = LinkedHashMap(),
) {
    var hasFinished: Boolean = false

    fun addHand(player: Player, hand: Hand) {
        this.history[player] = hand
    }

    fun getRoundResult(): RoundResult {
        val cyclicDraw = checkForCyclicDraw()
        val roundWinner: Player? = history.entries.find { playerWon(it.key, it.value) }?.key

        val gameResult = when {
            cyclicDraw -> GameResult.DRAW
            roundWinner != null -> GameResult.WINNER
            else -> GameResult.DRAW
        }

        return RoundResult(gameResult, winner = roundWinner)
    }

    private fun checkForCyclicDraw(): Boolean {
        val allHands: List<Hand> = this.history.toList().map { it.second }

        return allHands.containsAll(Hand.values().toList())
    }

    private fun playerWon(player: Player, hand: Hand): Boolean {
        val opponentsHands =
            this.history.filterNot { entry -> entry.key == player }.map { it.value }.toList()
        return opponentsHands.all { it == hand.counters() }
    }

}

