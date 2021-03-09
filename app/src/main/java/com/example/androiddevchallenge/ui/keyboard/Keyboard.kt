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
package com.example.androiddevchallenge.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.cream

@Preview("Key")
@Composable
fun Key() {
    MyTheme(false) {
        KeyboardKey(key = 1, onClicked = { })
    }
}

@Composable
fun KeyboardKey(key: Int, onClicked: (Int) -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.padding(6.dp)) {
        Button(onClick = { onClicked(key) }) {
            Text(text = "$key", style = MaterialTheme.typography.h4)
        }
    }
}

@Preview
@Composable
fun KeyboardWithDisplayPreview() {
    MyTheme() {
        KeyboardWithDisplay({})
    }
}

@Composable
fun KeyboardWithDisplay(onUpdate: (MutableList<Int>) -> Unit) {
    var register = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    val updateTime: () -> Unit = {

        onUpdate(register)
    }
    var currentDigit by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KeyboardDisplay(register)
        Keyboard(
            true,
            {
                if (currentDigit < 6) {
                    register
                    register.add(0, it)
                    updateTime()
                    currentDigit += 1
                }
            },
            {
                if (currentDigit > 0) {
                    register.removeAt(0)
                    updateTime()
                    currentDigit -= 1
                }
            }
        )
    }
}

@Preview("KeyboardDisplay")
@Composable
fun KeyboardDisplayPreview() {
    MyTheme(false) {
        KeyboardDisplay(mutableListOf(0, 1, 60, 20, 20, 10))
    }
}

@Composable
fun KeyboardDisplay(digits: List<Int>, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {

        Row(
            modifier = Modifier
                .background(cream)
                .clip(RoundedCornerShape(5.dp))
                .padding(start = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = digits[5].toString(),
                modifier.padding(5.dp),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = digits[4].toString(),
                modifier.padding(5.dp),
                style = MaterialTheme.typography.h3
            )
            Text(":", modifier, style = MaterialTheme.typography.h3)
            Text(
                text = digits[3].toString(),
                modifier.padding(5.dp),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = digits[2].toString(),
                modifier.padding(5.dp),
                style = MaterialTheme.typography.h3
            )
            Text(":", modifier, style = MaterialTheme.typography.h3)
            Text(
                text = digits[1].toString(),
                modifier.padding(5.dp),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = digits[0].toString(),
                modifier.padding(5.dp),
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun Keyboard(
    isVisible: Boolean,
    onDigitClicked: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(
            start = 0.dp,
            end = 0.dp,
            bottom = 0.dp,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                KeyboardKey(1, onDigitClicked)
                KeyboardKey(2, onDigitClicked)
                KeyboardKey(3, onDigitClicked)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                KeyboardKey(4, onDigitClicked)
                KeyboardKey(5, onDigitClicked)
                KeyboardKey(6, onDigitClicked)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                KeyboardKey(7, onDigitClicked)
                KeyboardKey(8, onDigitClicked)
                KeyboardKey(9, onDigitClicked)
            }
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                Button(onClick = onBack, Modifier.padding(6.dp)) {
                    Text("C", style = MaterialTheme.typography.h4)
                }
                KeyboardKey(0, onDigitClicked)

                KeyboardKey(0, onDigitClicked, modifier = Modifier.alpha(1f))
            }
        }
    }
}

@Preview
@Composable
fun NumericKeyboardPreview() {
    Keyboard(
        isVisible = true,
        onDigitClicked = {},
        onBack = {}
    )
}
