/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.timer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.cream
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Preview("Progress25", widthDp = 60, heightDp = 60)
@Composable()
fun Progress25() {
    MyTheme(false) {
        AnimatedCircle(percentage = MutableLiveData(0.25f)) {
        }
    }
}

@Preview("Progress50", widthDp = 60, heightDp = 60)
@Composable()
fun Progress50() {
    MyTheme(false) {
        AnimatedCircle(percentage = MutableLiveData(0.5f)) {
        }
    }
}

@Preview("Progress100", widthDp = 60, heightDp = 60)
@Composable()
fun Progress100() {
    MyTheme(false) {
        AnimatedCircle(percentage = MutableLiveData(1f)) {
        }
    }
}

@Composable
fun AnimatedCircle(
    modifier: Modifier = Modifier,
    percentage: LiveData<Float>,
    reset: () -> Unit
) {
    val color = remember { Animatable(initialValue = 0f) }
    val progressState = percentage.observeAsState()
    val progressValue = progressState.value ?: 0f
    val progress100 = mutableStateOf(progressValue == 1f)
    LaunchedEffect(progress100) {
        if (progressState.value == 1f) {
            color.animateTo(
                targetValue = 1f,
                animationSpec = repeatable(
                    100,
                    keyframes<Float> {
                        durationMillis = 600
                        0.0f at 0
                        0.4f at 100
                        0.6f at 200
                        0.9f at 300
                        1f at 600
                    },
                    RepeatMode.Reverse
                )
            )
        }
    }
    val stroke = with(LocalDensity.current) { Stroke(5.dp.toPx()) }
    val primary = MaterialTheme.colors.primary
    val onPrimary = MaterialTheme.colors.onPrimary
    Canvas(
        modifier.clickable {
            reset()
            GlobalScope.launch {

                color.stop()
                color.snapTo(0f)
            }
        }
    ) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        val startAngle = -90.0f + 360.0f
        drawCircle(cream, size.width / 2, alpha = 0.4f)
        drawCircle(primary, size.width / 2, style = stroke)
        drawCircle(
            color = primary,
            radius = (size.width / 2).times(color.value),
            alpha = color.value.times(0.7f)
        )
        drawArc(
            color = primary,
            startAngle = startAngle,
            sweepAngle = progressValue.times(360.0f),
            topLeft = topLeft,
            size = size,
            useCenter = false,
            style = stroke
        )

        val radian = Math.toRadians((progressValue.times(360) - 90).toDouble())
        val x = center.x + size.width / 2 * cos(radian)
        val y = center.y + size.width / 2 * sin(radian)
        if (!progress100.value) {
            drawCircle(onPrimary, 10f, Offset(x.toFloat(), y.toFloat()))
        }
    }
}
