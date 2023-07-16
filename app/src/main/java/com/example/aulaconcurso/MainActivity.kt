package com.example.aulaconcurso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aulaconcurso.ui.theme.AulaConcursoTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AulaConcursoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var fimDeJogo by remember {
        mutableStateOf(false)
    }
    var vitoriasEquipe1 by remember {
        mutableStateOf(0)
    }
    var vitoriasEquipe2 by remember {
        mutableStateOf(0)
    }
    var pontosEquipe1 by remember {
        mutableStateOf(0)
    }
    var pontosEquipe2 by remember {
        mutableStateOf(0)
    }
    val adicionarPontos: (Int, Int) -> Unit = { equipe, pontos ->
        if (equipe == 0) {
            pontosEquipe1 += pontos
            if (pontosEquipe1 >= 12) {
                fimDeJogo = true
                vitoriasEquipe1++
            }
        } else {
            pontosEquipe2 += pontos
            if (pontosEquipe2 >= 12) {
                fimDeJogo = true
                vitoriasEquipe2++
            }
        }
    }

    val zerarVitorias: () -> Unit = {
        pontosEquipe1 = 0
        pontosEquipe2 = 0
        vitoriasEquipe1 = 0
        vitoriasEquipe2 = 0
        fimDeJogo = false
    }

    val zerarPlacar: () -> Unit = {
        pontosEquipe1 = 0
        pontosEquipe2 = 0
        fimDeJogo = false
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Truco App",
            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold)
        )
        Column() {
            Equipes()
            Spacer(modifier = Modifier.height(32.dp))
            Placar(equipe1 = pontosEquipe1, equipe2 = pontosEquipe2)
        }
        Column(modifier = Modifier.padding(12.dp)) {
            Pontuar(pontos = 1, adicionarPontos = adicionarPontos, fimDeJogo = fimDeJogo)
            Pontuar(pontos = 3, adicionarPontos = adicionarPontos, fimDeJogo = fimDeJogo)
            Pontuar(pontos = 6, adicionarPontos = adicionarPontos, fimDeJogo = fimDeJogo)
            Pontuar(pontos = 9, adicionarPontos = adicionarPontos, fimDeJogo = fimDeJogo)
            Pontuar(pontos = 12, adicionarPontos = adicionarPontos, fimDeJogo = fimDeJogo)
        }
        NumeroVitorias(vitoriasEquipe1 = vitoriasEquipe1, vitoriasEquipe2 = vitoriasEquipe2)
        ReiniciarJogo(zerarVitorias = zerarVitorias, zerarPlacar = zerarPlacar)
    }
}

@Composable
fun Equipes() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "Nós", style = MaterialTheme.typography.displayMedium)
        Text(text = "Eles", style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun Placar(equipe1: Int = 0, equipe2: Int = 0) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = equipe1.toString(), style = MaterialTheme.typography.displayMedium)
        Text(text = equipe2.toString(), style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun Pontuar(
    pontos: Int,
    fimDeJogo: Boolean,
    adicionarPontos: (Int, Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(enabled = !fimDeJogo, onClick = { adicionarPontos(0, pontos) }) {
                Text(text = "Nosso")
            }
            val texto = when (pontos) {
                1 -> "Rodada simples"
                3 -> "Truco"
                6 -> "Seis"
                9 -> "Nove"
                else -> "Doze"
            }
            Text(text = texto)
            Button(enabled = !fimDeJogo, onClick = { adicionarPontos(1, pontos) }) {
                Text(text = "Deles")
            }
        }
    }
}



@Composable
fun NumeroVitorias(
    vitoriasEquipe1: Int = 0,
    vitoriasEquipe2: Int = 0
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Vitórias",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Nós = $vitoriasEquipe1", style = MaterialTheme.typography.titleLarge)
            Text(text = "Eles = $vitoriasEquipe2", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun ReiniciarJogo(
    zerarVitorias: () -> Unit,
    zerarPlacar: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = zerarVitorias) {
                Text(text = "Zerar Vitórias")
            }
            Button(onClick = zerarPlacar) {
                Text(text = "Zerar Placar")
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AulaConcursoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
}