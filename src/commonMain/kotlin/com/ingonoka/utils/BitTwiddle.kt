package com.ingonoka.utils

/** Reverse order of all bits in a Byte.
 * The left-most bit becomes the right most bit etc.
 *
 * ### Attribution
 * "Bit Twiddling Hacks", By Sean Eron Anderson [seander@cs.stanford.edu].
 *
 * Algorithm devised by Sean Anderson, July 13, 2001. Typo spotted and correction supplied by Mike Keith, January 3, 2002.
 *
 * Retrieved from [https://graphics.stanford.edu/~seander/bithacks.html#BitReverseObvious] on Sept 9, 2018


 * @return A new Byte with the reversed bits

 */
@Suppress("SpellCheckingInspection")
fun Byte.reverse(): Byte {

    var b: Int = this.toInt() and 0xFF

    b = ((b * 0x0802 and 0x22110) or (b * 0x8020 and 0x88440)) * 0x10101 ushr 16

    return (b and 0xFF).toByte()

}

/** Shift all bits in the ByteArray to the left by [shiftBitCount] bits
 *
 * [https://stackoverflow.com/a/47231300] by [https://stackoverflow.com/users/774398/for3st]
 */

fun ByteArray.shiftLeft(shiftBitCount: Int): ByteArray {
    val shiftMod = shiftBitCount % 8
    val carryMask = ((1 shl shiftMod) - 1).toByte()
    val offsetBytes = shiftBitCount / 8

    var sourceIndex: Int
    for (i in this.indices) {
        sourceIndex = i + offsetBytes
        if (sourceIndex >= this.size) {
            this[i] = 0
        } else {
            val src = this[sourceIndex]
            var dst = (src.toInt() shl shiftMod).toByte()
            if (sourceIndex + 1 < this.size) {
                dst = (dst.toInt() or (this[sourceIndex + 1].toInt().ushr(
                    8 - shiftMod
                ) and carryMask.toInt())).toByte()
            }
            this[i] = dst
        }
    }
    return this
}



/**
 * Shift all bits in the least significant byte of each integer in the lst to the left by [shiftBitCount] bits.
 *
 * Bits dropping off of the left are lost
 *
 * Each integer is treated like a byte by only ever touching the bits in the least significant byte of the integer
 *
 * [https://stackoverflow.com/a/47231300] by [https://stackoverflow.com/users/774398/for3st]
 */

fun MutableList<Int>.shiftLeft(shiftBitCount: Int): List<Int> {
    val shiftMod = shiftBitCount % 8
    val carryMask = ((1 shl shiftMod) - 1)
    val offsetBytes = shiftBitCount / 8

    var sourceIndex: Int
    for (i in this.indices) {
        sourceIndex = i + offsetBytes
        if (sourceIndex >= this.size) {
            this[i] = 0
        } else {
            val src = this[sourceIndex]
            var dst = (src shl shiftMod) and 0xff
            if (sourceIndex + 1 < this.size) {
                dst = (dst or (this[sourceIndex + 1].ushr(
                    8 - shiftMod
                ) and carryMask)) and 0xff
            }
            this[i] = dst
        }
    }
    return this
}

/** Shift all bits in the ByteArray to the right by [shiftBitCount] bits.
 * The Sign is not carried.
 *
 * [https://stackoverflow.com/a/47231300] by [https://stackoverflow.com/users/774398/for3st]
 **/

fun ByteArray.shiftRight(shiftBitCount: Int): ByteArray {
    val shiftMod = shiftBitCount % 8
    val carryMask = (0xFF shl 8 - shiftMod).toByte()
    val offsetBytes = shiftBitCount / 8

    var sourceIndex: Int
    for (i in this.indices.reversed()) {
        sourceIndex = i - offsetBytes
        if (sourceIndex < 0) {
            this[i] = 0
        } else {
            val src = this[sourceIndex]
            var dst = (0xff and src.toInt()).ushr(shiftMod).toByte()
            if (sourceIndex - 1 >= 0) {
                dst = (dst.toInt() or (this[sourceIndex - 1].toInt().shl(
                    8 - shiftMod
                ) and carryMask.toInt())).toByte()
            }
            this[i] = dst
        }
    }
    return this
}

/**
 * Shift all bits in the least significant byte of each integer in the lst to the right by [shiftBitCount] bits.
 * The Sign is not carried.
 *
 * Bits dropping off of the right are lost
 *
 * Each integer is treated like a byte by only ever touching the bits in the least significant byte of the integer
 *
 * [https://stackoverflow.com/a/47231300] by [https://stackoverflow.com/users/774398/for3st]
 **/

fun MutableList<Int>.shiftRight(shiftBitCount: Int): List<Int> {
    val shiftMod = shiftBitCount % 8
    val carryMask = (0xFF shl 8 - shiftMod)
    val offsetBytes = shiftBitCount / 8

    var sourceIndex: Int
    for (i in this.indices.reversed()) {
        sourceIndex = i - offsetBytes
        if (sourceIndex < 0) {
            this[i] = 0
        } else {
            val src = this[sourceIndex]
            var dst = (0xff and src).ushr(shiftMod)
            if (sourceIndex - 1 >= 0) {
                dst = (dst or (this[sourceIndex - 1].shl(
                    8 - shiftMod
                ) and carryMask)) and 0xff
            }
            this[i] = dst
        }
    }
    return this
}

/**
 * Applies [mutator] to each byte in the ByteArray. The [mutator] returns a Byte that replaces the original Byte in the original [ByteArray]
### Example with [Byte.reverse]

 *       println("Reverse 0x6F: ${0x6F.toByte().reverse().toHex()}")
 *       => Reverse 0x6F: 0xF6
 *       println("Reverse 0x00: ${0x00.toByte().reverse().toHex()}")
 *       => Reverse 0x00: 0x00
 *       println("Reverse 0x01: ${0x01.toByte().reverse().toHex()}")
 *       => Reverse 0x01: 0x80
 *       => println("Reverse 0x80: ${0x80.toByte().reverse().toHex()}")
 *       Reverse 0x80: 0x01
 *       => println("Reverse 0x10: ${0x10.toByte().reverse().toHex()}")
 *       Reverse 0x10: 0x08

 *
 * @param mutator __`(Byte)->Byte`__ Function that takes a Byte as input and returns a Byte
 *
 * @return The original [ByteArray] with all Bytes changed by the [mutator]
 */
inline fun ByteArray.mapInPlace(mutator: (Byte) -> Byte): ByteArray {
    this.forEachIndexed { idx, value ->
        mutator(value).let { newValue ->
            if (newValue != value) this[idx] = mutator(value)
        }
    }
    return this
}


/**
 * Convert a [ByteArray] to a list of Int
 */
fun ByteArray.toListOfInt(): List<Int> = map { it.toInt() }

/**
 * Convert each element of the list to a byte and convert the resulting list of bytes to a byte array.
 */
fun List<Int>.toByteArray(): ByteArray = map { it.toByte() }.toByteArray()