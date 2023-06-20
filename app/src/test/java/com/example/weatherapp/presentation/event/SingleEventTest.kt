package com.example.weatherapp.presentation.event

import org.junit.Assert
import org.junit.Test

class SingleEventTest {

    @Test
    fun gettingValueReturnsValueAtFirstTimeAndNullAtSecondTime() {
        val value = "Test Value"
        val singleEvent = SingleEvent(value)

        val firstValue = singleEvent.getValue()
        val secondValue = singleEvent.getValue()

        Assert.assertEquals(firstValue, value)
        Assert.assertNull(secondValue)
    }
}