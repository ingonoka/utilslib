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

class ListExtTest {

    @Test
    fun testCopyInto() {

        assertEquals(
            listOf(0, 10, 11, 12, 13, 14, 15, 16, 8),
            listOf(10, 11, 12, 13, 14, 15, 16)
                .copyInto(0, mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8), 1, 7)
        )

        assertEquals(
            listOf(0, 10, 11, 12, 13, 14, 15, 16, 8),
            listOf(10, 11, 12, 13, 14, 15, 16)
                .copyInto(0, mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8), 1)
        )

        assertEquals(
            listOf(10, 11, 12, 13, 14, 15, 16, 7, 8),
            listOf(10, 11, 12, 13, 14, 15, 16)
                .copyInto(0, mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8))
        )

        assertEquals(
            listOf(10, 11, 12, 13, 14, 15, 16, 17, 18),
            listOf(10, 11, 12, 13, 14, 15, 16, 17, 18)
                .copyInto(0, mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8))
        )

        assertEquals(
            listOf(0, 10, 2, 3, 4, 5, 6, 7, 8),
            listOf(10)
                .copyInto(0, mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8), 1, 1)
        )

    }

    @Test
    fun testPadStart() {

        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padStart(4, 0))
        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padStart(3, 0))
        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padStart(0, 0))
        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padStart(-1, 0))
        assertEquals(listOf(0,1,2,3,4), listOf(1,2,3,4).padStart(5, 0))
        assertEquals(listOf(0,0,0,0,0), listOf<Int>().padStart(5, 0))
    }

    @Test
    fun testPadEnd() {

        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padEnd(4, 0))
        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padEnd(3, 0))
        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padEnd(0, 0))
        assertEquals(listOf(1,2,3,4), listOf(1,2,3,4).padEnd(-1, 0))
        assertEquals(listOf(1,2,3,4, 0), listOf(1,2,3,4).padEnd(5, 0))
        assertEquals(listOf(0,0,0,0,0), listOf<Int>().padEnd(5, 0))
    }
}
