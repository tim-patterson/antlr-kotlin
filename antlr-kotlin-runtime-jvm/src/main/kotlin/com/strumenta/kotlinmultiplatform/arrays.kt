package com.strumenta.kotlinmultiplatform

actual fun <T> Array<T>.indices(): List<Int> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

actual fun IntArray.indices(): List<Int> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

actual fun <T> arraycopy(src: Array<T>, srcPos: Int, dest: Array<T>, destPos: Int, length: Int) {
    System.arraycopy(src, srcPos, dest, destPos, length)
}

actual fun arraycopy(src: IntArray, srcPos: Int, dest: IntArray, destPos: Int, length: Int) {
    System.arraycopy(src, srcPos, dest, destPos, length)
}

actual object Arrays {
    actual fun <T> asList(vararg elements: T): List<T> {
        return java.util.Arrays.asList(*elements)
    }
    actual fun <T> copyOf(original: Array<T>, size: Int): Array<T> {
        return java.util.Arrays.copyOf(original, size)
    }

    actual fun toString(a: Array<*>): String {
        return java.util.Arrays.toString(a)
    }

    actual fun toString(a: IntArray): String {
        return java.util.Arrays.toString(a)
    }
}
