# Module

Useful functions and classes:

- Luhn checksum
- Base64
- Singleton holder
- Int/Long conversion to and from byte arrays

## Luhn checksum

```kotlin
"7992739871".luhnCheckSum() // 3
listOf(7, 9, 9, 2, 7, 3, 9, 8, 7, 1).luhnCheckSum() // 3
7992739871L.luhnCheckSum() // 3
"79927398713".validateLuhnChecksum() // true
listOf(7, 9, 9, 2, 7, 3, 9, 8, 7, 1, 3).validateLuhnChecksum() // true
79927398713.validateLuhnChecksum() // true
```

## Singleton Holder

Make a class a singleton

```kotlin
class Manager private constructor(context: Context) {
   init {
     context.toString()
   }

   companion object : SingletonHolder<Manager, Context>(::Manager)
 }
```

The singleton must be initialized before it can be retrieved without the parameter

```kotlin
Manager.initialize(context) // returns the singleton
Manager.instance(context) // returns the singleton
```

Retrieve singleton without parameter
```kotlin
Manager.instance() // throws an exception if niot initialised at lest once with the parameter
Manager.instance(context) // parameter will be ignored if initialised already
```


## Base64

Convert from Base64 string to byte array
```kotlin
"Zg==".decodeFromBase64() // [ 102 ]
```

Convert from byte array to Base64 string
```kotlin
byteArrayOf(102).toBase64() // "Zg=="
```

## Minimum byte array number conversion

The functions in this class convert numbers to and from byte arrays that do not use leading
zero bytes to indicate positive numbers.  For example `0x80` is converted to 128 and 
not to -127.  Likewise an Integer 128 is converted to `0x80` and not to `0x00000080`.

```kotlin
assertEquals(128L, byteArrayOf(0x80.toByte()).toLongNoLeadingZeros())
assertEquals(128L, ByteReadPacket(byteArrayOf(0x80.toByte())).readLongNoLeadingZeros(1))

assertEquals(128, byteArrayOf(0x80.toByte()).toIntNoLeadingZeros())
assertEquals(128, ByteReadPacket(byteArrayOf(0x80.toByte())).readIntNoLeadingZeros(1))

assertTrue(128.toByteArrayWithoutLeadingZeros().contentEquals(byteArrayOf(0x80.toByte())))
assertTrue(128u.toByteArrayWithoutLeadingZeros().contentEquals(uByteArrayOf(0x80.toUByte())))
assertTrue(128L.toByteArrayWithoutLeadingZeros().contentEquals(byteArrayOf(0x80.toByte())))
assertTrue(128uL.toByteArrayWithoutLeadingZeros().contentEquals(byteArrayOf(0x80.toByte())))

assertTrue(buildPacket { writeIntNoLeadingZeros(128) }.readBytes().contentEquals(byteArrayOf(0x80.toByte())))
assertTrue(buildPacket { writeLongNoLeadingZeros(128L) }.readBytes().contentEquals(byteArrayOf(0x80.toByte())))



```


