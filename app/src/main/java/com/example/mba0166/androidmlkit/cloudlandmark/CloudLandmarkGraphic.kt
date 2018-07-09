package com.example.mba0166.androidmlkit.cloudlandmark

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.GraphicOverlay.Graphic
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 7/9/18
 */
class CloudLandmarkGraphic(private val cloudLandmark: FirebaseVisionCloudLandmark, graphicOverlay: GraphicOverlay) : Graphic(graphicOverlay) {

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
        canvas?.drawRect(cloudLandmark.boundingBox, mPaint)
        val landmarkName = cloudLandmark.landmark
        val entityId = cloudLandmark.entityId
        val confidence = cloudLandmark.confidence
        Log.d("xxxx", "cloud landmark: $landmarkName $entityId $confidence")
    }
}