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

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.TimeUnit

class SimpleCountDownTimer(
    var timeCountInMilliSeconds: Long? = null
) {
    val lastDrawnDigits: HashMap<String, Int> = HashMap()

    //  private var email: String
    enum class TimerStatus {
        STARTED, STOPPED, PAUSED, NOT_SET
    }

    private val _hourDigit0 = MutableLiveData<Int>(0)
    private val _hourDigit1 = MutableLiveData<Int>(0)
    private val _minuteDigit0 = MutableLiveData<Int>(0)
    private val _minuteDigit1 = MutableLiveData<Int>(0)
    private val _secondDigit0 = MutableLiveData<Int>(0)
    private val _secondDigit1 = MutableLiveData<Int>(0)

    val hourDigit0: LiveData<Int> = _hourDigit0
    val hourDigit1: LiveData<Int> = _hourDigit1
    val minuteDigit0: LiveData<Int> = _minuteDigit0
    val minuteDigit1: LiveData<Int> = _minuteDigit1
    val secondDigit0: LiveData<Int> = _secondDigit0
    val secondDigit1: LiveData<Int> = _secondDigit1

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    val _percentage = MutableLiveData(-1f)
    val percentage: LiveData<Float> = _percentage
    var pausedTime = 0L

    var timerStatus = MutableLiveData(TimerStatus.STOPPED)

    @OptIn(ExperimentalStdlibApi::class)
    fun updateDigits() {
        if (currentTime.value == null) return
        val milliSeconds = currentTime.value!!
        val (hours, minutes, seconds) = listOf(
            TimeUnit.MILLISECONDS.toHours(milliSeconds),
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )
        val hoursString = hours.toString()
        if (hoursString.length > 1) {
            _hourDigit1.value = hoursString[0].digitToInt()
            _hourDigit0.value = hoursString[1].digitToInt()
        } else {
            _hourDigit0.value = hoursString[0].digitToInt()
            _hourDigit1.value = 0
        }

        val minuteString = minutes.toString()
        if (minuteString.length > 1) {
            _minuteDigit1.value = minuteString[0].digitToInt()
            _minuteDigit0.value = minuteString[1].digitToInt()
        } else {
            _minuteDigit0.value = minuteString[0].digitToInt()
            _minuteDigit1.value = 0
        }

        val secondString = seconds.toString()
        if (secondString.length > 1) {
            _secondDigit1.value = secondString[0].digitToInt()
            _secondDigit0.value = secondString[1].digitToInt()
        } else {
            _secondDigit0.value = secondString[0].digitToInt()
            _secondDigit1.value = 0
        }
    }

    private var countDownTimer: CountDownTimer? = null

    /**
     * method to start count down timer
     */
    private fun startCountDownTimer(pausedTime: Long? = null) {
        timeCountInMilliSeconds?.let {
            countDownTimer = object : CountDownTimer(pausedTime ?: it, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    _currentTime.value = millisUntilFinished
                    _percentage.value =
                        1f - (millisUntilFinished.toFloat() / it.toFloat())
                    updateDigits()
                }

                override fun onFinish() {
                    _currentTime.value = 0
                    _percentage.value = 1f
                    timerStatus.value = TimerStatus.STOPPED
                }
            }.start()
            timerStatus.value = TimerStatus.STARTED
        }
    }

    fun start() {
        if (timerStatus.value === TimerStatus.PAUSED) {
            countDownTimer?.cancel()
            startCountDownTimer(pausedTime)
        } else if (timerStatus.value !== TimerStatus.NOT_SET) {
            countDownTimer?.cancel()
            // call to initialize the timer values
            // call to initialize the progress bar values
            // showing the reset icon
            // changing the timer status to started
            timerStatus.value = TimerStatus.STARTED
            // call to start the count down timer
            startCountDownTimer()
        }
    }

    fun pause() {
        timerStatus.value = TimerStatus.PAUSED
        pausedTime = currentTime.value ?: 0
        countDownTimer?.cancel()
    }

    fun stop() {
        timerStatus.value = TimerStatus.STOPPED
        countDownTimer?.cancel()
    }

    /**
     * method to stop count down timer
     */
    private fun stopCountDownTimer() {
        countDownTimer!!.cancel()
    }

    fun reset() {
        _currentTime.value = timeCountInMilliSeconds
        _percentage.value = 0f
        timerStatus.value = TimerStatus.STOPPED
        updateDigits()
    }
}
