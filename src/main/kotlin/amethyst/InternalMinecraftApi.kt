package amethyst

/**
 * Denotes an API used internally by Minecraft. These APIs are generally unstable in their implementation,
 * and should be avoided whenever possible.
 */
@RequiresOptIn(message = "This is an internal Minecraft API provided by Amethyst that is not guaranteed to be backwards-compatible.")
annotation class InternalMinecraftApi