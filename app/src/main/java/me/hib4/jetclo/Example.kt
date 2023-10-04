package me.hib4.jetclo

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun JetCloExample() {
    var currentTime by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }

    JetClo(
        modifier = Modifier.size(500.dp),
        circleRadius = 300f,
        outerCircleThickness = 50f,
        time = {
            currentTime
        }
    )
}