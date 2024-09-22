/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class DateFunctionsTest {

    @Test
    fun testEpochSecondsToString() {
        assertEquals("1970-01-01T00:00:00+00:00", 0L.epochSecondsToString())
        assertEquals("2020-07-14T10:17:48+00:00", 1594721868L.epochSecondsToString())
    }

    @Test
    fun testUtcStringToSeconds() {
        assertEquals(0L, "1970-01-01T00:00:00+00:00".asUtcToEpochSeconds())
        assertEquals(1594721868L, "2020-07-14T10:17:48+00:00".asUtcToEpochSeconds())

    }
}