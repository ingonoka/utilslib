/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlin.reflect.KProperty

expect class AtomicReference<V>() {
    fun compareAndSet(expect: V?, update: V): Boolean
    fun get(): V?
}

/**
 * Usage:
 * ````
 * val field = InitOnceThreadSafe<String>()
 * val prop by field
 *
 * fun set() {
 *    prop.initWith("A string")
 * }
 *
 * fun test() {
 *     if(prop != null) { // do something }
 * }
 * ````
 */
class InitOnceThreadSafe<T : Any> {

    private val viewRef = AtomicReference<T?>()

    fun initWith(value: T) {
        if (!viewRef.compareAndSet(null, value)) {
            throw IllegalStateException("Already initialized")
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = viewRef.get()
}