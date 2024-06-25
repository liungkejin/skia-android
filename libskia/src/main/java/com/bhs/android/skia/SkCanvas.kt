package com.bhs.android.skia

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF

/**
 * 封装 SkCanvas 类
 * 为了方便使用，默认每个 SkCanvas 对象都会创建一个 SkBitmap 对象
 * 即 SkCanvas 总以 SkBitmap 为目标绘图对象
 * 结束绘制后，可以通过 readBitmap() 方法获取绘制结果
 */
class SkCanvas {
    enum class PointMode {
        POINTS,
        LINES,
        POLYGON
    }

    enum class FilterMode {
        NEAREST,
        LINEAR
    }

    companion object {
        init {
            System.loadLibrary("bhs_skia")
        }
    }

    val width: Int
    val height: Int

    private var nativePtr: Long = 0
    private val mat3x3 = FloatArray(9)

    private var tmpBmp: Bitmap? = null

    constructor(width: Int, height: Int) {
        this.width = width
        this.height = height
        nativePtr = nNew(width, height)
        if (nativePtr == 0L) {
            throw RuntimeException("Failed to create SkCanvas with size: $width x $height")
        }
    }

    constructor(bmp: Bitmap) {
        this.width = bmp.width
        this.height = bmp.height
        nativePtr = nNew2(bmp)
        if (nativePtr == 0L) {
            throw RuntimeException("Failed to create SkCanvas with bitmap: $bmp")
        }
    }

    fun release() {
        if (nativePtr != 0L) {
            nFree(nativePtr)
            nativePtr = 0L
        }
        tmpBmp?.recycle()
        tmpBmp = null
    }

    private fun checkPtr() {
        if (nativePtr == 0L) {
            throw IllegalStateException("Canvas is already released")
        }
    }

    fun save() {
        checkPtr()
        nSave(nativePtr)
    }

    fun restore() {
        checkPtr()
        nRestore(nativePtr)
    }

    fun saveCount() : Int {
        checkPtr()
        return nSaveCount(nativePtr)
    }

    fun restoreToCount(saveCount: Int) {
        checkPtr()
        nRestoreToCount(nativePtr, saveCount)
    }

    fun translate(dx: Float, dy: Float) {
        checkPtr()
        nTranslate(nativePtr, dx, dy)
    }

    fun scale(sx: Float, sy: Float) {
        checkPtr()
        nScale(nativePtr, sx, sy)
    }

    fun rotate(degrees: Float) {
        checkPtr()
        nRotate(nativePtr, degrees)
    }

    fun rotate(degrees: Float, px: Float, py: Float) {
        checkPtr()
        nRotate(nativePtr, degrees, px, py)
    }

    fun skew(sx: Float, sy: Float) {
        checkPtr()
        nSkew(nativePtr, sx, sy)
    }

    fun concat(mt: Matrix) {
        checkPtr()
        mt.getValues(mat3x3)
        nConcat(nativePtr, mat3x3)
    }

    fun setMatrix(mt: Matrix) {
        checkPtr()
        mt.getValues(mat3x3)
        nSetMatrix(nativePtr, mat3x3)
    }

    fun resetMatrix() {
        checkPtr()
        nResetMatrix(nativePtr)
    }

    fun clear(color: Int) {
        checkPtr()
        nClear(nativePtr, color)
    }

    fun clipRect(rectF: RectF, op: SkClipOp, antiAlias: Boolean) {
        checkPtr()
        nClipRect(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, op.ordinal, antiAlias)
    }

    fun clipPath(path: SkPath, op: SkClipOp, antiAlias: Boolean) {
        checkPtr()
        nClipPath(nativePtr, path.nativePtr, op.ordinal, antiAlias)
    }

    fun drawColor(color: Int, blendMode: SkBlendMode = SkBlendMode.SrcOver) {
        checkPtr()
        nDrawColor(nativePtr, color, blendMode.ordinal)
    }

    fun discard() {
        checkPtr()
        nDiscard(nativePtr)
    }

    fun drawPaint(paint: SkPaint) {
        checkPtr()
        nDrawPaint(nativePtr, paint.nativePtr)
    }

    fun drawPoint(x: Float, y: Float, paint: SkPaint) {
        checkPtr()
        nDrawPoint(nativePtr, x, y, paint.nativePtr)
    }

    private var points: FloatArray = FloatArray(0)
    fun drawPoints(mode: PointMode, pts: Collection<PointF>, paint: SkPaint) {
        checkPtr()

        val xysize = pts.size * 2
        if (points.size < xysize) {
            points = FloatArray(xysize)
        }
        var i = 0
        for (p in pts) {
            points[i*2] = p.x
            points[i*2+1] = p.y
            i += 1
        }
        nDrawPoints(nativePtr, mode.ordinal, points, pts.size, paint.nativePtr)
    }

    fun drawLine(x0: Float, y0: Float, x1: Float, y1: Float, paint: SkPaint) {
        checkPtr()
        nDrawLine(nativePtr, x0, y0, x1, y1, paint.nativePtr)
    }

    fun drawRect(rectF: RectF, paint: SkPaint) {
        checkPtr()
        nDrawRect(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, paint.nativePtr)
    }

    fun drawIRect(rect: Rect, paint: SkPaint) {
        checkPtr()
        nDrawIRect(nativePtr, rect.left, rect.top, rect.right, rect.bottom, paint.nativePtr)
    }

    fun drawOval(rectF: RectF, paint: SkPaint) {
        checkPtr()
        nDrawOval(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, paint.nativePtr)
    }

    fun drawCircle(cx: Float, cy: Float, radius: Float, paint: SkPaint) {
        checkPtr()
        nDrawCircle(nativePtr, cx, cy, radius, paint.nativePtr)
    }

