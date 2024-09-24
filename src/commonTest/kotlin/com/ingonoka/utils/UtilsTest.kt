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
    fun testBuildByteArray() {

        val expected = listOf(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2)
        assertArrayEquals(
            expected.toTypedArray(),
            buildByteArray {
                this.write(1)
                this.write(2L)
            }.getOrThrow().toTypedArray()
        )
    }

    @Test
    fun testCompareLists() {

        assertTrue(listOf<Int>().containsAll(listOf()))
        assertTrue(listOf(1, 2, 3).containsAll(listOf(1, 2, 3)))
        assertTrue(listOf(1, 2, 3).containsAll(listOf(3, 2, 1)))
        assertTrue(listOf(1, 2, 2).containsAll(listOf(2, 2, 1)))
        assertFalse(listOf(1, 2, 2).containsAll(listOf(1, 2, 3)))
        assertFalse(listOf(1, 2, 3).containsAll(listOf(1, 2, 3, 4)))

    }

    @Test
    fun combineMessages() {
        val ex = Exception("First Message", Exception("Second Message", Exception("Third Message")))

        assertEquals("First MessageSecond MessageThird Message", ex.combineAllMessages(""))
        assertEquals("First Message => Second Message => Third Message", ex.combineAllMessages(" => "))
        assertEquals(
            """
            |First Message
            |Second Message
            |Third Message""".trimMargin(), ex.combineAllMessages("\n")
        )
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

    private fun getTestSealedClass(option: Int): TestSealedClass? = when (option) {
        1 -> TestSealedClass1()
        2 -> TestSealedClass2()
        else -> null
    }

}

sealed class TestSealedClass(val state: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TestSealedClass) return false

        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        return state
    }

    override fun toString(): String {
        return "TestSealedClass(state=$state)"
    }
}
class TestSealedClass1 : TestSealedClass(1)
class TestSealedClass2 : TestSealedClass(2)
class SomeOtherClass