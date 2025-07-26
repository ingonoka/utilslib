/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import kotlin.math.max
import kotlin.properties.Delegates

/**
 * Minimum number of bytes to add to the buffer if capacity is reached when writing data to the buffer.
 */
internal const val MIN_EXTEND_SIZE = 128

interface ReadBuffer : Buffer {

    /**
     * Read one byte from the buffer.
     */
    fun readIntByte(): Result<Int>
    fun readUByte(): Result<UByte>
    fun readByte(): Result<Byte>

    /**
     * Read one byte from buffer, but return null instead of failure [Result].
     */
    fun readIntByteOrNull(): Int?
    fun readUByteOrNull(): UByte?
    fun readByteOrNull(): Byte?

    /**
     * Read one byte, but do not advance position.
     *
     * ## Usage
     * ```
     * // Read a 3 byte long string, but only if the first byte is not a 0
     * val buf = ListOfIntBuffer.wrap(listOf('A'.code, 'B'.code, 'C'.code))
     * if(buf.peekByte() != 0) {
     *      buf.readString(3) // returns "ABC" wrapped in successful [Result]
     * }
     *
     * ```
     */
    fun peekIntByte(): Result<Int>
    fun peekByte(): Result<Byte>
    fun peekUByte(): Result<UByte>

    /**
     * Same as peekByte, but return a null instead of failure [Result]
     */
    fun peekIntByteOrNull(): Int?
    fun peekByteOrNull(): Byte?
    fun peekUByteOrNull(): UByte?

