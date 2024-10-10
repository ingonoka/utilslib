package com.ingonoka.utils

import com.ingonoka.utils.ByteOrder.BIG_ENDIAN
import com.ingonoka.utils.ByteOrder.LITTLE_ENDIAN

fun UInt.toUBytes(
    length: Int = UInt.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN,
): UByteArray {

    require(length in 0..UInt.SIZE_BYTES)

    // The minimum number of bytes needed to encode this integer
    val minBytes = (UInt.SIZE_BYTES - (this.countLeadingZeroBits() / 8)).takeIf { it != 0 } ?: 1
    require(length == 0 || length >= minBytes)

    val baLength = if (length == 0) minBytes else length

    val ba = UByteArray(baLength)

    when (byteOrder) {
        BIG_ENDIAN -> {
            for (i in 0 until baLength) ba[i] = (this shr (baLength - i - 1) * 8).toUByte()
        }

        LITTLE_ENDIAN -> {
            for (i in baLength - 1 downTo 0) ba[i] = (this shr i * 8).toUByte()
        }
    }
    return ba
}

fun ULong.toUBytes(
    length: Int = ULong.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN,
): UByteArray {

    require(length in 0..ULong.SIZE_BYTES)

    // The minimum number of bytes needed to encode this integer
    val minBytes = (ULong.SIZE_BYTES - (this.countLeadingZeroBits() / 8)).takeIf { it != 0 } ?: 1
    require(length == 0 || length >= minBytes)

    val baLength = if (length == 0) minBytes else length

    val ba = UByteArray(baLength)

    when (byteOrder) {
        BIG_ENDIAN -> {
            for (i in 0 until baLength) ba[i] = (this shr (baLength - i - 1) * 8).toUByte()
        }

        LITTLE_ENDIAN -> {
            for (i in baLength - 1 downTo 0) ba[i] = (this shr i * 8).toUByte()
//                    for (i in 0 until baLength) ba[baLength - i - 1] = (this shr (baLength - i - 1) * 8).toUByte()
        }
    }
    return ba
}

fun UByteArray.toUInt(
    offset: Int = 0,
    length: Int = UInt.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN
): UInt {
    require(length in 1..4)
    require(offset + length <= size)

    var i = 0u

    when (byteOrder) {
        BIG_ENDIAN -> {
            if (length > 3) i = i or (this[offset + length - 4].toUInt() shl 24)
            if (length > 2) i = i or (this[offset + length - 3].toUInt() shl 16)
            if (length > 1) i = i or (this[offset + length - 2].toUInt() shl 8)
            i = i or this[offset + length - 1].toUInt()
        }

        LITTLE_ENDIAN -> {
            if (length > 3) i = i or (this[offset + length - 1].toUInt() shl 24)
            if (length > 2) i = i or (this[offset + length - 2].toUInt() shl 16)
            if (length > 1) i = i or (this[offset + length - 3].toUInt() shl 8)
            i = i or this[offset + length - 4].toUInt()
        }
    }

    return i

}

fun UByteArray.toULong(
    offset: Int = 0,
    length: Int = ULong.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN
): ULong {
    require(length in 1..8)
    require(offset + length <= size)

    var uLong = 0uL

    when (byteOrder) {
        BIG_ENDIAN -> {
            for (i in 0..<length) {
                val a = this[offset + i].toULong()
                val b = (length - 1 - i) * 8
                uLong = uLong or (a shl b)
            }
        }

        LITTLE_ENDIAN -> {
            for (i in 0..<length) {
                val a = this[offset + i].toULong()
                val b = i * 8
                uLong = uLong or (a shl b)
            }
        }
    }

    return uLong

}

fun UByteArray.readUInt(
    offset: Int = 0,
    length: Int = UInt.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN
): Result<UInt> = try {

    require(length in 1..UInt.SIZE_BYTES)

    readULong(offset, length, byteOrder).map { it.toUInt() }

} catch (e: Exception) {

    Result.failure(e)
}

