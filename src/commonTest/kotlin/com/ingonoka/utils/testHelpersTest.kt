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
import kotlin.test.assertFails

class TestHelpersTest {

    @Test
    fun testArrayCompare() {

        assertArrayEquals(intArrayOf(1, 2, 3).toTypedArray(), intArrayOf(1, 2, 3).toTypedArray())

        assertFails { assertArrayEquals(intArrayOf(1, 2, 3).toTypedArray(), intArrayOf(1, 2, 4).toTypedArray()) }

        assertArrayNotEquals(intArrayOf(1, 2, 3).toTypedArray(), intArrayOf(1, 2, 4).toTypedArray())

        assertFails { assertArrayNotEquals(intArrayOf(1, 2, 3).toTypedArray(), intArrayOf(1, 2, 3).toTypedArray()) }

    }
}