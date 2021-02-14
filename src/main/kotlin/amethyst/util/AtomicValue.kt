package amethyst.util

import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class AtomicValue<T>() {

    var value: Any? = null
    var initialized: Boolean = false

    constructor(initialValue: T): this() {
        this.initialized = true
        this.value = initialValue
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value as T

    @Synchronized
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.initialized = true
        this.value = value
    }
}
