package amethyst.net.packet

import amethyst.InternalMinecraftApi
import amethyst.net.ConnectionState
import amethyst.net.PacketReader
import amethyst.net.PacketWriter

/**
 * Represents a 
 */
abstract class Packet<T : Packet<T>>(val descriptor: PacketDescriptor<T>)

abstract class PacketDescriptor<T : Packet<T>>(val id: Int, val state: ConnectionState, val direction: PacketDirection) {

    open fun read(reader: PacketReader): T = throw UnsupportedOperationException()
    open fun write(packet: T, writer: PacketWriter): Unit = throw UnsupportedOperationException()
}

@InternalMinecraftApi
class PacketRegistry(val state: ConnectionState, val descriptors: MutableMap<Int, PacketDescriptor<*>>)

enum class PacketDirection {

    SERVER,
    CLIENT
}