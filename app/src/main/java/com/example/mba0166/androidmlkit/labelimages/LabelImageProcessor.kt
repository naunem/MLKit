package com.example.mba0166.androidmlkit.labelimages

import android.util.Log
import com.example.mba0166.androidmlkit.FrameMetadata
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions
import java.lang.Exception

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/25/18
 */
class LabelImageProcessor : VisionProcessorBase<List<FirebaseVisionLabel>>() {

    private var mDetector: FirebaseVisionLabelDetector

    init {
        val option = FirebaseVisionLabelDetectorOptions.Builder()
                .setConfidenceThreshold(0.8f)
                .build()

        mDetector = FirebaseVision.getInstance().getVisionLabelDetector(option)
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionLabel>> {
        return mDetector.detectInImage(image)
    }

    override fun onSuccess(results: List<FirebaseVisionLabel>, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        val labelGraphic = LabelGraphic(results, graphicOverlay)
        graphicOverlay.add(labelGraphic)
    }

    override fun onFailure(e: Exception) {
        Log.e("xxxx", "onFailure: ${e.message}")
    }
}
