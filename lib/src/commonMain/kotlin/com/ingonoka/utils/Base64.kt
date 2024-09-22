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
 * The alphabet of Base64 according to https://tools.ietf.org/html/rfc4648
 */
val alphabet = byteArrayOf(
    // A B C D E F  G H I J K L M N O P Q R S T U V W X Y Z
    65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
    // a b c d e f g h i j k l m n o p q r s t u v w x
    97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120,
    // y z
    121, 122,
    // 0 1 2 3 4 5 6 7 8 9
    48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
    // + /
    43, 47
)

val indicesOne = intArrayOf(
    // 43x
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1,
    // +
    62,
    //
    -1, -1, -1,
    // /
    63,
    // 0 1 2 3 4 5 6 7 8 9 
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
    // 3x
    -1, -1, -1,
    // =
    0,
    // 3x
    -1, -1, -1,
    // A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 
    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
    //
    -1, -1, -1, -1, -1, -1,
    // a b c d e f g h i j k l m n o p q r s t u v w x y z 
    26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,
    // 5x
    -1, -1, -1, -1, -1,
    // 127x
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1
)

//val indicesFour by lazy { indices.map { if(it != -1) it shl 18 else -1}.toIntArray() }

val indicesFour = intArrayOf(
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, 0xf80000, -1, -1, -1, 0xfc0000, 0xd00000, 0xd40000, 0xd80000, 0xdc0000,
    0xe00000, 0xe40000, 0xe80000, 0xec0000, 0xf00000, 0xf40000, -1, -1, -1, 0x0, -1,
    -1, -1, 0x0, 0x40000, 0x80000, 0xc0000, 0x100000, 0x140000, 0x180000, 0x1c0000,
    0x200000, 0x240000, 0x280000, 0x2c0000, 0x300000, 0x340000, 0x380000, 0x3c0000,
    0x400000, 0x440000, 0x480000, 0x4c0000, 0x500000, 0x540000, 0x580000, 0x5c0000,
    0x600000, 0x640000, -1, -1, -1, -1, -1, -1, 0x680000, 0x6c0000, 0x700000, 0x740000,
    0x780000, 0x7c0000, 0x800000, 0x840000, 0x880000, 0x8c0000, 0x900000, 0x940000, 0x980000,
    0x9c0000, 0xa00000, 0xa40000, 0xa80000, 0xac0000, 0xb00000, 0xb40000, 0xb80000, 0xbc0000,
    0xc00000, 0xc40000, 0xc80000, 0xcc0000, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
)

//val indicesThree by lazy { indices.map { if(it != -1) it shl 12  else -1}.toIntArray() }

val indicesThree = intArrayOf(
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0x3e000,
    -1, -1, -1, 0x3f000, 0x34000, 0x35000, 0x36000, 0x37000, 0x38000, 0x39000, 0x3a000, 0x3b000,
    0x3c000, 0x3d000, -1, -1, -1, 0x0, -1, -1, -1, 0x0, 0x1000, 0x2000, 0x3000, 0x4000, 0x5000,
    0x6000, 0x7000, 0x8000, 0x9000, 0xa000, 0xb000, 0xc000, 0xd000, 0xe000, 0xf000, 0x10000,
    0x11000, 0x12000, 0x13000, 0x14000, 0x15000, 0x16000, 0x17000, 0x18000, 0x19000, -1, -1,
    -1, -1, -1, -1, 0x1a000, 0x1b000, 0x1c000, 0x1d000, 0x1e000, 0x1f000, 0x20000, 0x21000, 0x22000,
    0x23000, 0x24000, 0x25000, 0x26000, 0x27000, 0x28000, 0x29000, 0x2a000, 0x2b000, 0x2c000, 0x2d000,
    0x2e000, 0x2f000, 0x30000, 0x31000, 0x32000, 0x33000, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
)

//val indicesTwo by lazy { indices.map { if(it != -1) it shl 6  else -1}.toIntArray() }

val indicesTwo = intArrayOf(
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0xf80, -1,
    -1, -1, 0xfc0, 0xd00, 0xd40, 0xd80, 0xdc0, 0xe00, 0xe40, 0xe80, 0xec0, 0xf00, 0xf40, -1, -1,
    -1, 0x0, -1, -1, -1, 0x0, 0x40, 0x80, 0xc0, 0x100, 0x140, 0x180, 0x1c0, 0x200, 0x240, 0x280,
    0x2c0, 0x300, 0x340, 0x380, 0x3c0, 0x400, 0x440, 0x480, 0x4c0, 0x500, 0x540, 0x580, 0x5c0,
    0x600, 0x640, -1, -1, -1, -1, -1, -1, 0x680, 0x6c0, 0x700, 0x740, 0x780, 0x7c0, 0x800, 0x840,
    0x880, 0x8c0, 0x900, 0x940, 0x980, 0x9c0, 0xa00, 0xa40, 0xa80, 0xac0, 0xb00, 0xb40, 0xb80,
    0xbc0, 0xc00, 0xc40, 0xc80, 0xcc0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1
)

/**
 * Convert a string of UTF-8 characters into Base64
 */
fun String.toBase64() = toUtf8().toBase64().decodeToString()

/**
 * Convert an array of bytes into base64
 */

