package com.kejin.android.skia.demo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bhs.android.skia.SkBmpCanvas
import com.bhs.android.skia.SkFont
import com.bhs.android.skia.SkPaint
import kotlin.math.cos

class MainActivity : AppCompatActivity() {

    private val bmpCanvas = SkBmpCanvas(1080, 1920)
    private val paint = SkPaint()
    private val font = SkFont()

    private var testIndex: Int = 0
    private val testArray = arrayOf(
        Pair("test1", Runnable { test1() }),
        Pair("test2", Runnable { test2() }),
        Pair("test3", Runnable { test3() }),
        Pair("test4", Runnable { test4() }),
        Pair("test5", Runnable { test5() }),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgView: ImageView = findViewById(R.id.imageView)
        imgView.scaleType = ImageView.ScaleType.FIT_CENTER

        val textView = findViewById<TextView>(R.id.textView)
        textView.setOnClickListener {
            val test = testArray[testIndex%testArray.size]
            val startMs = System.currentTimeMillis()
            bmpCanvas.resetMatrix()
            test.second.run()
            testIndex += 1
            val costMs = System.currentTimeMillis() - startMs
            textView.setText(test.first + " - " + costMs)
            imgView.setImageBitmap(bmpCanvas.readBitmap())
        }
    }

    private fun test1() {
        bmpCanvas.clear(Color.YELLOW)
        font.setSize(20f)
        paint.setColor(Color.RED)
        bmpCanvas.save()

        paint.setStrokeWidth(100f)
        paint.setStrokeCap(SkPaint.Cap.ROUND)

        bmpCanvas.translate(500f, 1000f)
        bmpCanvas.drawPoint(0f, 0f, paint)
        bmpCanvas.restore()

        paint.setColor(Color.GREEN)
        paint.setStrokeCap(SkPaint.Cap.SQUARE)
        bmpCanvas.drawPoint(500f, 500f, paint)
    }

    // test drawCircle
    private fun test2() {
        bmpCanvas.clear(Color.RED)
        bmpCanvas.save()

        paint.setColor(Color.YELLOW)
        paint.setStrokeWidth(10f)
        bmpCanvas.translate(100f, 500f)
//        bmpCanvas.skew(1f, 0f)
        bmpCanvas.scale(10f, 10f)
        bmpCanvas.rotate(45f)
//        bmpCanvas.clear(Color.DKGRAY)
        bmpCanvas.drawPoint(0f, 0f, paint)
        bmpCanvas.save()

        bmpCanvas.restoreToCount(0)

        paint.setColor(Color.GREEN)
        paint.setStyle(SkPaint.Style.STROKE)
        paint.setStrokeWidth(10f)
        //bmpCanvas.translate(500f, 500f)
        bmpCanvas.drawCircle(0f, 0f, 100f, paint)
        bmpCanvas.save()

//        bmpCanvas.restoreToCount(1)
//
//        bmpCanvas.drawRect(RectF(0f, 0f, 100f, 50f), paint)
//        bmpCanvas.restore()
    }

    private fun test3() {
        paint.setAntiAlias(true)

        paint.setColor(Color.DKGRAY)
        bmpCanvas.drawLine(0f, 0f, bmpCanvas.width.toFloat(), bmpCanvas.height.toFloat(), paint)

        paint.setColor(Color.WHITE)
        bmpCanvas.drawRect(RectF(0f, 0f, 500f, 100f), paint)

        paint.setColor(Color.CYAN)
        bmpCanvas.drawOval(RectF(0f, 0f, 500f, 1000f), paint)

        paint.setColor(Color.YELLOW)
        bmpCanvas.drawArc(RectF(100f, 100f, 500f, 1000f), 10f, 50f, true, paint)
    }

    private fun test4() {
        bmpCanvas.clear(Color.DKGRAY)

        paint.setColor(Color.RED)
        paint.setStyle(SkPaint.Style.STROKE_AND_FILL)
        bmpCanvas.drawRoundRect(RectF(100f, 100f, 500f, 1000f), 50f, 50f, paint)
    }

    private fun test5() {
        val bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)

        c.drawColor(Color.CYAN)
        val p = Paint()
        p.isAntiAlias = true
        p.setColor(Color.GREEN)
        p.strokeWidth = 10f
        p.style = Paint.Style.STROKE
        c.drawCircle(50f, 50f, 20f, p)

        bmpCanvas.drawImage(bmp, 480f, 640f, SkBmpCanvas.FilterMode.NEAREST)

        bmpCanvas.drawImageRect(bmp, null, RectF(720f, 960f, 1080f, 1920f), SkBmpCanvas.FilterMode.LINEAR)

        val mat = Matrix()
//        mat.postTranslate(10f, 10f)
//        mat.postRotate(45f)
        bmpCanvas.drawImageMatrix(bmp, mat, paint)
    }

    override fun onDestroy() {
        super.onDestroy()
        bmpCanvas.release()
        font.release()
        paint.release()
    }
}