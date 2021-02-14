package amethyst.net.packet.handshake

import amethyst.InternalMinecraftApi
import amethyst.net.ConnectionState
import amethyst.net.PacketReader
import amethyst.net.packet.Packet
import amethyst.net.packet.PacketDescriptor
import amethyst.net.packet.PacketDirection

data class HandshakePacket(
    val protocolVersion: Int,
    val address: String,
    val nextState: ConnectionState
) : Packet<HandshakePacket>(Companion) {

    companion object : PacketDescriptor<HandshakePacket>(0x00, ConnectionState.Handshake, PacketDirection.SERVER) {

        @OptIn(InternalMinecraftApi::class)
        override fun read(reader: PacketReader) = HandshakePacket(
            protocolVersion = reader.readVarInt(),
            address = "${reader.readString()}:${reader.readUShort()}",
            nextState = ConnectionState[reader.readVarInt()]
        )
    }
}