package vander.gabriel.pedrapapeltesoura.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vander.gabriel.pedrapapeltesoura.R
import vander.gabriel.pedrapapeltesoura.models.MatchResult
import vander.gabriel.pedrapapeltesoura.models.Player
import vander.gabriel.pedrapapeltesoura.models.Round
import vander.gabriel.pedrapapeltesoura.models.RoundResult
import vander.gabriel.pedrapapeltesoura.models.enums.GameResult
import vander.gabriel.pedrapapeltesoura.models.enums.Hand
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus
import vander.gabriel.pedrapapeltesoura.presentation.view_models.ConfigurationViewModel
import vander.gabriel.pedrapapeltesoura.presentation.view_models.FinishedRounds
import vander.gabriel.pedrapapeltesoura.presentation.view_models.MatchViewModel

@Composable
fun MatchScreen(
    matchViewModel: MatchViewModel = viewModel(),
    configurationViewModel: ConfigurationViewModel = viewModel()
) {
    val matchStatus by matchViewModel.matchStatus.observeAsState(MatchStatus.NOT_STARTED)
    val currentRound by matchViewModel.currentRound.observeAsState(Round(index = 1))
    val finishedRounds by matchViewModel.finishedRounds.observeAsState(LinkedHashMap())
    val numberOfPlayers by configurationViewModel.numberOfPlayers.observeAsState(2)
    val numberOfRounds by configurationViewModel.numberOfRounds.observeAsState(1)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        when (matchStatus) {
            MatchStatus.NOT_STARTED -> {
                StartMatch(onStartMatch = {
                    matchViewModel.onStartMatch(
                        numberOfPlayers = numberOfPlayers,
                        numberOfRounds = numberOfRounds
                    )
                })
            }
            MatchStatus.ONGOING -> {
                RoundHandsDisplay(round = currentRound)
                Spacer(modifier = Modifier.size(10.dp))
                if (currentRound.hasFinished && currentRound.index < numberOfRounds) NextRoundButton()
                else if (!currentRound.hasFinished) HumanPlayerHandSelection(onHandSelected = {
                    matchViewModel.onHumanPlayerHandSelection(
                        it
                    )
                })
                else Button(onClick = { matchViewModel.onFinishMatch() }) {
                    Text(text = "Finish match")
                }
            }
            MatchStatus.FINISHED -> {
                MatchResultDisplay(
                    finishedRounds,
                    matchViewModel.getMatchResult(),
                    onRematch = { matchViewModel.onRematch() })
            }
        }
    }
}

@Composable
private fun StartMatch(onStartMatch: () -> Unit = {}) {
    Button(onClick = onStartMatch) {
        Text("Start match")
    }
}

@Composable
private fun RoundHandsDisplay(
    round: Round
) {
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Round ${round.index}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            if (round.hasFinished) RoundResult(round)
        }
    }
}

@Composable
private fun NextRoundButton() {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Next round ->")
    }
}

@Composable
private fun RoundResult(round: Round) {
    val roundResult = round.getRoundResult()

    round.history.forEach { entry ->
        Text(text = "${entry.key.playerName ?: "You"} played ${entry.value}")
    }
    Spacer(modifier = Modifier.size(10.dp))
    RoundOverallResult(roundResult)
}

@Composable
private fun HumanPlayerHandSelection(onHandSelected: (Hand) -> Unit = {}) {
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Choose a hand", fontSize = 34.sp)
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                HandOption(
                    imageResourceId = R.drawable.rock_paper_scissors_rock,
                    text = "Rock",
                    onClick = {
                        onHandSelected(Hand.ROCK)
                    }
                )
                HandOption(
                    imageResourceId = R.drawable.rock_paper_scissors_paper,
                    text = "Paper",
                    onClick = {
                        onHandSelected(Hand.PAPER)
                    }
                )
                HandOption(
                    imageResourceId = R.drawable.rock_paper_scissors_scissors,
                    text = "Scissors",
                    onClick = {
                        onHandSelected(Hand.SCISSORS)
                    }
                )

            }
        }
    }
}

@Composable
private fun HandOption(imageResourceId: Int, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            modifier = Modifier.size(50.dp),
            contentDescription = text
        )
        Text(text = text)
    }
}

@Composable
private fun MatchResultDisplay(
    finishedRounds: FinishedRounds,
    matchResult: MatchResult,
    onRematch: () -> Unit = {}
) {
    val rounds = finishedRounds.keys

    Text(
        text = "Result", fontSize = 48.sp,
        fontWeight = FontWeight.Bold
    )
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            rounds.forEach { round ->
                Text(text = "Round ${round.index}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                RoundOverallResult(finishedRounds[round]!!)
            }
        }
    }
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = getMatchResultText(matchResult),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(15.dp))
            Button(onClick = onRematch) {
                Text(text = "Rematch")
            }
        }
    }
}

@Composable
private fun RoundOverallResult(roundResult: RoundResult) {
    when (roundResult.result) {
        GameResult.DRAW -> Text(
            text = "Draw",
            fontSize = 20.sp
        )
        GameResult.WINNER -> Text(
            text = getWinnerBasedText(roundResult.winner!!),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun getWinnerBasedText(player: Player): String {
    return if (player.isHuman) "You won!" else "${player.playerName} won"
}

private fun getMatchResultText(matchResult: MatchResult): String {
    return if (matchResult.result == GameResult.DRAW) "It was a draw" else getWinnerBasedText(
        matchResult.winner!!
    )
}
