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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.ui.keyboard.KeyboardConverter
import com.example.androiddevchallenge.ui.keyboard.KeyboardWithDisplay
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.time.LocalTime

@Preview("DIGIT InPUT")
@Composable
fun DigitInput() {
    MyTheme(false) {
        AddTimer(appViewModel = AppViewModel(), showTimers = { })
    }
}

@Composable
fun AddTimer(appViewModel: AppViewModel, showTimers: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var currentTime by remember { mutableStateOf(LocalTime.of(0, 0, 0)) }
        Text("Add a timer", style = MaterialTheme.typography.h2)
        KeyboardWithDisplay {
            currentTime = KeyboardConverter.getTime(it)
        }
        Button(
            onClick = {
                appViewModel.addTimer(KeyboardConverter.getTimeInMilliseconds(currentTime))
                showTimers()
            }
        ) {
            Text("Add timer", style = MaterialTheme.typography.h4)
        }
    }
}
