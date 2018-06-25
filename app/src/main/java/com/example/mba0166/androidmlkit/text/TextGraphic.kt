package com.example.mba0166.androidmlkit.text

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.GraphicOverlay.Graphic
import com.google.firebase.ml.vision.text.FirebaseVisionText

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/25/18
 */
class TextGraphic(graphicOverlay: GraphicOverlay, private var text: FirebaseVisionText.Element) : Graphic(graphicOverlay) {

    private val mPaint = Paint()
    private val mPaintText = Paint()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
        mPaint.color = Color.WHITE

        mPaintText.color = Color.WHITE
        postInvalidate()
    }

    override fun draw(canvas: Canvas?) {
        // Draws the bounding box around the TextBlock.
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        canvas?.drawRect(rect, mPaint)

        // Renders the text at the bottom of the box.
        mPaintText.textSize = rect.bottom - rect.top - 5
        canvas?.drawText(text.text, rect.left, rect.bottom, mPaintText)
    }
}