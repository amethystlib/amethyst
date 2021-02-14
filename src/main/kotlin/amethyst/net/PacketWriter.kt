package amethyst.net

import io.ktor.utils.io.core.*
import java.util.*

/**
 * Encodes values to the provided packet builder for sending outgoing packets.
 */
@OptIn(ExperimentalUnsignedTypes::class)
class PacketWriter(val builder: BytePacketBuilder) {

    // <editor-fold desc="> Numerical Data Types">

    /**
     * Writes the given byte to the packet builder.
     */
    fun writeByte(value: Byte) {
        builder.writeByte(value)
    }

    /**
     * Writes the given unsigned byte to the packet builder.
     */
    fun writeUByte(value: UByte) {
        builder.writeUByte(value)
    }

    /**
     * Writes the given short to the packet builder.
     */
    fun writeShort(value: Short) {
        builder.writeShort(value, ByteOrder.LITTLE_ENDIAN)
    }

    /**
     * Writes the given unsigned short to the packet builder.
     */
    fun writeUShort(value: UShort) {
        builder.writeShort(value.toShort(), ByteOrder.LITTLE_ENDIAN)
    }

    /**
     * Writes the given int to the packet builder. This type is not generally used by Minecraft, as
     * the [VarInt encoding](https://wiki.vg/Protocol#Varint-and-Varlong) is more space efficient.
     */
    fun writeInt(value: Int) {
        builder.writeInt(value, ByteOrder.LITTLE_ENDIAN)
    }

    /**
     * Writes the given long to the packet builder. This type is not generally used by Minecraft, as
     * the [VarLong encoding](https://wiki.vg/Protocol#Varint-and-Varlong) is more space efficient.
     */
    fun writeLong(value: Long) {
        builder.writeLong(value, ByteOrder.LITTLE_ENDIAN)
    }

    /**
     * Writes the given float to the packet builder.
     */
    fun writeFloat(value: Float) {
        builder.writeFloat(value, ByteOrder.LITTLE_ENDIAN)
    }

    /**
     * Writes the given double to the packet builder.
     */
    fun writeDouble(value: Double) {
        builder.writeDouble(value, ByteOrder.LITTLE_ENDIAN)
    }

    /**
     * Writes the given int to the packet builder with a variable length.
     */
    fun writeVarInt(value: Int) {
        var int = value
        do {
            var toWrite = value and 0b01111111
            int = int ushr 7

            if (int != 0) {
                toWrite = toWrite or 0b10000000
            }

            builder.writeByte(toWrite.toByte())
        } while (int != 0)
    }

    /**
     * Writes the given long to the packet builder with a variable length.
     */
    fun writeVarLong(value: Long) {
        var long = value
        do {
            var toWrite = value and 0b01111111
            long = long ushr 7

            if (long != 0L) {
                toWrite = toWrite or 0b10000000
            }
            writeByte(toWrite.toByte())
        } while (long != 0L)
    }
    // </editor-fold>

    /**
     * Writes the given string to the packet builder, encoding its length as a VarInt.
     */
    fun writeString(value: String) {
        writeVarInt(value.length)
        builder.writeFully(value.toByteArray())
    }

    /**
     * Writes the given UUID to the packet builder as two sequential longs.
     */
    fun writeUuid(value: UUID) {
        writeLong(value.mostSignificantBits)
        writeLong(value.leastSignificantBits)
    }

    /**
     * Writes the ordinal of the given enum to the packet builder.
     */
    fun writeEnum(value: Enum<*>) {
        writeVarInt(value.ordinal)
    }
}
