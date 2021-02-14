package amethyst.net

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.*
import io.ktor.util.collections.*
import io.ktor.util.network.*
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

class NetworkSession(address: NetworkAddress) {

    private val server: ServerSocket
    @OptIn(KtorExperimentalAPI::class)
    private val connections = ConcurrentList<PlayerConnection>()

    init {
        val executor = Executors.newCachedThreadPool()
        val selectorManager = ActorSelectorManager(executor.asCoroutineDispatcher())

        server = aSocket(selectorManager).tcp().bind(address)
    }

    suspend fun start(): PlayerConnection {
        val connection = PlayerConnection(server.accept())
        connections += connection
        println(connection.readAsync().await())
        return connection
    }
}