/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils


@Suppress("DuplicatedCode")
/**
 * Very limited version of a Byte Buffer, which is backed by a ByteArray.
 */
class PoorMansByteBuffer(private val n: Int = 128) {

    init {
        require(n > 0) { "Initial size of byte buffer must be greater than 0." }
    }

    constructor(buf: ByteArray) : this(if (buf.isEmpty()) 128 else buf.size) {
        if (buf.isNotEmpty()) writeByteArray(buf)
        rewind()
    }

    /**
     * The backing buffer
     */
    private var buffer = ByteArray(n)

    /**
     * Current read/write position in the buffer
     */
    var pos = 0
        private set

    /**
     * Current size of the backing buffer
     */
    val size
        get() = buffer.size

    /**
     * Write one single byte into buffer
     */
    fun writeByte(b: Byte) {
        if (pos + 1 > buffer.size) extend(pos + 1)
        buffer[pos++] = b
    }

    /**
     * Read one single character from buffer
     */
    fun readByte(): SingleResult<Byte> {
        return when {
            pos + 1 > buffer.size -> SingleResult.failure(Exception("Cannot read $n bytes.  Only have ${buffer.size - pos}"))
            else -> SingleResult.success(buffer[pos++])
        }
    }

    /**
     * Copy Long into buffer in big endian (8 bytes)
     */
    fun writeLong(i: Long) {
        if (pos + 8 > buffer.size) extend(pos + 8)
        buffer[pos++] = ((i shr 56) and 255).toByte()
        buffer[pos++] = ((i shr 48) and 255).toByte()
        buffer[pos++] = ((i shr 40) and 255).toByte()
        buffer[pos++] = ((i shr 32) and 255).toByte()
        buffer[pos++] = ((i shr 24) and 255).toByte()
        buffer[pos++] = ((i shr 16) and 255).toByte()
        buffer[pos++] = ((i shr 8) and 255).toByte()
        buffer[pos++] = (i and 255).toByte()
    }

    /**
     * Read 8 bytes of the buffer and return as Long in big endian
     */
    fun readLong(): Result<Long> = buffer.toLong(pos).onSuccess { pos += 8 }

    /**
     * Copy integer into buffer in big endian (4 bytes)
     */
    fun writeInt(i: Int) {
        if (pos + 4 > buffer.size) extend(pos + 4)
        buffer[pos++] = ((i shr 24) and 255).toByte()
        buffer[pos++] = ((i shr 16) and 255).toByte()
        buffer[pos++] = ((i shr 8) and 255).toByte()
        buffer[pos++] = (i and 255).toByte()
    }

    /**
     * Read 4 bytes of the buffer and return as Int in big endian
     */
    fun readInt(): Result<Int> =  buffer.toInt(pos).onSuccess { pos += 4 }

    /**
     * Copy the [arr] into the buffer and advance the position by the size of the [arr]
     */
    fun writeByteArray(arr: ByteArray) {
        if (pos + arr.size > buffer.size) extend(pos + arr.size)
        arr.copyInto(buffer, pos)
        pos += arr.size
    }

    /**
     * Returns a new array of bytes, which is a copy of [n] bytes of the backing array
     * starting at [pos]. If there are less than [n] bytes left in the backing array returns a SingleResult.Failure.
     * If [n] is 0 or omitted then all remaining bytes are returned, or if [pos] is already
     * at  the end of the backing array, then an empty array is returned.
     */
    fun readByteArray(n: Int = 0): Result<ByteArray> {
        return when {
            n == 0 -> {
                val result = buffer.copyOfRange(pos, buffer.size)
                pos = buffer.size
                Result.success(result)
            }
            pos + n > buffer.size -> Result.failure(Exception("Cannot read $n bytes.  Only have ${buffer.size - pos}"))
            else -> {
                val result = buffer.copyOfRange(pos, pos + n)
                pos += n
                Result.success(result)
            }
        }
    }

     /**
     * Returns true if [pos] is less than the size of the backing array length, i.e. there are still bytes to be read.
     *
     * Note that this function does not make sense for write operations as the backing array will always
     * be extended if the number of bytes to be written does not fit into the current backing array.
     *
     * It could be used however to determine whether the next write operation would trigger an extension of the
     * backing array, which would mean a copy operation of the entire array is performed.
     */
    fun hasRemaining(): Boolean = pos < buffer.size

    /**
     * Returns the number of bytes in the backing array that are still available to read.
     *
     * Note that this function does not make sense for write operations as the backing array will always
     * be extended if the number of bytes to be written does not fit into the current backing array.
     */
    fun remaining(): Int = buffer.size - pos

    /**
     * Return the byte array up to but excluding the current position
     */
    fun toByteArray() = buffer.copyOf(pos)

    /**
     * Set read/write position to 0 and trim buffer
     */
    fun rewind() {
        trim()
        pos = 0
    }

    /**
     * Trim the buffer. Removes unused bytes in the backing array
     */
    fun trim() {
        buffer = toByteArray()
    }

    /**
     * Create new backing buffer, which is n bytes larger than the current one. The content of the
     * original buffer is copied into the new one.
     */
    private fun extend(n: Int) {
        buffer = buffer.copyOf(((n / this.n) * this.n) + this.n)
    }

    /**
     * Hash code based on written part of backing array, i.e. the first [pos] bytes
     */
    override fun hashCode(): Int = buffer.take(pos).fold(1) { acc, element ->
        acc * 31 + element
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as PoorMansByteBuffer

        if (n != other.n) return false
        if (!buffer.contentEquals(other.buffer)) return false
        if (pos != other.pos) return false

        return true
    }
}