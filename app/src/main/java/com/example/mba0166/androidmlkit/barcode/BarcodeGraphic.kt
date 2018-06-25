package com.example.mba0166.androidmlkit.barcode

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.GraphicOverlay.Graphic
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/25/18
 */
class BarcodeGraphic(private val barcode: FirebaseVisionBarcode, graphicOverlay: GraphicOverlay) : Graphic(graphicOverlay) {

    private val mPaint = Paint()
    private val mPaintText = Paint()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
        mPaint.color = Color.WHITE

        mPaintText.color = Color.WHITE
        mPaintText.textSize = 40f
    }

    override fun draw(canvas: Canvas?) {
        val box = RectF(barcode.boundingBox)
        box.left = translateX(box.left)
        box.top = translateY(box.top)
        box.right = translateX(box.right)
        box.bottom = translateY(box.bottom)

        Log.d("xxxx", "result: " + barcode.rawValue)
        Log.d("xxxx", "result: " + barcode.displayValue)

        canvas?.drawRect(box, mPaint)
        canvas?.drawText(barcode.rawValue, box.left, box.bottom, mPaintText)
    }
}