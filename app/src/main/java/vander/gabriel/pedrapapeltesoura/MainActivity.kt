package vander.gabriel.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        var inGame by remember { mutableStateOf(false) }
        var numberOfPlayers by remember { mutableStateOf(2) }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Rock Paper Scissors") },
            )
        }) {
            if (!inGame) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "How many players?", fontSize = 25.sp)
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            inGame = true
                        }) {
                            Text(text = "2 Players")
                        }
                        Button(onClick = {
                            numberOfPlayers = 3
                            inGame = true
                        }) {
                            Text(text = "3 Players")
                        }
                    }
                }
            }
        }
    }
}
