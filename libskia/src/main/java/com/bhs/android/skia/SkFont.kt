package com.bhs.android.skia

import android.graphics.RectF
import java.io.File

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
    enum class FontStyle {
        NORMAL,
        BOLD,
        ITALIC,
        BOLD_ITALIC
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

    constructor(file: File, index: Int = 0) {
        nativePtr = nNew2(file.absolutePath, index)
    }

    constructor(familyName: String, style: FontStyle = FontStyle.NORMAL) {
        nativePtr = nNew3(familyName, style.ordinal)
    }

    fun release() {
        if (nativePtr != 0L) {
            nFree(nativePtr)
            nativePtr = 0L
        }
    }

    private fun checkPtr() {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkFont already released!")
        }
    }

    fun setSize(size: Float) {
        checkPtr()
        nSetSize(nativePtr, size)
    }

    fun setScaleX(scaleX: Float) {
        checkPtr()
        nSetScaleX(nativePtr, scaleX)
    }

    fun setSkewX(skewX: Float) {
        checkPtr()
        nSetSkewX(nativePtr, skewX)
    }

    fun setEdging(edging: Edging) {
        checkPtr()
        nSetEdging(nativePtr, edging.ordinal)
    }

    fun setHinting(hinting: Hinting) {
        checkPtr()
        nSetHinting(nativePtr, hinting.ordinal)
    }

    // TODO 实现多行文字的测量
    private val measureOutput = FloatArray(5)
    fun measureText(text: String, paint: SkPaint?, outBounds: RectF): Float {
        checkPtr()
        nMeasureText(nativePtr, text, paint?.nativePtr?:0L, measureOutput)
        outBounds.set(measureOutput[1], measureOutput[2], measureOutput[3], measureOutput[4])
        return measureOutput[0]
    }

    private external fun nNew() : Long
    private external fun nNew2(fontFile: String, index: Int) : Long
    private external fun nNew3(familyName: String, style: Int) : Long
    private external fun nFree(nativePtr: Long)
    private external fun nSetSize(nativePtr: Long, size: Float)
    private external fun nSetScaleX(nativePtr: Long, scaleX: Float)
    private external fun nSetSkewX(nativePtr: Long, scaleX: Float)
    private external fun nSetEdging(nativePtr: Long, edging: Int)
    private external fun nSetHinting(nativePtr: Long, hinting: Int)
    private external fun nMeasureText(nativePtr: Long, text: String, paintNativePtr: Long, output: FloatArray)
}