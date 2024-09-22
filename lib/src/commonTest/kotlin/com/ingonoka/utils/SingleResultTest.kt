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

class SingleResultTest {

    @Test
    fun failure() {
        assertEquals("Success", testFailure(false).materialize { it.message ?: ""})
        assertEquals("Failure", testFailure(true).materialize { it.message ?: ""})
        assertEquals("Set to fail", testFailure(true).materialize { it.cause?.message ?: ""})

    }

    @Test
    fun testValueOrThrow() {
        assertEquals(1, SingleResult.success(1).valueOrThrow())
        assertFails { SingleResult.failure<Int>(Exception("test 1234")).valueOrThrow() }.also {
            assertEquals("test 1234", it.message)
        }
    }

    @Test
    fun testMaterializeOrNull() {
        var value = SingleResult.failure<Int>(Exception("test")).valueOrNull {
            assertEquals("test", it.message)
        }
        assertNull(value)

        value = SingleResult.success(123).valueOrNull {
            fail()
        }
        assertEquals(123, value)

    }

    private fun testFailure(fail: Boolean) = try {
        if(fail)
            throw Exception("Set to fail")
        else
            SingleResult.success("Success")

    } catch(e: Exception) {
        SingleResult.failure("Failure", e)
    }
}