    /**
     * Read [n] bytes from the buffer and convert to a [Long] with [byteOrder]
     * ```
     * ```
     * If [byteOrder] is [ByteOrder.BIG_ENDIAN], then the highest byte will be read first
     *
     * ```
     * [0, 0, 0, 0, 0, 0, 0, 1]
     * // will be read as 1
     * ```
     * If [byteOrder] is [ByteOrder.LITTLE_ENDIAN], then the lowest byte will be written first
     *      *
     * ```
     * [1, 0, 0, 0, 0, 0, 0, 0]
     * // will be read as 1
     * ```
     */
    fun readLong(n: Int = 8, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Result<Long>

    /**
     * Same a [readLong] but returns a null instead of throwing an exception
     */
    fun readLongOrNull(n: Int = 8, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Long?

    /**
     * Read [Long], but do not advance position.
     *
     */
    fun peekLong(n: Int = 8, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Result<Long>

    /**
     * Read [Long], but do not advance position. Return null instead of failure [Result]
     *
     */
    fun peekLongOrNull(n: Int = 8, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Long?

    /**
     * Read [n] bytes from the buffer and convert to a [Int] with [byteOrder]
     * ```
     * ```
     * If [byteOrder] is [ByteOrder.BIG_ENDIAN], then the highest byte will be read first
     *
     * ```
     * [0, 0, 0, 1]
     * // will be read as 1
     * ```
     * If [byteOrder] is [ByteOrder.LITTLE_ENDIAN], then the lowest byte will be written first
     *      *
     * ```
     * [1, 0, 0, 0]
     * // will be read as 1
     * ```
     */
    fun readInt(n: Int = 4, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Result<Int>

    /**
     * Same a [readInt] but returns a null instead of throwing an exception
     */
    fun readIntOrNull(n: Int = 4, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Int?

    /**
     * Read [Int], but do not advance position.
     *
     */
    fun peekInt(n: Int = 4, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Result<Int>
    fun peekIntOrNull(n: Int = 4, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Int?

    /**
     * Read [n] bytes and convert to [String] using UTF-8
     */
    fun readString(n: Int): Result<AsciiString>

    /**
     * Same as [readString], but return null instead of throwing exception
     */
    fun readStringOrNull(n: Int): AsciiString?

    /**
     * Read [String], but do not advance position.
     *
     */
    fun peekString(n: Int): Result<AsciiString>
    fun peekStringOrNull(n: Int): AsciiString?

    /**
     * Returns a list of bytes, which is a copy of [n] bytes of the buffer starting at [position].
     * If there are less than [n] bytes left in the backing list, function returns a [Result.Failure].
     * If [position] is already at the [watermark] or [n] is zero, then an empty array is returned.
     *
     * Note that the list contains integers in the range `-128..127`
     */
    fun readList(n: Int = 0): Result<List<Int>>

    /**
     * Same as [readList], but return null instead of throwing exceptions
     *
     * @see readList
     */
    fun readListOrNull(n: Int = 0): List<Int>?

    /**
     * Read [List] of bytes, but do not advance position.
     *
     */
    fun peekList(n: Int = 0): Result<List<Int>>

    /**
     * Same as [peekList], but return null instead of throwing exceptions
     */
    fun peekListOrNull(n: Int = 0): List<Int>?

    /**
     * Returns next [n] bytes from buffer as [ByteArray]
     */
    fun readByteArray(n: Int = 0): Result<ByteArray>
    fun readUByteArray(n: Int = 0): Result<UByteArray>

    /**
     * Same as [readByteArray], but return null instead of throwing exceptions
     */
    fun readByteArrayOrNull(n: Int = 0): ByteArray?
    fun readUByteArrayOrNull(n: Int = 0): UByteArray?

    /**
     * Read [ByteArray], but do not advance position.
     *
     */
    fun peekByteArray(n: Int = 0): Result<ByteArray>
    fun peekUByteArray(n: Int = 0): Result<UByteArray>

    /**
     * Same as [peekByteArray], but return null instead of throwing exceptions
     */
    fun peekByteArrayOrNull(n: Int = 0): ByteArray?
    fun peekUByteArrayOrNull(n: Int = 0): UByteArray?

    /**
     * Reads the remaining bytes. Returns an empty list when no bytes are left to read.
     */
    fun readRemaining(): Result<List<Int>>
    fun readRemainingBytes(): Result<ByteArray>
    fun readRemainingUBytes(): Result<UByteArray>

    /**
     * Same as [readRemaining], but return null instead of throwing exceptions
     */
    fun readRemainingOrNull(): List<Int>?

    /**
     * Read remaining bytes, but do not advance position.
     *
     */
    fun peekRemaining(): Result<List<Int>>
    fun peekRemainingBytes(): Result<ByteArray>
    fun peekRemainingUBytes(): Result<UByteArray>

    /**
     * Same as [peekRemaining], but return null instead of throwing exceptions
     */
    fun peekRemainingOrNull(): List<Int>?
    fun peekRemainingBytesOrNull(): ByteArray?
    fun peekRemainingUBytesOrNull(): UByteArray?

    /**
     * Return true if number of bytes left to read is at least [n]
     */
    fun hasBytesLeftToRead(n: Int = 1): Boolean

    /**
     * Number of bytes still left to read.
     */
    fun bytesLeftToRead(): Int

    /**
     * Count the elements that meet [predicate], starting with current position.
     * Does not change the position of the buffer.
     */
    fun countWhile(predicate: (b: Byte) -> Boolean): Result<Int> = runCatching {
        var n = 0
        val view = view()
        while (view.readByte().map { predicate(it) }.getOrDefault(false)) n++
        n
    }

    /**
     *  After calling this function, the buffer can be read from the start again.
     *
     * The [watermark] at the time the function is called determines the number of bytes that can be read.
     */
    fun rewind(): ReadBuffer

    /**
     * The maximum number of bytes that can be read from the buffer
     */
    val watermark: Int

    /**
     * Advance the position by [n] elements.
     *
     * @return [Result] with new position as value. Or, [Result.Failure] if `position + n` not within `0 ≥...≤ watermark`
     *
     */
    fun seekBy(n: Int): Result<Int>

    /**
     * Change position of read/write pointer to the element with index [n] (zero based).
     *
     * @return [Result] with new position as value.
     * Return [Result.Failure] if [n] is not in `0..watermark`
     *
     */
    fun seekTo(n: Int): Result<Int>

    /**
     * Provide a view into this [ReadBuffer].
     * Reading from the view will not affect the position in the original [ReadBuffer].
     */
    fun view(): ReadBuffer
}

interface WriteBuffer : Buffer {

    /**
     * Copy a single byte [b] into the buffer
     *
     * @throws IllegalArgumentException If [b] is not in -128..127
     */
    fun writeIntByte(b: Int)
    fun writeByte(b: Byte)
    fun writeUByte(b: UByte)

    /**
     * Copy [l] into buffer ([n] bytes only).
     * If [n] is 0, the minimum number of bytes will be used.
     * ```
     * ```
     * If [byteOrder] is [ByteOrder.BIG_ENDIAN], then the highest byte will be written first
     *
     * ```
     * // 1 is written as
     * [0, 0, 0, 0, 0, 0, 0, 1]
     * ```
     * If [byteOrder] is [ByteOrder.LITTLE_ENDIAN], then the lowest byte will be written first
     *      *
     * ```
     * // 1 is written as
     * [1, 0, 0, 0, 0, 0, 0, 0]
     * ```
     * @param n Number of bytes to write
     * @return Number of bytes written
     */
    fun write(l: Long, n: Int = 8, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Result<Int>

    /**
     * Copy [i] into buffer ([n] bytes only).
     * If [n] is zero, only write the minimum number of bytes.
     * ```
     * ```
     * If [byteOrder] is [ByteOrder.BIG_ENDIAN], then the highest byte will be written first
     *
     * ```
     * // 1 is written as
     * [0, 0, 0, 1]
     * ```
     * If [byteOrder] is [ByteOrder.LITTLE_ENDIAN], then the lowest byte will be written first
     *      *
     * ```
     * // 1 is written as
     * [1, 0, 0, 0]
     * ```
     * @param n Number of bytes to write
     * @return Number of bytes written
     */
    fun write(i: Int, n: Int = 4, byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): Result<Int>

    /**
     * Write st[str]ring in UTF-8 encoding to buffer
     */
    fun write(str: String)

    /**
     * Write all elements of vararg [b] to the buffer. Integers must be within the range of [UByte]
     */
    fun writeAll(vararg b: Int)
    fun writeAll(vararg b: Byte)
    fun writeAll(vararg b: UByte)

    /**
     * Write [arr] to buffer
     */
    fun write(arr: ByteArray)
    fun write(arr: UByteArray)

    /**
     * Write [list] to buffer
     */
    fun write(list: List<Int>)

    /**
     * Current size of the backing buffer, i.e., the total number of bytes that can be written without triggering an
     * extension (and copy) of the backing buffer
     */
    val capacity: Int

    /**
     * Reset the buffer, which allows reuse of the buffer.
     * This is equivalent to deleting the content of the buffer.
     * New writes will start at [position] 0.
     *
     * The previous content of the buffer is not overridden.
     * The size of the backing buffer remains the same.
     */
    fun reset()

    /**
     * Create a [ReadBuffer] from this [WriteBuffer].
     * The new [position] will be 0, and the [ReadBuffer.watermark] is the position before calling this function.
     * Effectively allows reading the data written to the buffer.
     */
    fun toReadBuffer(): ReadBuffer
}

interface Buffer {
    /**
     * Current read/write position in the buffer
     */
    val position: Int

    /**
     * Return a new list with the entire buffer content.
     * Only the populated bytes, i.e., all bytes from index 0, up to the watermark are returned
     */
    fun toList(): List<Int>
    fun toByteArray(): ByteArray
    fun toUByteArray(): UByteArray
}

interface BufferCreator {
    /**
     * Wrap a list of integers into a [ReadBuffer], ready for reading at position 0.
     * The list is trimmed to [n] elements.
     *
     * @param n The number of bytes that are actually populated in [buf].
     * [n] must be positive and not bigger than the size of [buf].
     *
     */
    fun wrap(buf: List<Int>, n: Int = buf.size): ReadBuffer

    /**
     * Wrap a [ByteArray] into a [ReadBuffer], ready for reading at position 0
     *
     * @param n The number of bytes that are actually populated in [buf].
     * [n] must be positive and not bigger than the size of [buf].
     *
     */
    fun wrap(buf: ByteArray, n: Int = buf.size): ReadBuffer
    fun wrap(buf: UByteArray, n: Int = buf.size): ReadBuffer

    /**
     * Wrap a list of integers into a [ReadBuffer], ready for reading at position 0
     */
    fun wrap(vararg ints: Int): ReadBuffer

    /**
     * Create an empty [WriteBuffer], ready for writing at position 0.
     */
    fun empty(n: Int = MIN_EXTEND_SIZE): WriteBuffer
}

/**
 *
 */
class BufferImpl internal constructor(
    /**
     * The backing buffer. Filled with zeros if not provided in constructor.
     */
    private var buffer: UByteArray = UByteArray(MIN_EXTEND_SIZE),

    /**
     * The number of populated bytes in the buffer. Cannot be bigger than size of [buffer]
     */
    override var watermark: Int = 0
) : ReadBuffer, WriteBuffer {

    /**
     * The position of the read/write pointer.
     * The next read or write operation will start at this position.
     */
    override var position: Int by Delegates.observable(0) { _, oldPos, newPos ->
        if (newPos > oldPos && watermark < newPos) watermark = newPos
    }

    /**
     * The size of the backing buffer, which can be bigger than the [watermark], which
     * is the number of populated bytes.
     */
    override val capacity
        get() = buffer.size

    /**
     * WRITE
     */
    override fun writeIntByte(b: Int) {
        require(b in UByte.MIN_VALUE.toInt()..UByte.MAX_VALUE.toInt())
        writeUByte(b.toUByte())
    }

    override fun writeByte(b: Byte) = writeUByte(b.toUByte())

    override fun writeUByte(b: UByte) {
        if (position + 1 > capacity) extend(1)
        buffer[position++] = b
    }


    override fun writeAll(vararg b: Int) {
        require(b.all { it in UByte.MIN_VALUE.toInt()..UByte.MAX_VALUE.toInt() })
        write(b.toList())
    }

    override fun writeAll(vararg b: Byte) = write(b)
    override fun writeAll(vararg b: UByte) = write(b)

    override fun write(i: Int, n: Int, byteOrder: ByteOrder): Result<Int> = runCatching {
        val startPosition = position
        if (position + n > capacity) extend(n)
        position += i.toUInt().copyInto(buffer, position, n, byteOrder).getOrThrow()
        position - startPosition
    }

    override fun write(l: Long, n: Int, byteOrder: ByteOrder): Result<Int> = runCatching {
        val startPosition = position
        if (position + n > capacity) extend(n)
        position += l.toULong().copyInto(buffer, position, n, byteOrder).getOrThrow()
        position - startPosition
    }

    override fun write(str: String) = write(str.encodeToByteArray().toUByteArray())

    override fun write(list: List<Int>) = write(list.map { it.toUByte() }.toUByteArray())

    override fun write(arr: ByteArray) = write(arr.toUByteArray())

    override fun write(arr: UByteArray) {
        if (position + arr.size > capacity) extend(arr.size)
        arr.copyInto(buffer, position)
        position += arr.size
    }


    /**
     * READ
     */
    override fun readIntByte(): Result<Int> = readUByte().map { it.toInt() }

    override fun readByte(): Result<Byte> = readUByte().map { it.toByte() }

    override fun readUByte(): Result<UByte> = when {
        hasBytesLeftToRead() -> Result.success((buffer[position++] and 0xFFu))
        else -> Result.failure(Exception("Cannot read from position $position.  Buffer size: ${buffer.size}"))
    }

    override fun readIntByteOrNull(): Int? = readUByteOrNull()?.toInt()

    override fun readByteOrNull(): Byte? = readUByteOrNull()?.toByte()

    override fun readUByteOrNull(): UByte? = when {
        hasBytesLeftToRead() -> buffer[position++] and 0xFFu
        else -> null
    }

    override fun readInt(n: Int, byteOrder: ByteOrder): Result<Int> = try {

        buffer.readUInt(position, n, byteOrder)
            .onSuccess { position += n }
            .map { it.toInt() }

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun readIntOrNull(n: Int, byteOrder: ByteOrder): Int? = readInt(n, byteOrder).getOrNull()

    override fun readLong(n: Int, byteOrder: ByteOrder): Result<Long> = try {

        buffer.readULong(position, n, byteOrder)
            .onSuccess { position += n }
            .map { it.toLong() }

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun readLongOrNull(n: Int, byteOrder: ByteOrder): Long? = readLong(n, byteOrder).getOrNull()

    override fun readString(n: Int): Result<AsciiString> = try {

        buffer.toAsciiString(position, n).onSuccess { position += n }

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun readStringOrNull(n: Int): AsciiString? = readString(n).getOrNull()

    override fun readList(n: Int): Result<List<Int>> = readUByteArray(n).map { uba -> uba.map { it.toInt() } }

    override fun readListOrNull(n: Int): List<Int>? = readList(n).getOrNull()

    override fun readByteArray(n: Int): Result<ByteArray> = readUByteArray(n).map { it.toByteArray() }

    override fun readByteArrayOrNull(n: Int): ByteArray? = readByteArray(n).getOrNull()

    override fun readUByteArray(n: Int): Result<UByteArray> = try {

        if (position + n > watermark) throw IndexOutOfBoundsException()

        val ba = buffer.sliceArray(position..<position + n)

        position += n

        Result.success(ba)

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun readUByteArrayOrNull(n: Int): UByteArray? = readUByteArray(n).getOrNull()

    override fun readRemaining(): Result<List<Int>> =
        readRemainingUBytes().map { uba -> uba.toList().map { it.toInt() } }

    override fun readRemainingBytes(): Result<ByteArray> = readRemainingUBytes().map { it.toByteArray() }

    override fun readRemainingUBytes(): Result<UByteArray> = try {
        val uba = if (bytesLeftToRead() == 0) {
            ubyteArrayOf()
        } else {
            buffer.copyOfRange(position, watermark)
        }
        position = watermark
        Result.success(uba)

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun readRemainingOrNull(): List<Int>? = readRemaining().getOrNull()

    /**
     * PEEK
     */
    override fun peekIntByte(): Result<Int> = peekUByte().map { it.toInt() }

    override fun peekByte(): Result<Byte> = peekUByte().map { it.toByte() }

    override fun peekUByte(): Result<UByte> = when {
        hasBytesLeftToRead() -> Result.success((buffer[position] and 0xFFu))
        else -> Result.failure(Exception("Cannot read from position $position.  Buffer size: ${buffer.size}"))
    }

    override fun peekIntByteOrNull(): Int? = peekUByteOrNull()?.toInt()

    override fun peekByteOrNull(): Byte? = peekUByteOrNull()?.toByte()

    override fun peekUByteOrNull(): UByte? = when {
        hasBytesLeftToRead() -> buffer[position] and 0xFFu
        else -> null
    }

    override fun peekInt(n: Int, byteOrder: ByteOrder): Result<Int> = try {

        buffer.readUInt(position, n, byteOrder).map { it.toInt() }

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun peekIntOrNull(n: Int, byteOrder: ByteOrder): Int? = peekInt(n, byteOrder).getOrNull()

    override fun peekLong(n: Int, byteOrder: ByteOrder): Result<Long> = try {

        buffer.readULong(position, n, byteOrder).map { it.toLong() }

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun peekLongOrNull(n: Int, byteOrder: ByteOrder): Long? = peekLong(n, byteOrder).getOrNull()

    override fun peekString(n: Int): Result<AsciiString> = buffer.toAsciiString(position, n)

    override fun peekStringOrNull(n: Int): AsciiString? = peekString(n).getOrNull()

    override fun peekList(n: Int): Result<List<Int>> = peekUByteArray(n).map { uba -> uba.map { it.toInt() } }

    override fun peekListOrNull(n: Int): List<Int>? = peekList(n).getOrNull()

    override fun peekByteArray(n: Int): Result<ByteArray> = peekUByteArray(n).map { it.toByteArray() }

    override fun peekByteArrayOrNull(n: Int): ByteArray? = peekUByteArray(n).map { it.toByteArray() }.getOrNull()

    override fun peekUByteArray(n: Int): Result<UByteArray> = try {

        readUByteArray(n).onSuccess { position -= n }

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun peekUByteArrayOrNull(n: Int): UByteArray? = peekUByteArray(n).getOrNull()

    override fun peekRemaining(): Result<List<Int>> =
        peekRemainingUBytes().map { uba -> uba.toList().map { it.toInt() } }

    override fun peekRemainingOrNull(): List<Int>? = peekRemaining().getOrNull()

    override fun peekRemainingBytes(): Result<ByteArray> = peekRemainingUBytes().map { it.toByteArray() }
    override fun peekRemainingBytesOrNull(): ByteArray? = peekRemainingBytes().getOrNull()

    override fun peekRemainingUBytes(): Result<UByteArray> = try {
        val uba = if (bytesLeftToRead() == 0) {
            ubyteArrayOf()
        } else {
            buffer.copyOfRange(position, watermark)
        }

        Result.success(uba)

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun peekRemainingUBytesOrNull(): UByteArray? = peekRemainingUBytes().getOrNull()

    override fun toList(): List<Int> = toUByteArray().toList().map { it.toInt() }
    override fun toByteArray(): ByteArray = toUByteArray().toByteArray()
    override fun toUByteArray(): UByteArray = buffer.copyOf(watermark)

    override fun hasBytesLeftToRead(n: Int): Boolean = bytesLeftToRead() >= n
    override fun bytesLeftToRead(): Int = watermark - position

    override fun toReadBuffer(): ReadBuffer = wrap(buffer.copyOf(watermark), watermark)

    override fun rewind(): ReadBuffer {
        position = 0
        return this
    }

    override fun reset() {
        position = 0
        watermark = 0
    }

    /**
     * SEEK
     */
    override fun seekTo(n: Int): Result<Int> = try {

        require(n in 0..watermark)

        position = n

        Result.success(position)

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun seekBy(n: Int): Result<Int> = try {

        require(n >= 0)

        if (n > 0) {
            if (position + n > watermark) throw IndexOutOfBoundsException()
            position += n
        }

        Result.success(position)

    } catch (e: Exception) {

        Result.failure(e)
    }

    override fun view(): ReadBuffer = BufferImpl(buffer, watermark).also { it.position = position }

    /**
     * Create new backing buffer, which is n bytes larger than the current one. The content of the
     * original buffer is copied into the new one.
     */
    private fun extend(n: Int) {
        val newBuffer = UByteArray(capacity + max(n, MIN_EXTEND_SIZE))
        buffer.copyInto(newBuffer)
        buffer = newBuffer
    }

    /**
     * Two [Buffer] objects are equal if they have the same watermark and position, and if all buffer elements upto
     * the [watermark] are identical.
     * Buffer elements beyond the watermark are ignored.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BufferImpl) return false

        if (watermark != other.watermark) return false
        if (position != other.position) return false

        if(watermark == buffer.size) {
            if (!buffer.contentEquals(other.buffer)) return false
        } else {
            buffer.onEachIndexed { index, t ->
                if(index >= watermark) return@onEachIndexed
                if( t != other.buffer[index]) return false
            }
        }
        return true
    }

    /**
     * The hash code enures that [Buffer] objects that are equal also have the same hashcode by ensuring
     * that only buffer elements upto the [watermark] are included in the calculation of the hashcode.
     */
    override fun hashCode(): Int {
        var result = watermark
        result = buffer.foldIndexed(result) {index, acc, unit ->
            if(index >= watermark) return@foldIndexed(acc)
            31 * acc + unit.hashCode()
        }
        result = 31 * result + position
        return result
    }

    companion object : BufferCreator {
        /**
         * Wrap a list of integers into a [ReadBuffer], ready for reading at [position] 0.
         * The list is trimmed to [n] elements.
         *
         * @param n The number of bytes that are actually populated in [buf].
         * [n] must be positive and not bigger than the size of [buf].
         *
         */
        override fun wrap(buf: List<Int>, n: Int): ReadBuffer {
            require(buf.all { it in -256..255 })
            require(n in 0..buf.size)
            return BufferImpl(UByteArray(n) { buf[it].toUByte() }).apply { watermark = n }
        }

        /**
         * Wrap a [ByteArray] into a [ReadBuffer], ready for reading at [position] 0
         *
         * @param n The number of bytes that are actually populated in [buf].
         * [n] must be positive and not bigger than the size of [buf].
         *
         */
        override fun wrap(buf: ByteArray, n: Int): ReadBuffer {
            require(n in 0..buf.size)
            return wrap(UByteArray(n) { buf[it].toUByte() })
        }

        override fun wrap(buf: UByteArray, n: Int): ReadBuffer {
            require(n in 0..buf.size)
            return BufferImpl(buf.copyOf(n)).apply {
                watermark = n
                position = 0
            }
        }

        /**
         * Wrap a list of integers into a [ReadBuffer], ready for reading at [position] 0
         */
        override fun wrap(vararg ints: Int): ReadBuffer = wrap(ints.toList(), ints.size)

        /**
         * Create an empty [WriteBuffer], ready for writing at [position] 0.
         */
        override fun empty(n: Int): WriteBuffer {
            require(n >= 0)
            return BufferImpl(UByteArray(n))
        }
    }
}

fun List<Int>.buffer() = BufferImpl.wrap(this)
fun ByteArray.buffer() = BufferImpl.wrap(this)
fun UByteArray.buffer() = BufferImpl.wrap(this)