    fun drawArc(rectF: RectF, startAngle: Float, sweepAngle: Float, useCenter: Boolean, paint: SkPaint) {
        checkPtr()
        nDrawArc(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, startAngle, sweepAngle, useCenter, paint.nativePtr)
    }

    fun drawRoundRect(rectF: RectF, rx: Float, ry: Float, paint: SkPaint) {
        checkPtr()
        nDrawRoundRect(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, rx, ry, paint.nativePtr)
    }

    fun drawImage(bmp: Bitmap, x: Float, y: Float, filterMode: FilterMode) {
        checkPtr()
        nDrawImage(nativePtr, bmp, x, y, filterMode.ordinal)
    }

    fun drawImageRect(bmp: Bitmap, src: RectF?, dst: RectF, filterMode: FilterMode) {
        checkPtr()

        val srcRect = src?: RectF(0f, 0f, bmp.width.toFloat(), bmp.height.toFloat())
        nDrawImageRect(nativePtr, bmp,
            srcRect.left, srcRect.top, srcRect.right, srcRect.bottom,
            dst.left, dst.top, dst.right, dst.bottom, filterMode.ordinal)
    }

    fun drawImageMatrix(bmp: Bitmap, matrix: Matrix, filterMode: FilterMode) {
        checkPtr()

        save()
        setMatrix(matrix)
        nDrawImage(nativePtr, bmp, 0f, 0f, filterMode.ordinal)
        restore()
    }

    // TODO 实现文字换行，对齐
    fun drawText(text: String, x: Float, y: Float, font: SkFont, paint: SkPaint) {
        checkPtr()

        nDrawText(nativePtr, text, x, y, font.nativePtr, paint.nativePtr)
    }

    fun readBitmap(): Bitmap? {
        if (tmpBmp == null) {
            tmpBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        if (!readBitmap(tmpBmp!!)) {
            return null
        }
        return tmpBmp
    }

    fun readBitmap(bmp: Bitmap): Boolean {
        checkPtr()
        return nReadPixels(nativePtr, bmp)
    }

    private external fun nNew(width: Int, height: Int): Long
    private external fun nNew2(bmp: Bitmap): Long
    private external fun nFree(nativePtr: Long)

    private external fun nSave(nativePtr: Long)
    private external fun nRestore(nativePtr: Long)
    private external fun nSaveCount(nativePtr: Long) : Int
    private external fun nRestoreToCount(nativePtr: Long, saveCount: Int)
    private external fun nTranslate(nativePtr: Long, dx : Float, dy : Float)
    private external fun nScale(nativePtr: Long, sx: Float, sy: Float)
    private external fun nRotate(nativePtr: Long, degrees: Float)
    private external fun nRotate(nativePtr: Long, degrees: Float, px: Float, py: Float)
    private external fun nSkew(nativePtr: Long, sx: Float, sy: Float)
    private external fun nConcat(nativePtr: Long, mat3x3: FloatArray)
    private external fun nSetMatrix(nativePtr: Long, mat3x3: FloatArray)
    private external fun nResetMatrix(nativePtr: Long)

    private external fun nClear(nativePtr: Long, color: Int)
    private external fun nDrawColor(nativePtr: Long, color: Int, blendMode: Int)
    private external fun nDiscard(nativePtr: Long)

    private external fun nClipRect(nativePtr: Long, left: Float, top: Float, right: Float, bottom: Float, op: Int, antiAlias: Boolean)
    private external fun nClipPath(nativePtr: Long, pathNativePtr: Long, op: Int, antiAlias: Boolean)

    private external fun nDrawPaint(nativePtr: Long, paintNativePtr: Long)
    private external fun nDrawPoints(nativePtr: Long, mode: Int, pts: FloatArray, pointCount: Int, paintNativePtr: Long)
    private external fun nDrawPoint(nativePtr: Long, x: Float, y: Float, paintNativePtr: Long)
    private external fun nDrawLine(nativePtr: Long, x0: Float, y0: Float, x1: Float, y1: Float, paintNativePtr: Long)
    private external fun nDrawRect(nativePtr: Long, left: Float, top: Float, right: Float, bottom: Float, paintNativePtr: Long)
    private external fun nDrawIRect(nativePtr: Long, left: Int, top: Int, right: Int, bottom: Int, paintNativePtr: Long)
    private external fun nDrawOval(nativePtr: Long, left: Float, top: Float, right: Float, bottom: Float, paintNativePtr: Long)
    private external fun nDrawCircle(nativePtr: Long, cx: Float, cy: Float, radius: Float, paintNativePtr: Long)
    private external fun nDrawArc(nativePtr: Long, left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float, useCenter: Boolean, paintNativePtr: Long)
    private external fun nDrawRoundRect(nativePtr: Long, left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, paintNativePtr: Long)

    private external fun nDrawImage(nativePtr: Long, img: Bitmap, x: Float, y: Float, filterMode: Int)
    private external fun nDrawImageRect(nativePtr: Long, img: Bitmap,
                                        srcLeft: Float, srcTop: Float, srcRight: Float, srcBottom: Float,
                                        dstLeft: Float, dstTop: Float, dstRight: Float, dstBottom: Float, filterMode: Int)
//    private external fun nDrawImageMatrix(nativePtr: Long, img: Bitmap, mat3x3: FloatArray, paintNativePtr: Long)

    private external fun nDrawText(nativePtr: Long, text: String, x: Float, y: Float, fontNativePtr: Long, paintNativePtr: Long)

    private external fun nReadPixels(nativePtr: Long, bmp: Bitmap) : Boolean
}