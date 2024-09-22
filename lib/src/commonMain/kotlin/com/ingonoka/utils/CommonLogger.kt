/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

/**
 * Logger for common code. (Type alias for SLF4J Logger)
 *
 */
expect interface Logger {
    /**
     * Log a warning
     */
    fun isWarnEnabled(): Boolean
    fun warn(msg: String)
    fun warn(msg: String, e: Throwable)

    /**
     * Log an information
     */
    fun isInfoEnabled(): Boolean
    fun info(msg: String)
    fun info(msg: String, e: Throwable)
    /**
     * Log an error
     */
    fun isErrorEnabled(): Boolean
    fun error(msg: String)
    fun error(msg: String, e: Throwable)

    /**
     * Print trace message
     */
    fun isTraceEnabled(): Boolean
    fun trace(msg: String)
    fun trace(msg: String, e: Throwable)

    /**
     * Print debug message
     */
    fun isDebugEnabled(): Boolean
    fun debug(msg: String)
    fun debug(msg: String, e: Throwable)
}

expect fun getLogger(name: String): Logger

fun Logger.warn(provider: () -> String) {
    if(isWarnEnabled()) warn(provider.invoke())
}
fun Logger.warn(e: Throwable, provider: () -> String) {
    if(isWarnEnabled()) warn(provider.invoke(), e)
}

fun Logger.info(provider: () -> String) {
    if(isInfoEnabled()) info(provider.invoke())
}
fun Logger.info(e: Throwable, provider: () -> String) {
    if(isInfoEnabled()) info(provider.invoke(), e)
}

fun Logger.error(provider: () -> String) {
    if(isErrorEnabled()) error(provider.invoke())
}
fun Logger.error(e: Throwable, provider: () -> String) {
    if(isErrorEnabled()) error(provider.invoke(), e)
}

fun Logger.trace(provider: () -> String) {
    if(isTraceEnabled()) trace(provider.invoke())
}
fun Logger.trace(e: Throwable, provider: () -> String) {
    if(isTraceEnabled()) trace(provider.invoke(), e)
}

fun Logger.debug(provider: () -> String) {
    if(isDebugEnabled()) debug(provider.invoke())
}
fun Logger.debug(e: Throwable, provider: () -> String) {
    if(isDebugEnabled()) debug(provider.invoke(), e)
}