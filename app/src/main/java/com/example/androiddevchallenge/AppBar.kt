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
package com.example.androiddevchallenge

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(appViewModel: AppViewModel, showAlert: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "CountMeDown",
                style = MaterialTheme.typography.subtitle2,
                color = LocalContentColor.current
            )
        },
        actions = {
            IconButton(
                onClick = {
                    showAlert()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Timer,
                    contentDescription = "contentDescription",
                    modifier = Modifier.padding(2.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}
