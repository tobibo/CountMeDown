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

import java.time.LocalTime
import java.util.concurrent.TimeUnit

class KeyboardConverter {
    companion object {
        const val SECONDS_DIVIDER = 60
        const val SECONDS_MULTIPLIER = 10
        const val MINUTES_DIVIDER = 60
        const val MINUTES_MULTIPLIER = 10
        const val HOURS_MULTIPLIER = 10

        fun transformTimeToDigits(localTime: LocalTime): List<Int> {
            return mutableListOf<Int>().apply {
                add(0, localTime.second % SECONDS_MULTIPLIER)
                add(1, localTime.second / SECONDS_MULTIPLIER)
                add(2, localTime.minute % MINUTES_MULTIPLIER)
                add(3, localTime.minute / MINUTES_MULTIPLIER)
                add(4, localTime.hour % HOURS_MULTIPLIER)
                add(5, localTime.hour / HOURS_MULTIPLIER)
            }.toList()
        }

        fun getTime(digits: MutableList<Int>): LocalTime {
            val smallerSeconds = digits[0]
            val biggerSeconds = digits[1] * SECONDS_MULTIPLIER
            val seconds = biggerSeconds + smallerSeconds
            val remainingSeconds = seconds % SECONDS_DIVIDER
            val dividedSeconds = seconds / SECONDS_DIVIDER

            val smallerMinutes = digits[2]
            val biggerMinutes = digits[3] * MINUTES_MULTIPLIER
            val minutes = biggerMinutes + smallerMinutes + dividedSeconds
            val remainingMinutes = minutes % MINUTES_DIVIDER
            val dividedMinutes = minutes / MINUTES_DIVIDER

            val smallerHours = digits[4]
            val biggerHours = digits[5] * HOURS_MULTIPLIER
            val hours = biggerHours + smallerHours + dividedMinutes

            return LocalTime.of(hours, remainingMinutes, remainingSeconds)
        }

        fun getTimeInMilliseconds(currentTime: LocalTime): Long {
            return TimeUnit.HOURS.toMillis(currentTime.hour.toLong()) +
                TimeUnit.MINUTES.toMillis(currentTime.minute.toLong()) +
                TimeUnit.SECONDS.toMillis(currentTime.second.toLong())
        }
    }
}
