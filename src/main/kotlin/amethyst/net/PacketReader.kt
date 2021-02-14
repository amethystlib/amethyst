package amethyst.net

import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import java.util.*
import kotlin.experimental.and
import kotlin.reflect.KClass

/**
 * Decodes values from the provided channel.
 */
@OptIn(ExperimentalUnsignedTypes::class)
class PacketReader(private val packet: ByteReadPacket) {

    /**
     * Reads a byte from the channel.
     */
    fun readByte(): Byte {
        return packet.readByte()
    }

    /**
     * Reads an unsigned byte from the channel.
     */
    fun readUByte(): UByte {
        return packet.readUByte()
    }

    /**
     * Reads a short form the channel.
     */
    fun readShort(): Short {
        return packet.readShort()
    }

    /**
     * Reads an unsigned short from the channel.
     */
    fun readUShort(): UShort {
        return readUShort()
    }

    /**
     * Reads an integer from the channel. This type is not generally used by Minecraft, as
     * the [VarInt encoding](https://wiki.vg/Protocol#Varint-and-Varlong) is more space efficient.
     */
    fun readInt(): Int {
        return packet.readInt()
    }

    /**
     * Reads a long from the channel. This type is not generally used by Minecraft, as
     * the [VarInt encoding](https://wiki.vg/Protocol#Varint-and-Varlong) is more space efficient.
     */
    fun readLong(): Long {
        return packet.readLong()
    }

    /**
     * Reads a float from the channel.
     */
    fun readFloat(): Float {
        return packet.readFloat()
    }

    /**
     * Reads a double from the channel.
     */
    fun readDouble(): Double {
        return packet.readDouble()
    }

    /**
     * Reads a variable length integer from the channel.
     */
    @Suppress("DuplicatedCode") // Can't really extract the duplicate from the channel method because it's suspending
    fun readVarInt(): Int {
        var count = 0
        var result = 0

        var value: Byte
        do {
            value = packet.readByte()
            result = result or ((value and 0b01111111).toInt() shl (7 * count))

            count++
            if (count > 5) throw IllegalArgumentException("Oversize VarInt")
        } while ((value and 0b10000000.toByte()) != 0.toByte())

        return result
    }

    /**
     * Reads a variable length long from the channel.
     */
    fun readVarLong(): Long {
        var count = 0
        var result = 0L

        var value: Byte
        do {
            value = packet.readByte()
            result = result or ((value and 0b01111111).toLong() shl (7 * count))

            count++
            if (count > 10) throw IllegalArgumentException("Oversize VarLong")
        } while ((value and 0b10000000.toByte()) != 0.toByte())

        return result
    }

    /**
     * Reads a string from the channel.
     */
    fun readString(): String {
        return String(packet.readBytes(readVarInt()))
    }

    /**
     * Reads a UUID from the channel.
     */
    fun readUuid(): UUID {
        return UUID.nameUUIDFromBytes(packet.readBytes(16))
    }

    /**
     * Reads an enum from the channel, using its ordinal to find the relevant value.
     */
    fun <T : Enum<T>> readEnum(enumClass: KClass<T>): T {
        return enumClass.java.enumConstants[readVarInt()]
    }
}

/**
 * Reads a variable length integer directly from a byte channel.
 */
@Suppress("DuplicatedCode")
suspend fun ByteReadChannel.readVarInt(): Int {
    var count = 0
    var result = 0

    var value: Byte
    do {
        value = readByte()
        result = result or ((value and 0b01111111).toInt() shl (7 * count))

        count++
        if (count > 5) throw IllegalArgumentException("Oversize VarInt")
    } while ((value and 0b10000000.toByte()) != 0.toByte())

    return result
}
