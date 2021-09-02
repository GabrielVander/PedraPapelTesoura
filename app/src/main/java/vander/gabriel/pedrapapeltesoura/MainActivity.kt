package vander.gabriel.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus
import vander.gabriel.pedrapapeltesoura.ui.theme.PedraPapelTesouraTheme

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
    private fun MyApp() {
        var matchStatus: MatchStatus by remember { mutableStateOf(MatchStatus.NOT_STARTED) }
        var numberOfPlayers by remember { mutableStateOf(2) }

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
                if (matchStatus == MatchStatus.NOT_STARTED) {
                    Text(text = "How many players?", fontSize = 34.sp)
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            matchStatus = MatchStatus.ONGOING
                        }) {
                            Text(text = "2 Players")
                        }
                        Button(onClick = {
                            numberOfPlayers = 3
                            matchStatus = MatchStatus.ONGOING
                        }) {
                            Text(text = "3 Players")
                        }
                    }
                } else if (matchStatus == MatchStatus.ONGOING) {
                    Text(text = "Choose a hand", fontSize = 34.sp)
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HandOption(
                            imageResourceId = R.drawable.rock_paper_scissors_rock,
                            text = "Rock"
                        )
                        HandOption(
                            imageResourceId = R.drawable.rock_paper_scissors_paper,
                            text = "Paper"
                        )
                        HandOption(
                            imageResourceId = R.drawable.rock_paper_scissors_scissors,
                            text = "Scissors"
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun HandOption(imageResourceId: Int, text: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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
