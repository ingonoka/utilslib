/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Class to hold a Singleton that can be initialized with arguments,
 * which is not possible with the Kotlin `object`
 *
 * @param creator The function to instance the Singleton Object

 * @param T The type of the Singleton object
 *
 * @param A The argument used to instance the Singleton Object
 *
 *
 * ## Declare one
 *
 * ```Kotlin
 * class Manager private constructor(context: Context) {
 * init {
 * // Init using context argument
 * }
 *
 * companion object : SingletonHolder<Manager, Context>(::Manager)
 * }
 * ```
 * ## Use one
 * ```
 * Manager.initialize(context)
 * Manager.getInstance().doStuff()
 * ```
 * Or
 * ```
 * Manager.getInstance(context).doStuff()
 * ```
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private val synchronizationLock = Mutex(false)
    private var creator: ((A) -> T)? = creator
    private var instance: T? = null

    /**
     * Initialize the singleton and return the instance if successful
     *
     * @throws IllegalStateException if the Singleton was already initialized
     */
    suspend fun initialize(arg: A): T {
        val i = instance

        check(i == null) { "Already initialized" }

        return synchronizationLock.withLock {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }

    /**
     * Return the singleton and initialize it when it hasn't  been instantiated yet
     */
    suspend fun instance(arg: A): T = instance ?: initialize(arg)


    /**
     * Return a the singleton without the need for suspend function
     *
     * @throws IllegalStateException if the Singleton hasn't been initialized yet
     */
    fun instance() : T = instance ?: throw IllegalStateException("Singleton not initialized")

}