fun ByteArray.toBase64(): ByteArray {

    if (size == 0) return byteArrayOf()

    val resultSize = ((size + 2) / 3) * 4

    val resultBuffer = ByteArray(resultSize)

    var inIndex = 0
    var outIndex = 0

    while (inIndex < size) {

        when {
            size - inIndex > 2 -> {
                val numInt =
                    ((get(inIndex++).toInt() and 0xFF) shl 16) or ((get(inIndex++).toInt() and 0xFF) shl 8) or (get(
                        inIndex++
                    ).toInt() and 0xFF)
                resultBuffer[outIndex++] = alphabet[numInt ushr 18]
                resultBuffer[outIndex++] = alphabet[(numInt ushr 12) and 0x3F]
                resultBuffer[outIndex++] = alphabet[(numInt ushr 6) and 0x3F]
                resultBuffer[outIndex++] = alphabet[numInt and 0x3F]
            }

            size - inIndex > 1 -> {
                val numInt = ((get(inIndex++).toInt() and 0xFF) shl 16) or ((get(inIndex++).toInt() and 0xFF) shl 8)
                resultBuffer[outIndex++] = alphabet[numInt ushr 18]
                resultBuffer[outIndex++] = alphabet[(numInt ushr 12) and 0x3F]
                resultBuffer[outIndex++] = alphabet[(numInt ushr 6) and 0x3F]

                resultBuffer[outIndex++] = 61
            }

            size - inIndex == 1 -> {
                val numInt = ((get(inIndex++).toInt() and 0xFF) shl 16)
                resultBuffer[outIndex++] = alphabet[numInt ushr 18]
                resultBuffer[outIndex++] = alphabet[(numInt ushr 12) and 0x3F]

                resultBuffer[outIndex++] = 61
                resultBuffer[outIndex++] = 61
            }
        }
    }

    return resultBuffer
}

/**
 * Convert a string of bytes available from a [PoorMansByteBuffer] into base64
 */
fun PoorMansByteBuffer.toBase64(): ByteArray = toByteArray().toBase64()

fun String.fromBase64(): ByteArray {

    require(length % 4 == 0)

    if (length == 0) return byteArrayOf()

    var resultSize = (length / 4) * 3

    if (get(length - 1) == '=') {
        resultSize--
        if (get(length - 2) == '=') {
            resultSize--
        }
    }

    val resultBuffer = ByteArray(resultSize)

    var inIndex = 0
    var outIndex = 0

    while (inIndex < length) {

        val firstCharacter = get(inIndex++).code
        val secondCharacter = get(inIndex++).code
        val thirdCharacter = get(inIndex++).code
        val fourthCharacter = get(inIndex++).code

        val numInt =
            indicesFour[firstCharacter] or indicesThree[secondCharacter] or indicesTwo[thirdCharacter] or indicesOne[fourthCharacter]

        if (fourthCharacter != 0x3D) {
            resultBuffer[outIndex++] = ((numInt ushr 16) and 0xFF).toByte()
            resultBuffer[outIndex++] = ((numInt ushr 8) and 0xFF).toByte()
            resultBuffer[outIndex++] = (numInt and 0xFF).toByte()
        } else {
            if (thirdCharacter != 0x3D) {
                resultBuffer[outIndex++] = ((numInt ushr 16) and 0xFF).toByte()
                resultBuffer[outIndex++] = ((numInt ushr 8) and 0xFF).toByte()
            } else {
                resultBuffer[outIndex++] = ((numInt ushr 16) and 0xFF).toByte()
            }
        }
    }

    return resultBuffer
}

/**
 * Convert an array of Base64 characters to a [ByteArray]
 */
fun ByteArray.fromBase64(): ByteArray {

    require(size % 4 == 0)

    if (size == 0) return byteArrayOf()

    var resultSize = (size / 4) * 3

    if (get(size - 1) == 0x3D.toByte()) {
        resultSize--
        if (get(size - 2) == '='.code.toByte()) {
            resultSize--
        }
    }
    val resultBuffer = ByteArray(resultSize)

    var inIndex = 0
    var outIndex = 0

    while (inIndex < size) {

        val firstCharacter = get(inIndex++).toInt()
        val secondCharacter = get(inIndex++).toInt()
        val thirdCharacter = get(inIndex++).toInt()
        val fourthCharacter = get(inIndex++).toInt()

        val numInt =
            indicesFour[firstCharacter] or indicesThree[secondCharacter] or indicesTwo[thirdCharacter] or indicesOne[fourthCharacter]

        if (fourthCharacter != 0x3D) {
            resultBuffer[outIndex++] = ((numInt ushr 16) and 0xFF).toByte()
            resultBuffer[outIndex++] = ((numInt ushr 8) and 0xFF).toByte()
            resultBuffer[outIndex++] = (numInt and 0xFF).toByte()
        } else {
            if (thirdCharacter != 0x3D) {
                resultBuffer[outIndex++] = ((numInt ushr 16) and 0xFF).toByte()
                resultBuffer[outIndex++] = ((numInt ushr 8) and 0xFF).toByte()
            } else {
                resultBuffer[outIndex++] = ((numInt ushr 16) and 0xFF).toByte()
            }
        }
    }

    return resultBuffer
}


/**
 * Convert Base64 characters from a [PoorMansByteBuffer] into the original bytes
 */
fun PoorMansByteBuffer.fromBase64(): ByteArray = toByteArray().fromBase64()

fun Char.isBase64(): Boolean = (alphabet.find { it.toInt().toChar() == this } != null) || this == '='

fun String.isBase64(): Boolean = (length % 4 == 0) && (all { it.isBase64() })


