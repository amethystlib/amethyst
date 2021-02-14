package amethyst.net

import amethyst.InternalMinecraftApi

/**
 * Represents the current state of a connection. To create a new state,
 * extend `ConnectionState` and register it through the companion.
 */
@OptIn(InternalMinecraftApi::class)
abstract class ConnectionState(val id: Int) {

    /**
     * The initial state of a connection, responsible for
     * sending basic client information to the server.
     */
    object Handshake : ConnectionState(0)

    /**
     * The state immediately after handshake, responsible for
     * sending basic server information to the client.
     */
    object Status : ConnectionState(1)

    /**
     * The state following handshake or status where the client
     * establishes a connection to the server.
     */
    object Login : ConnectionState(2)

    /**
     * The final state, where proper gameplay occurs.
     */
    object Play : ConnectionState(3)

    companion object {

        /**
         * The registry of connection states. To add a new state, insert into the list.
         */
        @InternalMinecraftApi
        val registry = arrayListOf(Handshake, Status, Login, Play)

        /**
         * Returns the state matching the given ID.
         */
        @InternalMinecraftApi
        operator fun get(id: Int) = registry[id]
    }
}
