package com.example.mba0166.androidmlkit.labelimages

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.GraphicOverlay.Graphic
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabel

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/25/18
 */
class CloudLabelGraphic(private val labels: List<FirebaseVisionCloudLabel>, private val graphicOverlay: GraphicOverlay) : Graphic(graphicOverlay) {

    private val mPaint = Paint()

    init {
        mPaint.color = Color.WHITE
        mPaint.textSize = 60f
        postInvalidate()
    }

    override fun draw(canvas: Canvas?) {
        val x = graphicOverlay.width / 3.0f
        var y = graphicOverlay.height / 2.0f
        for (label in labels) {
            val result = String.format("%s : %s", label.label, label.confidence)
            canvas?.drawText(result, x, y, mPaint)
            y -= 62f
        }
    }
}