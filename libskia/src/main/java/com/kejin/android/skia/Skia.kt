package com.kejin.android.skia

import android.graphics.Bitmap

class Skia {
    companion object {
        init {
            System.loadLibrary("kejin_skia")
        }

        fun test() : Bitmap {
            val bmp = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888)
            Skia().testDraw(bmp)
            return bmp
        }
    }

    external fun testDraw(bmp: Bitmap)
}