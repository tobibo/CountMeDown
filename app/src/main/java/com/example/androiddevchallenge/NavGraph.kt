/*
 * Copyright 2020 The Android Open Source Project
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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.timer.AddTimer
import com.example.androiddevchallenge.ui.timer.Timers

/**
 * Destinations used in the ([OwlApp]).
 */
object MainDestinations {
    const val ADD_TIMER_ROUTE = "add_timmer"
    const val TIMER_LIST_ROUTE = "timmers"
}

@Composable
fun NavGraph(
    appViewModel: AppViewModel,
    startDestination: String = MainDestinations.ADD_TIMER_ROUTE
): MainActions {
    val navController = rememberNavController()

    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.TIMER_LIST_ROUTE) {
            Timers(appViewModel, actions.addTimer)
        }
        composable(MainDestinations.ADD_TIMER_ROUTE) {
            AddTimer(appViewModel, actions.showTimers)
        }
    }
    return actions
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val addTimer: () -> Unit = {
        navController.navigate(MainDestinations.ADD_TIMER_ROUTE)
    }
    val showTimers: () -> Unit = {
        navController.navigate(MainDestinations.TIMER_LIST_ROUTE)
    }
//    val selectCourse: (Long) -> Unit = { courseId: Long ->
//        navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$courseId")
//    }
}
