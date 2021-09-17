package vander.gabriel.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vander.gabriel.pedrapapeltesoura.models.Round
import vander.gabriel.pedrapapeltesoura.models.enums.MatchStatus
import vander.gabriel.pedrapapeltesoura.presentation.ui.components.MatchScreen
import vander.gabriel.pedrapapeltesoura.presentation.ui.components.SettingsScreen
import vander.gabriel.pedrapapeltesoura.presentation.ui.theme.PedraPapelTesouraTheme
import vander.gabriel.pedrapapeltesoura.presentation.view_models.ConfigurationViewModel
import vander.gabriel.pedrapapeltesoura.presentation.view_models.MatchViewModel

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
    private fun MyApp(
        configurationViewModel: ConfigurationViewModel = viewModel(),
        matchViewModel: MatchViewModel = viewModel()
    ) {
        val currentNumberOfPlayers by configurationViewModel.numberOfPlayers.observeAsState(2)
        val currentNumberOfRounds by configurationViewModel.numberOfRounds.observeAsState(1)
        val matchStatus by matchViewModel.matchStatus.observeAsState(MatchStatus.NOT_STARTED)
        val currentRound by matchViewModel.currentRound.observeAsState(Round(index = 1))
        val finishedRounds by matchViewModel.finishedRounds.observeAsState(LinkedHashMap())

        val navController = rememberNavController()

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Rock Paper Scissors") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings button")
                    }
                }
            )
        }) {
            NavHost(navController = navController, startDestination = "main") {

                composable("main") {
                    MatchScreen(
                        matchStatus = matchStatus,
                        currentRound = currentRound,
                        finishedRounds = finishedRounds,
                        numberOfPlayers = currentNumberOfPlayers,
                        numberOfRounds = currentNumberOfRounds,
                        getMatchResult = { matchViewModel.getMatchResult() },
                        onHumanHandSelection = { matchViewModel.onHumanPlayerHandSelection(it) },
                        onNextRound = { matchViewModel.onNextRound() },
                        onMatchFinish = { matchViewModel.onFinishMatch() },
                        onMatchStart = { numberOfPlayers, numberOfRounds ->
                            matchViewModel.onStartMatch(
                                numberOfPlayers,
                                numberOfRounds
                            )
                        },
                        onRematch = { matchViewModel.onRematch() }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        currentNumberOfPlayers = currentNumberOfPlayers,
                        currentNumberOfRounds = currentNumberOfRounds,
                        onNumberOfPlayersChange = {
                            configurationViewModel.onNumberOfPlayersSelected(it)
                        },
                        onNumberOfRoundsChange = {
                            configurationViewModel.onNumberOfRoundsSelected(it)
                        })
                }
            }
        }
    }
}
