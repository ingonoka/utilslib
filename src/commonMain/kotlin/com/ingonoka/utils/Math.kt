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
 * Round integer up to the nearest multiple of [n]. [n] must be a multiple of 2 and the integer must be in
 * `(Int.MIN_VALUE + (n - 1))..(Int.MAX_VALUE - (n - 1))`
 */
fun Int.roundUpToMultipleOf(n: Int): Int {
    require((n and 1) != 1) { "Only works for multiples of an even number." }
    require(this in (Int.MIN_VALUE + (n - 1))..(Int.MAX_VALUE - (n - 1))) { "Number is out of range for this function." }

    return (this + (n - 1)) and (-n)
}
/**
 * Round long integer up to the nearest multiple of [n]. [n] must be a multiple of 2 and the integer must be in
 * `(Long.MIN_VALUE + (n - 1))..(Long.MAX_VALUE - (n - 1))`
 */
fun Long.roundUpToMultipleOf(n: Long): Long {
    require((n and 1) != 1L) { "Only works for multiples of an even number." }
    require(this in (Long.MIN_VALUE + (n - 1))..(Long.MAX_VALUE - (n - 1))) { "Number is out of range for this function." }

    return (this + (n - 1)) and (-n)
}