package vander.gabriel.pedrapapeltesoura.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    currentNumberOfPlayers: Int,
    currentNumberOfRounds: Int,
    onNumberOfPlayersChange: (Int) -> Unit = {},
    onNumberOfRoundsChange: (Int) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        NumberOfPlayersSelection(
            currentNumberOfPlayers,
            onNumberOfPlayersSelected = onNumberOfPlayersChange
        )
        NumberOfRoundsSelection(
            currentNumberOfRounds,
            onNumberOfRoundsSelected = onNumberOfRoundsChange
        )
    }
}

@Composable
fun NumberOfRoundsSelection(
    currentNumberOfRounds: Int,
    onNumberOfRoundsSelected: (Int) -> Unit = { }
) {
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "How many rounds?", fontSize = 34.sp)
            Text(text = "Currently set to $currentNumberOfRounds")
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onNumberOfRoundsSelected(1) }) {
                    Text(text = "1 Round")
                }
                Button(onClick = { onNumberOfRoundsSelected(3) }) {
                    Text(text = "3 Rounds")
                }
                Button(onClick = { onNumberOfRoundsSelected(5) }) {
                    Text(text = "5 Rounds")
                }
            }
        }
    }

}

@Composable
private fun NumberOfPlayersSelection(
    currentNumberOfPlayers: Int,
    onNumberOfPlayersSelected: (Int) -> Unit = {}
) {
    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "How many players?", fontSize = 34.sp)
            Text(text = "Currently set to $currentNumberOfPlayers")
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onNumberOfPlayersSelected(2) }) {
                    Text(text = "2 Players")
                }
                Button(onClick = { onNumberOfPlayersSelected(3) }) {
                    Text(text = "3 Players")
                }
            }
        }
    }
}
