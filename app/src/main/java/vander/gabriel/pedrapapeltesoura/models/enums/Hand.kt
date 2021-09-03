package vander.gabriel.pedrapapeltesoura.models.enums

enum class Hand {
    ROCK {
        override fun counters(): Hand = SCISSORS

        override fun isCounteredBy(): Hand = PAPER
    },
    PAPER {
        override fun counters(): Hand = ROCK

        override fun isCounteredBy(): Hand = SCISSORS
    },
    SCISSORS {
        override fun counters(): Hand = PAPER

        override fun isCounteredBy(): Hand = ROCK
    };

    abstract fun counters(): Hand
    abstract fun isCounteredBy(): Hand
}