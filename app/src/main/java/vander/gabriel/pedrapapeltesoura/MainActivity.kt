package vander.gabriel.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import vander.gabriel.pedrapapeltesoura.presentation.ui.components.MatchScreen
import vander.gabriel.pedrapapeltesoura.presentation.ui.theme.PedraPapelTesouraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PedraPapelTesouraTheme {
                MyApp()
            }
        }
    }

    @Composable
    private fun MyApp() {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Rock Paper Scissors") },
            )
        }) {
            MatchScreen()
        }
    }

//    @Composable
//    private fun NumberOfPlayersSelection(matchViewModel: MatchViewModel) {
//        Text(text = "How many players?", fontSize = 34.sp)
//        Spacer(modifier = Modifier.size(10.dp))
//        Row(
//            horizontalArrangement = Arrangement.SpaceAround,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Button(onClick = {
//            }) {
//                Text(text = "2 Players")
//            }
//            Button(onClick = {
//            }) {
//                Text(text = "3 Players")
//            }
//        }
//    }
}
