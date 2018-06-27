package com.strumenta.kotlinmultiplatform

actual fun <T> Array<T>.indices(): List<Int> {
    TODO("Array<T>.indices not implemented") //To change body of created functions use File | Settings | File Templates.
}

actual fun IntArray.indices(): List<Int> {
    TODO("IntArray.indices not implemented") //To change body of created functions use File | Settings | File Templates.
}

actual fun <T> arraycopy(src: Array<T>, srcPos: Int, dest: Array<T>, destPos: Int, length: Int) {
    0.until(length).forEach { i ->
        dest[destPos + i] = src[srcPos + i]
    }
}

actual fun arraycopy(src: IntArray, srcPos: Int, dest: IntArray, destPos: Int, length: Int) {
    0.until(length).forEach { i ->
        dest[destPos + i] = src[srcPos + i]
    }
}

actual object Arrays {

    actual fun <T> asList(vararg elements: T): List<T> {
        return elements.asList()
    }

    actual fun <T> copyOf(original: Array<T>, size: Int): Array<T> {
        val res = original.copyOf(size)
        return res as Array<T>
    }

    actual fun toString(a: Array<*>): String {
        return "[${a.joinToString(separator = ", ") { it?.toString() ?: "null"}}]"
    }

    actual fun toString(a: IntArray): String {
        return "[${a.joinToString(separator = ", ") { it.toString() }}]"
    }
}