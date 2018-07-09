package com.example.mba0166.androidmlkit.labelimages

import android.util.Log
import com.example.mba0166.androidmlkit.FrameMetadata
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions
import java.lang.Exception

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/25/18
 */
class CloudLabelImageProcessor : VisionProcessorBase<List<FirebaseVisionCloudLabel>>() {

    private var mDetector: FirebaseVisionCloudLabelDetector

    init {
        val option = FirebaseVisionCloudDetectorOptions.Builder()
                .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                .setMaxResults(15)
                .build()

        mDetector = FirebaseVision.getInstance().getVisionCloudLabelDetector(option)
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionCloudLabel>> {
        return mDetector.detectInImage(image)
    }

    override fun onSuccess(results: List<FirebaseVisionCloudLabel>, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        Log.d("xxxx", "success: $results")
        val cloudLabelGraphic = CloudLabelGraphic(results, graphicOverlay)
        graphicOverlay.add(cloudLabelGraphic)
    }

    override fun onFailure(e: Exception) {
        Log.e("xxxx", "onFailure: ${e.message}")
    }
}
