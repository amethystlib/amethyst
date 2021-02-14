package amethyst.net.packet.status

import amethyst.net.ConnectionState
import amethyst.net.PacketWriter
import amethyst.net.packet.Packet
import amethyst.net.packet.PacketDescriptor
import amethyst.net.packet.PacketDirection

class StatusResponsePacket(val json: String) : Packet<StatusResponsePacket>(Companion)  {

    companion object : PacketDescriptor<StatusResponsePacket>(0x00, ConnectionState.Status, PacketDirection.CLIENT) {

        override fun write(packet: StatusResponsePacket, writer: PacketWriter) = writer.writeString(packet.json)
    }
}