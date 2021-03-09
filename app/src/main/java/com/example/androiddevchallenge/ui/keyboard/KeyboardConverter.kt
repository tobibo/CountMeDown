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

import org.joda.time.Duration
import java.util.concurrent.TimeUnit

class KeyboardConverter {
    companion object {
        const val SECONDS_DIVIDER = 60
        const val SECONDS_MULTIPLIER = 10
        const val MINUTES_DIVIDER = 60
        const val MINUTES_MULTIPLIER = 10
        const val HOURS_MULTIPLIER = 10

        fun transformTimeToDigits(localTime: Duration): List<Int> {
            return mutableListOf<Int>().apply {
                add(0, (localTime.standardSeconds % SECONDS_MULTIPLIER).toInt())
                add(1, (localTime.standardSeconds / SECONDS_MULTIPLIER).toInt())
                add(2, (localTime.standardMinutes % MINUTES_MULTIPLIER).toInt())
                add(3, (localTime.standardMinutes / MINUTES_MULTIPLIER).toInt())
                add(4, (localTime.standardHours % HOURS_MULTIPLIER).toInt())
                add(5, (localTime.standardHours / HOURS_MULTIPLIER).toInt())
            }.toList()
        }

        fun getTime(digits: MutableList<Int>): Duration {
            val smallerSeconds = digits[0]
            val biggerSeconds = digits[1] * SECONDS_MULTIPLIER

            val seconds = biggerSeconds + smallerSeconds

            val smallerMinutes = digits[2]
            val biggerMinutes = digits[3] * MINUTES_MULTIPLIER
            val minutes = biggerMinutes + smallerMinutes

            val smallerHours = digits[4]
            val biggerHours = digits[5] * HOURS_MULTIPLIER
            val hours = biggerHours + smallerHours

            var duration = Duration(0).plus(TimeUnit.SECONDS.toMillis(seconds.toLong()))
                .plus(TimeUnit.MINUTES.toMillis(minutes.toLong()))
                .plus(TimeUnit.HOURS.toMillis(hours.toLong()))

            return duration
        }

        fun getTimeInMilliseconds(currentTime: Duration): Long {
            return currentTime.millis
        }
    }
}
