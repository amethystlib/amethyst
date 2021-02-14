package amethyst.net.packet.status

import amethyst.net.ConnectionState
import amethyst.net.PacketWriter
import amethyst.net.packet.Packet
import amethyst.net.packet.PacketDescriptor
import amethyst.net.packet.PacketDirection

class StatusPongPacket(val id: Long) : Packet<StatusPongPacket>(Companion) {

    companion object : PacketDescriptor<StatusPongPacket>(0x01, ConnectionState.Status, PacketDirection.CLIENT) {

        override fun write(packet: StatusPongPacket, writer: PacketWriter) = writer.writeVarLong(packet.id)
    }
}