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

class StringifiableTest {

    @Test
    fun testStringify() {

        println(TestClassStringifiable().stringify(false))
    }
}

class TestClassStringifiable: Stringifiable {
    override fun stringify(short: Boolean, indent: String): String {
        return "TestClassStringifiable"
    }

}