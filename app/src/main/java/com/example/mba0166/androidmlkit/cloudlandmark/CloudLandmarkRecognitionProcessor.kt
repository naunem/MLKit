package com.example.mba0166.androidmlkit.cloudlandmark

import android.util.Log
import com.example.mba0166.androidmlkit.FrameMetadata
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.lang.Exception

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 7/9/18
 */
class CloudLandmarkRecognitionProcessor : VisionProcessorBase<List<FirebaseVisionCloudLandmark>>() {

    private var mDetector: FirebaseVisionCloudLandmarkDetector

    init {
        val option = FirebaseVisionCloudDetectorOptions.Builder()
                .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                .setMaxResults(15)
                .build()

        mDetector = FirebaseVision.getInstance().getVisionCloudLandmarkDetector(option)
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionCloudLandmark>> {
        return mDetector.detectInImage(image)
    }

    override fun onSuccess(results: List<FirebaseVisionCloudLandmark>, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        for (result in results) {
            Log.d("xxxx", "success: $result")
            val cloudLandmarkGraphic = CloudLandmarkGraphic(result, graphicOverlay)
            graphicOverlay.add(cloudLandmarkGraphic)
        }
    }

    override fun onFailure(e: Exception) {
        Log.d("xxxx", "failure: ${e.message}")
    }
}