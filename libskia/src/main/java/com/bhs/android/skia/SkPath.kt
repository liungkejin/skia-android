package com.bhs.android.skia

import android.graphics.RectF

class SkPath() {
    enum class PathFillType {
        WINDING,
        EVEN_ODD,
        INVERSE_WINDING,
        INVERSE_EVEN_ODD
    }

    enum class PathDirection {
        CW, // clockwise
        CCW
    }

    enum class PathSegmentMask {
        LINE,
        QUAD,
        CONIC,
        CUBIC
    }

    enum class PathVerb {
        MOVE,
        LINE,
        QUAD,
        CONIC,
        CUBIC,
        CLOSE
    }

    companion object {
        fun rect(rectF: RectF, direction: PathDirection = PathDirection.CW, startIndex: Int = 0) : SkPath {
            val path = SkPath()
            path.addRect(rectF, direction, startIndex)
            return path
        }

        fun oval(rectF: RectF, direction: PathDirection = PathDirection.CW) : SkPath {
            val path = SkPath()
            path.addOval(rectF, direction)
            return path
        }

        fun circle(x: Float, y: Float, radius: Float, direction: PathDirection = PathDirection.CW) : SkPath {
            val path = SkPath()
            path.addCircle(x, y, radius, direction)
            return path
        }

        fun polygon(points: FloatArray, count: Int,
                    close: Boolean, pathFillType: PathFillType = PathFillType.WINDING) : SkPath {
            val path = SkPath()
            path.addPolygon(points, count, close)
            path.setFillType(pathFillType)
            return path
        }

        fun line(x1: Float, y1: Float, x2: Float, y2: Float) : SkPath {
            val path = SkPath()
            path.moveTo(x1, y1)
            path.lineTo(x2, y2)
            return path
        }
    }

    internal var nativePtr : Long = 0L

    init {
        nativePtr = nNew()
    }

    private fun checkPtr() {
        if (nativePtr == 0L) {
            throw IllegalStateException("SkPath ptr == null!")
        }
    }

    fun setFillType(fillType: PathFillType) {
        checkPtr()
        nSetFillType(nativePtr, fillType.ordinal)
    }

    fun setVolatile(vol: Boolean) {
        checkPtr()
        nSetVolatile(nativePtr, vol)
    }

    fun close() {
        checkPtr()
        nClose(nativePtr)
    }

    fun moveTo(x: Float, y: Float) {
        checkPtr()
        nMoveTo(nativePtr, x, y)
    }

    fun rMoveTo(dx: Float, dy: Float) {
        checkPtr()
        nRMoveTo(nativePtr, dx, dy)
    }

    fun lineTo(x: Float, y: Float) {
        checkPtr()
        nLineTo(nativePtr, x, y)
    }

    fun rLineTo(dx: Float, dy: Float) {
        checkPtr()
        nRLineTo(nativePtr, dx, dy)
    }

    fun quadTo(x1: Float, y1: Float, x2: Float, y2: Float) {
        checkPtr()
        nQuadTo(nativePtr, x1, y1, x2, y2)
    }

    fun rQuadTo(dx1: Float, dy1: Float, dx2: Float, dy2: Float) {
        checkPtr()
        nRQuadTo(nativePtr, dx1, dy1, dx2, dy2)
    }

    fun conicTo(x1: Float, y1: Float, x2: Float, y2: Float, w: Float) {
        checkPtr()
        nConicTo(nativePtr, x1, y1, x2, y2, w)
    }

    fun rConicTo(dx1: Float, dy1: Float, dx2: Float, dy2: Float, w: Float) {
        checkPtr()
        nRConicTo(nativePtr, dx1, dy1, dx2, dy2, w)
    }

    fun cubicTo(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float) {
        checkPtr()
        nCubicTo(nativePtr, x1, y1, x2, y2, x3, y3)
    }

    fun rCubicTo(dx1: Float, dy1: Float, dx2: Float, dy2: Float, dx3: Float, dy3: Float) {
        checkPtr()
        nRCubicTo(nativePtr, dx1, dy1, dx2, dy2, dx3, dy3)
    }

    fun arcTo(l: Float, t: Float, r: Float, b: Float, startAngle: Float, sweepAngle: Float, forceMoveTo: Boolean) {
        checkPtr()
        nArcTo(nativePtr, l, t, r, b, startAngle, sweepAngle, forceMoveTo)
    }

