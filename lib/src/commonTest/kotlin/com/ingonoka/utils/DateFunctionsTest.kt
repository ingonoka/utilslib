/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlinx.datetime.*
import kotlinx.datetime.format.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class DateFunctionsTest {

    fun formatTime(instant: Instant): String {
        val dateFormat = DateTimeComponents.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
            char('T')
            hour()
            char(':')
            minute()
            char(':')
            second()
            offsetHours(Padding.ZERO)
            char(':')
            offsetMinutesOfHour(Padding.ZERO)
        }

        println(TimeZone.availableZoneIds)
        return instant.format(dateFormat, instant.offsetIn(TimeZone.of("+00:00")))

    }

    @Test
    fun testInstantToString() {


        assertEquals(
            "1970-01-01T00:00:00+00:00",
            Instant
                .fromEpochSeconds(0L)
                .formatTime(timeZone = TimeZone.UTC)
        )
        assertEquals(
            "2020-07-14T10:17:48+00:00",
            Instant
                .fromEpochSeconds(1594721868L)
                .formatTime(timeZone = TimeZone.UTC)
        )
    }

    @Test
    fun testStringToInstant() {
        assertEquals(0L, Instant.parse("1970-01-01T00:00:00+00:00").epochSeconds)
        assertEquals(0L, Instant.parse("1970-01-01T00:00:00Z").epochSeconds)
        assertEquals(1594721868L, Instant.parse("2020-07-14T10:17:48+00:00").epochSeconds)

        assertEquals(0L, "1970-01-01T00:00:00+00:00".scanTime().epochSeconds)
        assertFailsWith<IllegalArgumentException> { "1970-01-01T00:00:00Z".scanTime().epochSeconds }
        assertEquals(1594721868L, "2020-07-14T10:17:48+00:00".scanTime().epochSeconds)


    }
}