package com.kejin.android.skia.demo

import android.graphics.Color
import android.graphics.PointF
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

class MainActivity : AppCompatActivity() {

    private val bmpCanvas = SkBmpCanvas(1080, 1920)
    private val paint = SkPaint()
    private val font = SkFont()

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

        findViewById<TextView>(R.id.textView).setOnClickListener {
            testCanvas()
            imgView.setImageBitmap(bmpCanvas.readBitmap())
        }
    }

    private fun testCanvas() {
        val cx = bmpCanvas.width/2f
        val cy = bmpCanvas.height/2f

        bmpCanvas.clear(Color.BLUE)
        font.setSize(100f)

        paint.setColor(Color.YELLOW)
        bmpCanvas.drawText("Hello world", cx, cy, font, paint)

        paint.setColor(Color.RED)
        paint.setStrokeWidth(100f)
        paint.setStrokeCap(SkPaint.Cap.ROUND)
        bmpCanvas.drawPoint(cx, cy, paint)

        val points= listOf(
            PointF(0f, 500f),
            PointF(100f, 400f),
            PointF(200f, 300f),
            PointF(300f, 200f),
            PointF(400f, 100f),
            PointF(500f, 0f),
            PointF(500f, 0f),
            PointF(1080f, 1920f),
            )
        paint.setStrokeWidth(10f)
        bmpCanvas.drawPoints(SkBmpCanvas.PointMode.POLYGON, points, paint)
    }
}