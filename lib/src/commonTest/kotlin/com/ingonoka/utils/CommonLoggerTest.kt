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


class CommonLoggerTest {
    val logger = this::class.qualifiedName?.let {
        getLogger(it)
    } ?: getLogger("com.ingonoka.utils.CommonLoggerTest")

    @Test
    fun testWarn() {
        this::class.qualifiedName?.let {
            logger.warn("Test message: warn")
        }

        // make sure the logback config file enabled logging for error only
        //     <logger name="com.ingonoka.utils.CommonLoggerTest" level="ERROR" />
//        logger.warn { fail() }
        logger.error { "test" }
    }

    @Test
    fun testInfo() {
        this::class.qualifiedName?.let {
            logger.info("Test message: info")
        }
            ?: logger.info("Alternative message")
    }
}