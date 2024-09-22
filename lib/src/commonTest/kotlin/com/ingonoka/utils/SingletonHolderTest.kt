/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class SingletonHolderTest {


    @ExperimentalCoroutinesApi
    @Test
    fun testGetInstanceWithArg() = runBlocking {

        assertFails { ASingleton.instance() }
        
        assertEquals(2, ASingleton.instance(1).appliedContext)

        assertEquals(2, ASingleton.instance().appliedContext)


    }

    @ExperimentalCoroutinesApi
    @Test
    fun testInitialize() = runBlocking {

        assertEquals(2, AnotherSingleton.initialize(1).appliedContext)

        assertEquals(2, AnotherSingleton.instance().appliedContext)

    }
}

class ASingleton private constructor(context: Int) {

    val appliedContext = context + 1

    companion object : SingletonHolder<ASingleton, Int>(::ASingleton)
}

class AnotherSingleton private constructor(context: Int) {

    val appliedContext = context + 1

    companion object : SingletonHolder<AnotherSingleton, Int>(::AnotherSingleton)
}