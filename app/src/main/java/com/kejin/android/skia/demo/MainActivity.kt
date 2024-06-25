package com.kejin.android.skia.demo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bhs.android.skia.SkCanvas
import com.bhs.android.skia.SkClipOp
import com.bhs.android.skia.SkFont
import com.bhs.android.skia.SkPaint
import com.bhs.android.skia.SkPath

class MainActivity : AppCompatActivity() {

    private val bmpCanvas = SkCanvas(1080, 1920)
    private val paint = SkPaint()
    private val font = SkFont()

    private var testIndex: Int = 0
    private val testArray = arrayOf(
        Pair("test1", Runnable { test1() }),
        Pair("test2", Runnable { test2() }),
        Pair("test3", Runnable { test3() }),
        Pair("test4", Runnable { test4() }),
        Pair("test5", Runnable { test5() }),
        Pair("test6", Runnable { test6() }),
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

    val mat = Matrix()
    private fun test5() {
        val bmp = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)

        c.drawColor(Color.CYAN)
        val p = Paint()
        p.isAntiAlias = true
        p.setColor(Color.GREEN)
        p.strokeWidth = 10f
        p.style = Paint.Style.STROKE
        c.drawCircle(50f, 50f, 20f, p)
        p.strokeWidth = 1f
        p.textSize = 20f
        p.setColor(Color.DKGRAY)
        c.drawText("Android canvas", 20f, 20f, p)
        val bbbmp = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888)
        val ccc = Canvas(bbbmp)
        ccc.drawColor(Color.BLACK)
        ccc.drawText("B", 50f, 50f, p)
        mat.reset()
        mat.postTranslate(20f, 20f)
        mat.postRotate(10f, 50f+20f, 50f+20f)
        mat.postScale(-1f, 1f, 50f+20f, 50f+20f)
        c.drawBitmap(bbbmp, mat, null)

        mat.reset()
        mat.postTranslate(200f, 200f)
        mat.postRotate(45f, bmp.width/2f+200f, bmp.height/2f+200f)

        mat.postScale(-1f, 1f, bmp.width/2f+200f, bmp.height/2f+200f)
        bmpCanvas.drawImageMatrix(bmp, mat, SkCanvas.FilterMode.NEAREST)

        bmpCanvas.drawImage(bmp, 480f, 640f, SkCanvas.FilterMode.NEAREST)

        bmpCanvas.drawImageRect(bmp, null, RectF(720f, 960f, 1080f, 1920f), SkCanvas.FilterMode.LINEAR)

        bbbmp.recycle()
        bmp.recycle()
    }


    private fun test6() {
        bmpCanvas.clear(Color.RED)
        bmpCanvas.save()
        bmpCanvas.clipRect(RectF(100f, 100f, 500f, 500f), SkClipOp.DIFFERENCE, true)
        bmpCanvas.drawColor(Color.parseColor("#A0000000"))
        bmpCanvas.restore()

        val path = SkPath()
        path.moveTo(1080f, 1920f)
        path.lineTo(1080f, 0f)
        path.lineTo(0f, 1920f)
        path.close()

        bmpCanvas.save()
        bmpCanvas.clipPath(path, SkClipOp.DIFFERENCE, true)
        bmpCanvas.drawColor(Color.parseColor("#6600FF00"))
        bmpCanvas.restore()

        path.release()


        paint.setStrokeWidth(1f)
        paint.setColor(Color.GREEN)
        bmpCanvas.drawLine(0f, 960f, 1080f, 960f, paint)
        bmpCanvas.drawLine(540f, 0f, 540f, 1920f, paint)
        paint.setColor(Color.WHITE)

        val text = "hello, world你好，世界"
        val textBounds = RectF()
        font.setSize(100f)
        val fsize = font.measureText(text, paint, textBounds)
//        font.setHinting(SkFont.Hinting.SLIGHT)
        val bw = textBounds.width()
        val bh = textBounds.height()
        Log.i("test", "font bounds: $textBounds, fsize: $fsize")
        val dx = -bw/2f-textBounds.left
        val dy = bh/2f-textBounds.bottom
        bmpCanvas.drawText(text, 540f+dx, 960f+dy, font, paint)

        textBounds.offset(540f+dx, 960f+dy)
        paint.setStyle(SkPaint.Style.STROKE)
        bmpCanvas.drawRect(textBounds, paint)
    }

    override fun onDestroy() {
        super.onDestroy()
        bmpCanvas.release()
        font.release()
        paint.release()
    }
}