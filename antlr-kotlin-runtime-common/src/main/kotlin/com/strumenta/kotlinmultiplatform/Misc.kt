package com.strumenta.kotlinmultiplatform

import kotlin.reflect.KClass

expect fun <T> Array<T>.indices(): List<Int>

expect fun IntArray.indices(): List<Int>

expect fun String.toCharArray(): CharArray

expect class RuntimeException(message: String) : Throwable

expect fun assert(condition: Boolean)

expect object Arrays {
    fun <T> asList(vararg elements: T): List<T>

    fun <T> copyOf(original: Array<T>, size: Int): Array<T>

    fun toString(a: Array<*>): String
    fun toString(a: IntArray): String
}

fun <T> arrayEquals(a: Array<T>?, b: Array<T>?): Boolean {
    return when {
        a == null && b == null -> true
        a != null && b != null -> a.contentEquals(b)
        else -> false
    }
}

fun arrayEquals(a: IntArray?, b: IntArray?): Boolean {
    return when {
        a == null && b == null -> true
        a != null && b != null -> a.contentEquals(b)
        else -> false
    }
}

expect class BitSet {
    constructor()

    fun set(bitIndex: Int)
    fun clear(bitIndex: Int)
    fun get(bitIndex: Int): Boolean
    fun cardinality(): Int
    fun nextSetBit(i: Int): Int
    fun or(alts: BitSet)
}

expect object Math {

    fun min(a: Int, b: Int): Int

    fun max(a: Int, b: Int): Int

    fun floor(d: Double): Double
}

expect fun isCharUppercase(firstChar: Char): Boolean

expect fun isCharLowerCase(firstChar: Char): Boolean


//expect open class CopyOnWriteArrayList<T> : MutableList<T> {
//
//}

expect class NullPointerException : Throwable {
    constructor()
    constructor(message: String)
}

expect class WeakHashMap<K, V> : MutableMap<K, V> {
    constructor()
}

expect class IdentityHashMap<K, V> : MutableMap<K, V> {
    constructor()
}

expect class UUID {
    constructor(most: Long, least: Long)

    companion object {
        fun fromString(encoded: String): UUID
    }
}

expect fun errMessage(message: String)
fun outMessage(message: String) {
    println(message)
}

expect fun Char.Companion.isSupplementaryCodePoint(codePoint: Int): Boolean

expect fun Char.Companion.toChars(codePoint: Int, resultArray: CharArray, resultIdx: Int): Int

expect fun Char.Companion.charCount(i: Int): Byte

expect fun Char.Companion.maxValue(): Char

expect fun <T> arraycopy(src: Array<T>, srcPos: Int, dest: Array<T>, destPos: Int, length: Int)
expect fun arraycopy(src: IntArray, srcPos: Int, dest: IntArray, destPos: Int, length: Int)
