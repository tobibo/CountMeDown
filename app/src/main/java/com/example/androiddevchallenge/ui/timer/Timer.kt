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

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.animatedVectorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.MyTheme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun Timer(timer: SimpleCountDownTimer, cancel: () -> Unit) {
    val timerStatus = timer.timerStatus.observeAsState()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .requiredHeight(200.dp)
    ) {
        Box(
            Modifier
                .weight(0.2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Cancel,
                contentDescription = "contentDescription",
                modifier = Modifier
                    .size(50.dp)

                    .clickable {
                        cancel()
                    },
                tint = MaterialTheme.colors.primary
            )
        }
        Box(
            Modifier
                .weight(0.6f)
                .fillMaxHeight()

        ) {
            Box(Modifier.wrapContentWidth()) {

                AnimatedCircle(
                    Modifier
                        .height(200.dp)
                        .fillMaxSize(),
                    timer.percentage
                ) {
                    timer?.reset()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TimerDigit(timer.lastDrawnDigits, "hourDigit1", timer.hourDigit1)
                TimerDigit(timer.lastDrawnDigits, "hourDigit0", timer.hourDigit0)

                Text(" : ")
                TimerDigit(timer.lastDrawnDigits, "minuteDigit1", timer.minuteDigit1)
                TimerDigit(timer.lastDrawnDigits, "minuteDigit0", timer.minuteDigit0)

                Text(" : ")
                TimerDigit(timer.lastDrawnDigits, "secondDigit1", timer.secondDigit1)
                TimerDigit(timer.lastDrawnDigits, "secondDigit0", timer.secondDigit0)
            }
        }

        Box(
            Modifier
                .weight(0.2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(targetState = timerStatus.value) { screen ->
                when (screen) {
                    SimpleCountDownTimer.TimerStatus.PAUSED,
                    SimpleCountDownTimer.TimerStatus.STOPPED -> {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "contentDescription",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    if (timer.timerStatus.value == SimpleCountDownTimer.TimerStatus.PAUSED ||
                                        timer.timerStatus.value == SimpleCountDownTimer.TimerStatus.STOPPED
                                    )
                                        timer.start()
                                    else
                                        timer.pause()
                                },
                            tint = MaterialTheme.colors.primary
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Filled.Pause,
                            contentDescription = "contentDescription",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    if (timer.timerStatus.value == SimpleCountDownTimer.TimerStatus.PAUSED ||
                                        timer.timerStatus.value == SimpleCountDownTimer.TimerStatus.STOPPED
                                    )
                                        timer.start()
                                    else
                                        timer.pause()
                                },
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimerDigit(
    lastDrawnDigits: HashMap<String, Int>,
    lastDrawnKey: String,
    digitValue: LiveData<Int>
) {
    var atEnd by remember { mutableStateOf(false) }
    val digit by digitValue.observeAsState()
    val painter = if (lastDrawnDigits[lastDrawnKey] != digit) {
        animatedVectorResource(id = digit.getDigitDrawable()).painterFor(atEnd = atEnd)
    } else {
        painterResource(id = digit.getStaticDigit())
    }
    Icon(
        painter = painter,
        contentDescription = null
    )
    atEnd = true
}

@DrawableRes
private fun Int?.getDigitDrawable() = when (this) {
    0 -> R.drawable.avd_pathmorph_digits_1_to_0
    1 -> R.drawable.avd_pathmorph_digits_2_to_1
    2 -> R.drawable.avd_pathmorph_digits_3_to_2
    3 -> R.drawable.avd_pathmorph_digits_4_to_3
    4 -> R.drawable.avd_pathmorph_digits_5_to_4
    5 -> R.drawable.avd_pathmorph_digits_6_to_5
    6 -> R.drawable.avd_pathmorph_digits_7_to_6
    7 -> R.drawable.avd_pathmorph_digits_8_to_7
    8 -> R.drawable.avd_pathmorph_digits_9_to_8
    9 -> R.drawable.avd_pathmorph_digits_0_to_9
    else -> R.drawable.avd_pathmorph_digits_1_to_0
}

@DrawableRes
private fun Int?.getStaticDigit() = when (this) {
    0 -> R.drawable.vd_pathmorph_digits_zero
    1 -> R.drawable.vd_pathmorph_digits_one
    2 -> R.drawable.vd_pathmorph_digits_two
    3 -> R.drawable.vd_pathmorph_digits_three
    4 -> R.drawable.vd_pathmorph_digits_four
    5 -> R.drawable.vd_pathmorph_digits_five
    6 -> R.drawable.vd_pathmorph_digits_six
    7 -> R.drawable.vd_pathmorph_digits_seven
    8 -> R.drawable.vd_pathmorph_digits_eight
    9 -> R.drawable.vd_pathmorph_digits_nine
    else -> R.drawable.vd_pathmorph_digits_zero
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun TimerPreview() {
    MyTheme(false) {
        Timer(SimpleCountDownTimer(300L), {})
    }
}
