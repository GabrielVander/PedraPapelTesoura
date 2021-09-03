package vander.gabriel.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vander.gabriel.pedrapapeltesoura.models.MatchResult
import vander.gabriel.pedrapapeltesoura.models.enums.Hand
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus
import vander.gabriel.pedrapapeltesoura.models.enums.Result
import vander.gabriel.pedrapapeltesoura.ui.theme.PedraPapelTesouraTheme
import vander.gabriel.pedrapapeltesoura.view_models.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PedraPapelTesouraTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }

    @Composable
    private fun MyApp(mainViewModel: MainViewModel = viewModel()) {
        val matchStatus: MatchStatus by mainViewModel.matchStatus.observeAsState(MatchStatus.NOT_STARTED)
        val matchResult: MatchResult? by mainViewModel.matchResult.observeAsState()

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Rock Paper Scissors") },
            )
        }) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                when (matchStatus) {
                    MatchStatus.NOT_STARTED -> {
                        Text(text = "How many players?", fontSize = 34.sp)
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                mainViewModel.onPlayerCountSelection(2)
                            }) {
                                Text(text = "2 Players")
                            }
                            Button(onClick = {
                                mainViewModel.onPlayerCountSelection(3)
                            }) {
                                Text(text = "3 Players")
                            }
                        }
                    }
                    MatchStatus.ONGOING -> {
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
                                    mainViewModel.onHumanPlayerHandSelection(Hand.ROCK)
                                }
                            )
                            HandOption(
                                imageResourceId = R.drawable.rock_paper_scissors_paper,
                                text = "Paper",
                                onClick = {
                                    mainViewModel.onHumanPlayerHandSelection(Hand.PAPER)

                                }
                            )
                            HandOption(
                                imageResourceId = R.drawable.rock_paper_scissors_scissors,
                                text = "Scissors",
                                onClick = {
                                    mainViewModel.onHumanPlayerHandSelection(Hand.SCISSORS)

                                }
                            )
                        }
                    }
                    MatchStatus.FINISHED -> {
                        val players = matchResult?.players!!
                        Column(Modifier.height(150.dp)) {
                            players.forEach { player ->
                                Text(
                                    text = "${player.playerName ?: "You"} played ${player.hand}",
                                    fontSize = 20.sp
                                )
                            }
                        }
                        Text(text = getResultBasedText(matchResult!!.result), fontSize = 45.sp)
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(onClick = { mainViewModel.onRematch() }) {
                            Text(text = "Rematch")
                        }
                    }
                }
            }
        }
    }

    private fun getResultBasedText(result: Result): String {
        return when (result) {
            Result.HUMAN_WIN -> "You won!"
            Result.HUMAN_LOSE -> "Sorry, you lost!"
            Result.DRAW -> "It was a draw!"
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
}
