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
import kotlin.test.assertFails

class InitOnceThreadSafeTest {

    @Test
    fun testSingleAssignment() {

        val refObject = InitOnceThreadSafe<Int>()
        val ref by refObject

        refObject.initWith(128)
        assertEquals(128, ref)

        assertFails { refObject.initWith(10) }
    }

    @Test
    fun testIntialValueIsNull() {
        val refObject = InitOnceThreadSafe<Int>()
        val ref by refObject
        assertEquals(null, ref)
    }

    @Test
    fun testSetSameValueAgain() {
        val refObject = InitOnceThreadSafe<Int>()

        refObject.initWith(128)
        assertFails { refObject.initWith(128) }
    }
}