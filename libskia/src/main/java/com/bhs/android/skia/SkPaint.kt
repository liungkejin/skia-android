package com.bhs.android.skia

class SkPaint {
    enum class Style {
        FILL,
        STROKE,
        STROKE_AND_FILL
    }

    enum class Cap {
        BUTT,
        ROUND,
        SQUARE
    }

    companion object {
        init {
            System.loadLibrary("bhs_skia")
        }
    }

    internal var nativePtr : Long = 0

    init {
        nativePtr = nNew()
    }

    fun release() {
        if (nativePtr != 0L) {
            nFree(nativePtr)
            nativePtr = 0L
        }
    }

    fun reset() {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released! reset() failed")
        }
        nReset(nativePtr)
    }

    fun isAntiAlias(): Boolean {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        return nIsAntiAlias(nativePtr)
    }

    fun setAntiAlias(antialias: Boolean) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetAntiAlias(nativePtr, antialias)
    }

    fun setStyle(style: Style) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetStyle(nativePtr, style.ordinal)
    }

    fun setColor(color: Int) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetColor(nativePtr, color)
    }

    fun setAlpha(alpha: Float) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetAlpha(nativePtr, alpha)
    }

    fun setStrokeWidth(width: Float) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetStrokeWidth(nativePtr, width)
    }

    fun setStrokeMiter(miter: Float) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetStrokeMiter(nativePtr, miter)
    }

    fun setStrokeCap(cap: Cap) {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPaint already released!")
        }
        nSetStrokeCap(nativePtr, cap.ordinal)
    }


    private external fun nNew() : Long
    private external fun nFree(nativePtr: Long)

    private external fun nReset(nativePtr: Long)
    private external fun nIsAntiAlias(nativePtr: Long): Boolean
    private external fun nSetAntiAlias(nativePtr: Long, antialias: Boolean)

    private external fun nSetStyle(nativePtr: Long, style: Int)

    private external fun nSetColor(nativePtr: Long, color: Int)
    private external fun nSetAlpha(nativePtr: Long, alpha: Float)

    private external fun nSetStrokeWidth(nativePtr: Long, width: Float)
    private external fun nSetStrokeMiter(nativePtr: Long, miter: Float)

    private external fun nSetStrokeCap(nativePtr: Long, cap: Int)
}