/*
 * Copyright (c) 2022. Ingo Noka
 * This file belongs to project cba9driver.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.milliseconds

expect fun currentTimeMillis(): Long

/**
 * Pool of objects with expiry period and maximum capacity.
 *
 * Objects that haven't been checked out for more than [expirationTime] will be removed from the pool.
 *
 * The maximum combined number of available and checked-out objects must not exceed [capacity]. [checkOut] will return `null`
 * if the capacity has been reached.
 *
 * @param capacity [Int] Default **`100`**
 *
 * @param expirationTime [Long] In milliseconds. Default **`0`**. A value of **`0`** means objects do not expire.
 */
abstract class ObjectPool<T>(val capacity: Int = 100, private val expirationTime: Long = 0L) {

    init {
        require(capacity > 0)
        require(expirationTime >= 0)
    }

    private val locked = mutableMapOf<T, Instant>()
    private val unlocked = mutableMapOf<T, Instant>()

    /**
     * Called to instance a new object that will be added to the pool.
     */
    protected abstract fun create(): T

    /** Called before the object is checked out from the pool.
     * Return **`false`** if object is not valid anymore.  Invalid objects will be removed from the pool.
     */
    abstract fun validate(o: T?): Boolean

    /** Called when the object is removed from the pool.
     *
     */
    abstract fun expire(o: T?)

    /** Return an element from the pool of available elements.
     *
     * @param required [(T)->Boolean] Function takes an element of the pool and returns true if the element meets
     * certain specifications
     */
    fun checkOut(required: (T) -> Boolean): T? {
        val now = Clock.System.now()
        val iterator: MutableIterator<Map.Entry<T, Instant>> = unlocked.entries.iterator()

        while (iterator.hasNext()) {
            val next = iterator.next()
            val obj = next.key
            val time = next.value

            if (expirationTime > 0 && now - time > expirationTime.milliseconds) {
                iterator.remove(); expire(obj)
            } else
                if (validate(obj)) {
                    iterator.remove(); locked[obj] = now; return obj
                } else {
                    iterator.remove(); expire(obj)
                }
        }

        // no objects available, instance a new one if capacity hasn't been reached yet
        return if (locked.size + unlocked.size < capacity) {
            val t = create()
            locked[t] = now
            t
        } else {
            // all objects are checked out, see whether any object is not required anymore and check it back in
            if (locked.size == capacity) {
                for (t in locked.keys) {
                    if (expirationTime > 0 && now - locked.getValue(t) > expirationTime.milliseconds && !required(t)) checkIn(
                        t
                    )
                }
            }
            null
        }
    }

    /**
     * Return an element to the pool of available elements.
     */
    fun checkIn(t: T) {
        locked.remove(t)
        unlocked[t] = Clock.System.now()
    }

}