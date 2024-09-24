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
 * Copy [length] elements of this list starting at [srcPos] into [dest] list starting at [destPos]. The elements
 * are copied by reference only and not cloned.
 *
 * An [IndexOutOfBoundsException] exception will be thrown if positions or length are not within the limits of this list and [dest].
 */
fun <T> List<T>.copyInto(
    srcPos: Int = 0,
    dest: MutableList<T>,
    destPos: Int = 0,
    length: Int = size
): MutableList<T> {
    for (i in 0 until length) {
        dest[destPos + i] = this[srcPos + i]
    }
    return dest
}

/**
 * Return a list of size [n], by adding [n] elements of [value] and type [T] at the beginning of the list
 *
 * Does nothing if [n] is smaller or equal to the list size
 */
fun <T> List<T>.padStart(n: Int, value: T): List<T> = List(maxOf(0, n - size)) { value } + this

/**
 * Return a list of size [n], by adding [n] elements of [value] and type [T] at the end of the list
 *
 * Does nothing if [n] is smaller or equal to the list size
 */
fun <T> List<T>.padEnd(n: Int, value: T): List<T> = this + List(maxOf(0, n - size)) { value }