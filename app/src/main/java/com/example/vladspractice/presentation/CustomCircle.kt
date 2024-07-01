package com.example.vladspractice.presentation

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin



@Composable
fun CustomCircle(
    modifier: Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float,
    onPositionChange: (Int) -> Unit
) {
    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var positionValue by remember { mutableStateOf(initialValue) }
    var changeAngle by remember { mutableStateOf(0f) }
    var dragStartedAngle by remember { mutableStateOf(0f) }
    var oldPositionValue by remember { mutableStateOf(initialValue) }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            dragStartedAngle = -atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat()
                            dragStartedAngle = (dragStartedAngle + 180f).mod(360f)
                        },
                        onDrag = { change, _ ->
                            var touchAngle = -atan2(
                                x = circleCenter.y - change.position.y,
                                y = circleCenter.x - change.position.x
                            ) * (180f / PI).toFloat()
                            touchAngle = (touchAngle + 180f).mod(360f)

                            val currentAngle = oldPositionValue * 360f / (maxValue - minValue)
                            changeAngle = touchAngle - currentAngle

                            val lowerThreshold = currentAngle - (360f / (maxValue - minValue) * 5)
                            val higherThreshold = currentAngle + (360f / (maxValue - minValue) * 5)

                            if (dragStartedAngle in lowerThreshold..higherThreshold) {
                                positionValue = (oldPositionValue + (changeAngle / (360f / (maxValue - minValue))).roundToInt()).coerceIn(minValue, maxValue)
                            }
                        },
                        onDragEnd = {
                            oldPositionValue = positionValue
                            onPositionChange(positionValue)
                        }
                    )
                }
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 35f
            circleCenter = Offset(x = width / 2f, y = height / 2)

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.75f),
                        secondaryColor.copy(0.25f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )

            drawCircle(
                style = Stroke(width = circleThickness),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f / maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f,
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f) / 2f,
                    (height - circleRadius * 2f) / 2f
                )
            )

            val outerRadius = circleRadius + circleThickness / 2f
            val gap = 15f
            for (i in 0..(maxValue - minValue)) {
                val color = if (i < positionValue - minValue) primaryColor else primaryColor.copy(alpha = 0.3f)
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f

                val yGapAdjustment = cos(angleInDegrees * PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegrees * PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val end = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleThickness + circleCenter.y + yGapAdjustment + 5).toFloat()
                )

                rotate(
                    angleInDegrees,
                    pivot = start
                ) {
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 1.5.dp.toPx()
                    )
                }
            }
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "$positionValue%",
                    circleCenter.x,
                    circleCenter.y + 45.dp.toPx() / 3f,
                    Paint().apply {
                        textSize = 38.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        color = Color.White.toArgb()
                        isFakeBoldText = true
                    }
                )
            }
        }
    }
    LaunchedEffect(initialValue) {
        positionValue = initialValue
    }
}

@Preview
@Composable
fun Preview(){
    Box(
        modifier = Modifier.background(Color.White)
    ){
        CustomCircle(
            modifier = Modifier
                .size(250.dp),
            initialValue = 50,
            primaryColor = Color(15,112,255),
            secondaryColor = Color.Gray,
            circleRadius = 230f,
            onPositionChange = {

            }
        )

    }
}









