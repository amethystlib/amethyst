package amethyst.net.packet.status

import amethyst.net.ConnectionState
import amethyst.net.PacketWriter
import amethyst.net.packet.Packet
import amethyst.net.packet.PacketDescriptor
import amethyst.net.packet.PacketDirection

class StatusPingPacket(val id: Long) : Packet<StatusPingPacket>(Companion) {

    companion object : PacketDescriptor<StatusPingPacket>(0x01, ConnectionState.Status, PacketDirection.CLIENT) {

        override fun write(packet: StatusPingPacket, writer: PacketWriter) = writer.writeVarLong(packet.id)
    }
}