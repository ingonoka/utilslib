/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import com.ingonoka.qcatlight.utils.asDurationToSeconds
import com.ingonoka.qcatlight.utils.asSecondsToDurationString
import kotlin.test.*

class DurationFunctionsTest {

    @Test
    fun testLongAsSecondsToDurationString() {

        assertEquals("PT0S", (0L).asSecondsToDurationString())
        assertEquals("PT1M", (60L).asSecondsToDurationString())
        assertEquals("PT1H", (3600L).asSecondsToDurationString())
        assertEquals("PT24H", (86400L).asSecondsToDurationString())
        assertEquals("PT240H", (10*86400L).asSecondsToDurationString())

    }

    @Test
    fun testStringAsDurationToSeconds() {

        assertEquals(0L, "PT0S".asDurationToSeconds())
        assertEquals(60L, "PT1M".asDurationToSeconds())
        assertEquals(3600L, "PT1H".asDurationToSeconds())
        assertEquals(86400L, "PT24H".asDurationToSeconds())
        assertEquals(86400L, "P1D".asDurationToSeconds())
        assertEquals(10*86400L, "PT240H".asDurationToSeconds())
        assertEquals(10*86400L, "P10D".asDurationToSeconds())
        assertEquals(10*86400L + 60, "P10DT60S".asDurationToSeconds())
        assertEquals(10*86400L + 60, "P10DT1M".asDurationToSeconds())
    }
}