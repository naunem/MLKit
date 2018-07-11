package com.example.mba0166.androidmlkit.faces

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.GraphicOverlay.Graphic
import com.google.android.gms.vision.CameraSource
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/19/18
 */
class FaceGraphic(graphicOverlay: GraphicOverlay) : Graphic(graphicOverlay) {

    private lateinit var mFirebaseVisionFace: FirebaseVisionFace
    private val ID_TEXT_SIZE = 40.0f
    private val ID_Y_OFFSET = 50.0f
    private val ID_X_OFFSET = -50.0f
    private val BOX_STROKE_WIDTH = 10f
    private var mFacing: Int = 0
    private val mPaint = Paint()
    private val mPaintText = Paint()

    init {
        mPaint.color = Color.GREEN
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = BOX_STROKE_WIDTH

        mPaintText.color = Color.WHITE
        mPaintText.textSize = ID_TEXT_SIZE
    }

    override fun draw(canvas: Canvas?) {

        val face = mFirebaseVisionFace
        val x = translateX(face.boundingBox.centerX().toFloat())
        val y = translateY(face.boundingBox.centerY().toFloat())
        canvas?.let {
            canvas.drawPoint(x, y, mPaint)
            canvas.drawText("id: ${face.trackingId}", x + ID_X_OFFSET, y + ID_Y_OFFSET, mPaintText)
            canvas.drawText("happiness: ${String.format("%.2f", face.smilingProbability)}", x + ID_X_OFFSET * 3, y - ID_Y_OFFSET, mPaintText)
            if (mFacing != CameraSource.CAMERA_FACING_FRONT) {
                canvas.drawText("right eye: ${String.format("%.2f", face.rightEyeOpenProbability)}", x - ID_X_OFFSET, y, mPaintText)
                canvas.drawText("left eye: ${String.format("%.2f", face.leftEyeOpenProbability)}", x + ID_X_OFFSET * 6, y, mPaintText)
            } else {
                canvas.drawText("left eye: ${String.format("%.2f", face.leftEyeOpenProbability)}", x - ID_X_OFFSET, y, mPaintText)
                canvas.drawText("right eye: ${String.format("%.2f", face.rightEyeOpenProbability)}", x + ID_X_OFFSET * 6, y, mPaintText)
            }

            // Draws a bounding box around the face.
            val xOffset = scaleX(face.boundingBox.width() / 2.0f)
            val yOffset = scaleY(face.boundingBox.height() / 2.0f)
            val left = x - xOffset
            val top = y - yOffset
            val right = x + xOffset
            val bottom = y + yOffset
            canvas.drawRect(left, top, right, bottom, mPaint)
        }

        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.LEFT_EAR)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.RIGHT_EAR)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.LEFT_CHEEK)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.RIGHT_CHEEK)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.LEFT_EYE)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.RIGHT_EYE)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.LEFT_MOUTH)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.RIGHT_MOUTH)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.BOTTOM_MOUTH)
        drawLandmarkPosition(canvas, mFirebaseVisionFace, FirebaseVisionFaceLandmark.NOSE_BASE)
    }

    fun updateFace(face: FirebaseVisionFace) {
        mFirebaseVisionFace = face
        postInvalidate()
    }

    private fun drawLandmarkPosition(canvas: Canvas?, face: FirebaseVisionFace, landmarkID: Int) {
        val landmark = face.getLandmark(landmarkID)
        landmark?.let {
            val position = landmark.position
            canvas?.drawPoint(translateX(position.x), translateY(position.y), mPaint)
        }
    }
}
