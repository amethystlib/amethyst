package amethyst.server

import amethyst.net.DEFAULT_PORT
import amethyst.net.NetworkSession
import io.ktor.util.network.*
import java.net.InetSocketAddress

class Server {

    lateinit var networkSession: NetworkSession

    suspend fun connect(address: NetworkAddress = InetSocketAddress(DEFAULT_PORT)) {
        NetworkSession(address).start()
    }
}

// TODO remove
suspend fun main() {
    Server().connect()
}