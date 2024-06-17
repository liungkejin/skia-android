package com.bhs.android.skia

class SkFont {
    enum class Edging {
        ALIAS,
        ANTI_ALIAS,
        SUBPIXEL_ANTI_ALIAS
    }
    enum class Hinting {
        NONE,
        SLIGHT,
        NORMAL,
        FULL
    }
    companion object {
        init {
            System.loadLibrary("bhs_skia")
        }
    }

    internal var nativePtr : Long = 0

    constructor() {
        nativePtr = nNew()
    }

    constructor(size: Float) {
        nativePtr = nNewSize(size)
    }

    fun release() {
        if (nativePtr != 0L) {
            nFree(nativePtr)
            nativePtr = 0L
        }
    }

    fun setSize(size: Float) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkFont already released!")
        }
        nSetSize(nativePtr, size)
    }

    fun setScaleX(scaleX: Float) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkFont already released!")
        }
        nSetScaleX(nativePtr, scaleX)
    }

    fun setSkewX(skewX: Float) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkFont already released!")
        }
        nSetSkewX(nativePtr, skewX)
    }

    fun setEdging(edging: Edging) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkFont already released!")
        }
        nSetEdging(nativePtr, edging.ordinal)
    }

    fun setHinting(hinting: Hinting) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkFont already released!")
        }
        nSetHinting(nativePtr, hinting.ordinal)
    }

    private external fun nNew() : Long
    private external fun nNewSize(size: Float) : Long
    private external fun nFree(nativePtr: Long)
    private external fun nSetSize(nativePtr: Long, size: Float)
    private external fun nSetScaleX(nativePtr: Long, scaleX: Float)
    private external fun nSetSkewX(nativePtr: Long, scaleX: Float)
    private external fun nSetEdging(nativePtr: Long, edging: Int)
    private external fun nSetHinting(nativePtr: Long, hinting: Int)
}