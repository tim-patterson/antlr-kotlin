package com.strumenta.kotlinmultiplatform

actual class BitSet {

    private var setBits = BooleanArray(80)

    actual fun set(bitIndex: Int) {
        while (bitIndex >= setBits.size) setBits = setBits.copyOf(setBits.size * 2)
        setBits[bitIndex] = true
    }
    actual fun clear(bitIndex: Int) {
        setBits[bitIndex] = false
    }
    actual fun get(bitIndex: Int): Boolean {
        if (bitIndex >= setBits.size) return false
        return setBits[bitIndex]
    }

    actual fun cardinality(): Int {
        return setBits.count { it }
    }

    actual fun nextSetBit(i: Int): Int {
        return setBits.drop(i).indexOfFirst { it }
    }

    actual fun or(alts: BitSet) {
        alts.setBits.forEachIndexed { i, v ->
            if (v) setBits[i] = true
        }
    }

}