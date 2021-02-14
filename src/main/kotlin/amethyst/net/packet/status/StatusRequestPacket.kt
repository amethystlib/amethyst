package amethyst.net.packet.status

import amethyst.net.ConnectionState
import amethyst.net.PacketReader
import amethyst.net.packet.Packet
import amethyst.net.packet.PacketDescriptor
import amethyst.net.packet.PacketDirection

class StatusRequestPacket : Packet<StatusRequestPacket>(Companion) {

    companion object : PacketDescriptor<StatusRequestPacket>(0x00, ConnectionState.Status, PacketDirection.SERVER) {

        private val instance = StatusRequestPacket()

        override fun read(reader: PacketReader): StatusRequestPacket = instance
    }
}