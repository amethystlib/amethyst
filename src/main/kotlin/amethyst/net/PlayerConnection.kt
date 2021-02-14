package amethyst.net

import amethyst.InternalMinecraftApi
import amethyst.net.packet.Packet
import amethyst.net.packet.handshake.HandshakePacket
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow

const val DEFAULT_PORT = 25565

/**
 * Represents a gameplay session between the server and the client.
 */
@OptIn(InternalMinecraftApi::class)
class PlayerConnection(private val socket: Socket) {

    /**
     * A flow of packets coming in from the client.
     */
    val incoming = MutableSharedFlow<Packet<*>>()

    /**
     * A flow of packets sent out to the client.
     */
    val outgoing = MutableSharedFlow<Packet<*>>()

    private val readChannel: ByteReadChannel
        by lazy { socket.openReadChannel() }
    private val writeChannel: ByteWriteChannel
        by lazy { socket.openWriteChannel() }

    /**
     * Reads a single packet from the socket read channel.
     */
    suspend fun readAsync() = GlobalScope.async {
        val size = readChannel.readVarInt()
        val reader = PacketReader(readChannel.readPacket(size))

        val packetId = reader.readVarInt()
        println(packetId)
        HandshakePacket.read(reader)
    }
}

//fun main() {
//    val executor = Executors.newCachedThreadPool()
//    val selector = ActorSelectorManager(executor.asCoroutineDispatcher())
//
//    val server = aSocket(selector).tcp().bind(port = DEFAULT_PORT)
//    while (true) {
//        println("Starting session...")
//        NetworkSession(server).start()
//    }
//}
