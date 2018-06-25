package com.example.mba0166.androidmlkit.faces

import android.util.Log

import com.example.mba0166.androidmlkit.FrameMetadata
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

import java.io.IOException

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/21/18
 */
class FaceDetectionProcessor : VisionProcessorBase<List<FirebaseVisionFace>>() {

    private val mDetector: FirebaseVisionFaceDetector

    init {
        val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setTrackingEnabled(true)
                .build()

        mDetector = FirebaseVision.getInstance().getVisionFaceDetector(options)
    }

    override fun stop() {
        try {
            mDetector.close()
        } catch (e: IOException) {
            Log.e("", "Exception thrown while trying to close Face Detector: $e")
        }

    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionFace>> {
        return mDetector.detectInImage(image)
    }

    override fun onSuccess(faces: List<FirebaseVisionFace>, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        for (face: FirebaseVisionFace in faces) {
            Log.d("xxxx", "success: $face")

            val faceGraphic = FaceGraphic(graphicOverlay)
            graphicOverlay.add(faceGraphic)
            faceGraphic.updateFace(face)
        }
    }

    override fun onFailure(e: Exception) {
        Log.d("xxxx", "Face detection failed $e")
    }
}
