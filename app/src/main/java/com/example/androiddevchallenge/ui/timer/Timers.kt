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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppBar
import com.example.androiddevchallenge.AppViewModel

@Composable
fun Timers(appViewModel: AppViewModel, addTimer: () -> Unit) {
    val timerValues by appViewModel.timers.observeAsState()
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(appViewModel, addTimer)
            },
            content = {

                timerValues?.let { timers ->
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(timers) { timer ->
                            Timer(
                                timer,
                                cancel = {
                                    appViewModel.removeTimer(timer)
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}
