/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

/*
Copied from https://android.jlelse.eu/just-another-result-monad-with-kotlin-4744f662349d on Jan 5, 2020
*/

/**
 * Return type to encapsulate an object of type T in case of success or a [Throwable] in case
 * of failure.
 */
class SingleResult<T : Any> private constructor(val value: Any) {

    companion object {


        /**
         * Create a successful result with [value]
         */
        fun <T : Any> success(value: T): SingleResult<T> = SingleResult(value)

        /**
         * Create a failed result with [error]
         */
        fun <T : Any> failure(error: Throwable): SingleResult<T> = SingleResult(Failure(error))

        fun <T : Any> failure(msg: String, cause: Throwable) = failure<T>(Exception(msg, cause))
    }

    /**
     * Return true if this represents a successful result
     */
    fun isSuccess(): Boolean = this.value !is Failure

    /**
     * Return true if this represents a failed result
     */
    fun isFailure(): Boolean = this.value is Failure

    /**
     * Return actual result value in case of success or call [onFailure] with [Throwable] as argument
     */
    @Suppress("UNCHECKED_CAST")
    inline fun materialize(onFailure: (error: Throwable) -> T): T = when (this.value) {
        is Failure -> onFailure(this.value.error)
        else -> this.value as T
    }

    /**
     * Call [onFailure] is the result is a failure and return null or return the value if a success.
     */
    @Suppress("UNCHECKED_CAST")
    inline fun valueOrNull(onFailure: (error: Throwable) -> Unit): T? = when (this.value) {
        is Failure -> {
            onFailure(this.value.error)
            null
        }
        else -> this.value as T
    }

    /**
     * Return actual result value in case of success or throw Exception with a message created by [onFailure]
     */
    @Suppress("UNCHECKED_CAST")
    fun throwIt(onFailure: () -> String): T = when (this.value) {
        is Failure -> {
            val msg = onFailure()
            throw Exception(msg, this.value.error)
        }
        else -> this.value as T
    }

    /**
     * Return actual result value in case of success or throw original exception
     */
    @Suppress("UNCHECKED_CAST")
    fun valueOrThrow(): T = when (this.value) {
        is Failure -> {
            throw this.value.error
        }
        else -> this.value as T
    }

    /**
     * Map a [SingleResult] with success type T to a new [SingleResult] with success type U by calling [f] with the input
     * result type T. A failed result will be mapped to a new [SingleResult] with the same error.
     */
    @Suppress("UNCHECKED_CAST")
    fun <U : Any> map(f: (T) -> U): SingleResult<U> = when (this.value) {
        is Failure -> failure(this.value.error)
        else -> success(f.invoke(this.value as T))
    }

    /**
     * Marker class for failed result
     */
    class Failure(val error: Throwable) {
        constructor (msg: String, cause: Exception) : this(Exception(msg, cause))
    }
}