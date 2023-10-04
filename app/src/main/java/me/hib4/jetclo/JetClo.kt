package me.hib4.jetclo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import me.hib4.jetclo.ui.theme.ForceBlue
import me.hib4.jetclo.ui.theme.Grey
import me.hib4.jetclo.ui.theme.RedOrange
import me.hib4.jetclo.ui.theme.White
import java.util.Calendar
import java.util.Date
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun JetCloEx(
    modifier: Modifier = Modifier,
    circleRadius: Float = 300f,
    outerCircleThickness: Float = 50f,
    time: () -> Long
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier.size(500.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(width / 2f, height / 2f)

            val calendar = Calendar.getInstance()
            val date = Date(time())
            calendar.time = date
            val seconds = calendar.get(Calendar.SECOND)
            val minutes = calendar.get(Calendar.MINUTE)
            val hours = calendar.get(Calendar.HOUR_OF_DAY)

            drawCircle(
                center = circleCenter,
                brush = Brush.linearGradient(
                    listOf(
                        White.copy(alpha = 0.45f),
                        ForceBlue.copy(alpha = 0.35f)
                    )
                ),
                radius = circleRadius + outerCircleThickness / 2f,
                style = Stroke(width = outerCircleThickness),
            )
            drawCircle(
                center = circleCenter,
                brush = Brush.radialGradient(
                    listOf(
                        White.copy(alpha = 0.75f),
                        ForceBlue.copy(alpha = 0.25f)
                    )
                ),
                radius = circleRadius,
            )

            val littleLineLength = circleRadius * 0.1f
            val largeLineLength = circleRadius * 0.2f

            for (i in 0 until 60) {
                val angleInDegrees = i * 360f / 60f
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f
                val lineLength = if (i % 5 == 0) largeLineLength else littleLineLength
                val lineThickness = if (i % 5 == 0) 5f else 2f

                val start = Offset(
                    x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angleInRad) + circleCenter.y).toFloat()
                )
                val end = Offset(
                    x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angleInRad) + lineLength + circleCenter.y).toFloat()
                )
                rotate(
                    degrees = angleInDegrees + 180,
                    pivot = start
                ) {
                    drawLine(
                        color = Grey,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }
            }

            val clockTimes = listOf(ClockTimes.Minutes, ClockTimes.Hours, ClockTimes.Seconds)

            clockTimes.forEach { clockTime ->
                val angleInDegrees = when (clockTime) {
                    ClockTimes.Seconds -> {
                        seconds * 360f / 60f
                    }

                    ClockTimes.Minutes -> {
                        (minutes + seconds / 60f) * 360f / 60f
                    }

                    ClockTimes.Hours -> {
                        (((hours % 12) / 12f * 60f) + minutes / 12f) * 360f / 60f
                    }
                }
                val lineLength = when (clockTime) {
                    ClockTimes.Seconds -> {
                        circleRadius * 0.8f
                    }

                    ClockTimes.Minutes -> {
                        circleRadius * 0.7f
                    }

                    ClockTimes.Hours -> {
                        circleRadius * 0.55f
                    }
                }
                val lineThickness = when (clockTime) {
                    ClockTimes.Seconds -> {
                        3f
                    }

                    ClockTimes.Minutes -> {
                        7f
                    }

                    ClockTimes.Hours -> {
                        8f
                    }
                }
                val start = Offset(
                    x = circleCenter.x,
                    y = circleCenter.y
                )
                val end = Offset(
                    x = circleCenter.x,
                    y = circleCenter.y + lineLength
                )
                rotate(
                    degrees = angleInDegrees - 180,
                    pivot = start
                ) {
                    drawLine(
                        color = if (clockTime == ClockTimes.Seconds) RedOrange else Grey,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }
            }

            drawCircle(
                color = Grey,
                center = circleCenter,
                radius = 15f
            )
        }
    }
}

enum class ClockTimes {
    Seconds, Minutes, Hours
}