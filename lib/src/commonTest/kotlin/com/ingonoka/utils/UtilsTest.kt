/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlin.test.*


class UtilsTest {

    @Test
    fun testToByteArray() {
        assertTrue { byteArrayOf(0x30, 0x31, 0x32).contentEquals("012".toByteArray()) }
        assertTrue { byteArrayOf().contentEquals("".toByteArray()) }
    }

    @Test
    fun testToCharArray() {
        assertTrue { charArrayOf('0', '1', '2').contentEquals("012".toCharacterArray()) }
        assertTrue { charArrayOf().contentEquals("".toCharacterArray()) }
    }

    @Test
    fun testCompareLists() {

        assertTrue(compareLists<Int>(listOf(), listOf()))
        assertFalse(compareLists<Int>(listOf(), null))
        assertTrue(compareLists<Int>(null, null))
        assertTrue(compareLists(listOf(1, 2, 3), listOf(1, 2, 3)))
        assertTrue(compareLists(listOf(1, 2, 3), listOf(3, 2, 1)))
        assertTrue(compareLists(listOf(1, 2, 2), listOf(2, 2, 1)))
        assertFalse(compareLists(listOf(1, 2, 2), listOf(1, 2, 3)))
        assertFalse(compareLists(listOf(1, 2, 3), listOf(1, 2, 3, 4)))

    }

    @Test
    fun combineMessages() {
        val ex = Exception("First Message", Exception("Second Message", Exception("Third Message")))

        assertEquals("First MessageSecond MessageThird Message",ex.combineAllMessages("") )
        assertEquals("First Message => Second Message => Third Message",ex.combineAllMessages(" => ") )
        assertEquals("""
            |First Message
            |Second Message
            |Third Message""".trimMargin(),ex.combineAllMessages("\n") )
    }

    @Test
    fun testTakeIfIsInstance() {

        assertNotNull(getTestSealedClass(1).takeIfIsInstance<TestSealedClass1>())
        assertNull(getTestSealedClass(1).takeIfIsInstance<TestSealedClass2>())

        assertNotNull(getTestSealedClass(2).takeIfIsInstance<TestSealedClass2>())
        assertNull(getTestSealedClass(2).takeIfIsInstance<TestSealedClass1>())

        assertNull(getTestSealedClass(3).takeIfIsInstance<TestSealedClass2>())

        assertNotNull(SomeOtherClass().takeIfIsInstance<SomeOtherClass>())
        assertNull(SomeOtherClass().takeIfIsInstance<TestSealedClass>())

    }

    private fun getTestSealedClass(option: Int): TestSealedClass? = when(option) {
            1 -> TestSealedClass1()
            2 -> TestSealedClass2()
            else -> null
        }
}

sealed class TestSealedClass()
class TestSealedClass1: TestSealedClass()
class TestSealedClass2: TestSealedClass()
class SomeOtherClass