fun UByteArray.readULong(
    offset: Int = 0,
    length: Int = ULong.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN
): Result<ULong> = try {

    require(length in 1..ULong.SIZE_BYTES)
    require(offset + length <= size)

    var i = 0uL

    when (byteOrder) {
        BIG_ENDIAN -> for (j in 0 until length) {
            i = i or ((this[offset + j].toULong() shl (length - j - 1) * 8))
        }

        LITTLE_ENDIAN -> for (j in 0 until length) {
            i = i or (this[offset + j].toULong() shl j * 8)
        }
    }

    Result.success(i)

} catch (e: Exception) {

    Result.failure(e)
}

fun UInt.copyInto(
    ba: UByteArray,
    offset: Int = 0,
    length: Int = UInt.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN,
): Int {
    // The minimum number of bytes needed to encode this integer
    val minBytes = (UInt.SIZE_BYTES - (this.countLeadingZeroBits() / 8)).let { if (it == 0) 1 else it }
    require(minBytes <= length)
    val encodingLength = if (length == 0) minBytes else length

    require(offset + encodingLength <= ba.size)

    when (byteOrder) {
        BIG_ENDIAN -> {
            if (encodingLength > 3) ba[offset + encodingLength - 4] = (this shr 24).toUByte()
            if (encodingLength > 2) ba[offset + encodingLength - 3] = (this shr 16).toUByte()
            if (encodingLength > 1) ba[offset + encodingLength - 2] = (this shr 8).toUByte()
            ba[offset + length - 1] = (this).toUByte()
        }

        LITTLE_ENDIAN -> {
            if (encodingLength > 3) ba[offset + 3] = (this shr 24).toUByte()
            if (encodingLength > 2) ba[offset + 2] = (this shr 16).toUByte()
            if (encodingLength > 1) ba[offset + 1] = (this shr 8).toUByte()
            ba[offset] = (this).toUByte()
        }
    }

    return offset + encodingLength
}

fun ULong.copyInto(
    ba: UByteArray,
    offset: Int = 0,
    length: Int = ULong.SIZE_BYTES,
    byteOrder: ByteOrder = BIG_ENDIAN,
): Int {
    // The minimum number of bytes needed to encode this integer
    val minBytes = (ULong.SIZE_BYTES - (this.countLeadingZeroBits() / 8)).let { if (it == 0) 1 else it }
    require(minBytes <= length)
    val encodingLength = if (length == 0) minBytes else length

    require(offset + encodingLength <= ba.size)

    when (byteOrder) {
        BIG_ENDIAN -> {
            if (encodingLength > 7) ba[offset + encodingLength - 8] = (this shr 56).toUByte()
            if (encodingLength > 6) ba[offset + encodingLength - 7] = (this shr 48).toUByte()
            if (encodingLength > 5) ba[offset + encodingLength - 6] = (this shr 40).toUByte()
            if (encodingLength > 4) ba[offset + encodingLength - 5] = (this shr 32).toUByte()
            if (encodingLength > 3) ba[offset + encodingLength - 4] = (this shr 24).toUByte()
            if (encodingLength > 2) ba[offset + encodingLength - 3] = (this shr 16).toUByte()
            if (encodingLength > 1) ba[offset + encodingLength - 2] = (this shr 8).toUByte()
            ba[offset + length - 1] = (this).toUByte()
        }

        LITTLE_ENDIAN -> {
            if (encodingLength > 7) ba[offset + 7] = (this shr 56).toUByte()
            if (encodingLength > 6) ba[offset + 6] = (this shr 48).toUByte()
            if (encodingLength > 5) ba[offset + 5] = (this shr 40).toUByte()
            if (encodingLength > 4) ba[offset + 4] = (this shr 32).toUByte()
            if (encodingLength > 3) ba[offset + 3] = (this shr 24).toUByte()
            if (encodingLength > 2) ba[offset + 2] = (this shr 16).toUByte()
            if (encodingLength > 1) ba[offset + 1] = (this shr 8).toUByte()
            ba[offset] = (this).toUByte()
        }
    }

    return offset + encodingLength
}