    fun arcTo(x1: Float, y1: Float, x2: Float, y2: Float, radius: Float) {
        checkPtr()
        nArcTo2(nativePtr, x1, y1, x2, y2, radius)
    }

    fun addRect(rectF: RectF, direction: PathDirection, start: Int) {
        checkPtr()
        nAddRect(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, direction.ordinal, start)
    }

    fun addRect(l: Float, t: Float, r: Float, b: Float, direction: PathDirection, start: Int) {
        checkPtr()
        nAddRect(nativePtr, l, t, r, b, direction.ordinal, start)
    }

    fun addOval(rectF: RectF, direction: PathDirection) {
        checkPtr()
        nAddOval(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, direction.ordinal)
    }

    fun addOval(l: Float, t: Float, r: Float, b: Float, direction: PathDirection) {
        checkPtr()
        nAddOval(nativePtr, l, t, r, b, direction.ordinal)
    }

    fun addCircle(x: Float, y: Float, radius: Float, direction: PathDirection) {
        checkPtr()
        nAddCircle(nativePtr, x, y, radius, direction.ordinal)
    }

    fun addArc(rectF: RectF, startAngle: Float, sweepAngle: Float) {
        checkPtr()
        nAddArc(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, startAngle, sweepAngle)
    }

    fun addArc(l: Float, t: Float, r: Float, b: Float, startAngle: Float, sweepAngle: Float) {
        checkPtr()
        nAddArc(nativePtr, l, t, r, b, startAngle, sweepAngle)
    }

    fun addRoundRect(rectF: RectF, rx: Float, ry: Float, dir: Int) {
        checkPtr()
        nAddRoundRect(nativePtr, rectF.left, rectF.top, rectF.right, rectF.bottom, rx, ry, dir)
    }

    fun addRoundRect(l: Float, t: Float, r: Float, b: Float, rx: Float, ry: Float, dir: Int) {
        checkPtr()
        nAddRoundRect(nativePtr, l, t, r, b, rx, ry, dir)
    }

    fun addPolygon(points: FloatArray, count: Int, close: Boolean) {
        checkPtr()
        nAddPolygon(nativePtr, points, count, close)
    }
    

    fun release() {
        if (nativePtr != 0L) {
            nFree(nativePtr)
            nativePtr = 0L
        }
    }

    private external fun nNew() : Long
    private external fun nFree(ptr: Long)

    private external fun nClose(ptr: Long)

    private external fun nSetFillType(ptr: Long, fillType: Int)
    private external fun nSetVolatile(ptr: Long, vol: Boolean)

    private external fun nMoveTo(ptr: Long, x: Float, y: Float)
    private external fun nRMoveTo(ptr: Long, dx: Float, dy: Float)

    private external fun nLineTo(ptr: Long, x: Float, y: Float)
    private external fun nRLineTo(ptr: Long, dx: Float, dy: Float)

    private external fun nQuadTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float)
    private external fun nRQuadTo(ptr: Long, dx1: Float, dy1: Float, dx2: Float, dy2: Float)

    private external fun nConicTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float, w: Float)
    private external fun nRConicTo(ptr: Long, dx1: Float, dy1: Float, dx2: Float, dy2: Float, w: Float)

    private external fun nCubicTo(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float)
    private external fun nRCubicTo(ptr: Long, dx1: Float, dy1: Float, dx2: Float, dy2: Float, dx3: Float, dy3: Float)

    private external fun nArcTo(ptr: Long, l: Float, t: Float, r: Float, b: Float, startAngle: Float, sweepAngle: Float, forceModeTo: Boolean)
    private external fun nArcTo2(ptr: Long, x1: Float, y1: Float, x2: Float, y2: Float, radius: Float)

    // start:     0: upper-left  corner
    //            1: upper-right corner
    //            2: lower-right corner
    //            3: lower-left  corner
    private external fun nAddRect(ptr: Long, l: Float, t: Float, r: Float, b: Float, direction: Int, start: Int)
    private external fun nAddOval(ptr: Long, l: Float, t: Float, r: Float, b: Float, direction: Int)
    private external fun nAddCircle(ptr: Long, x: Float, y: Float, radius: Float, direction: Int)
    private external fun nAddArc(ptr: Long, l: Float, t: Float, r: Float, b: Float, startAngle: Float, sweepAngle: Float)
    private external fun nAddRoundRect(ptr: Long, l: Float, t: Float, r: Float, b: Float, rx: Float, ry: Float, dir: Int)
    private external fun nAddPolygon(ptr: Long, points: FloatArray, count: Int, close: Boolean)
}