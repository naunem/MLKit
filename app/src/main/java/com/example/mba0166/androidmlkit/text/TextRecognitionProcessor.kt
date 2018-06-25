package com.example.mba0166.androidmlkit.text

import android.util.Log
import com.example.mba0166.androidmlkit.FrameMetadata
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector
import java.io.IOException

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/21/18
 */
class TextRecognitionProcessor : VisionProcessorBase<FirebaseVisionText>() {

    private val mDetector: FirebaseVisionTextDetector = FirebaseVision.getInstance().visionTextDetector

    override fun stop() {
        try {
            mDetector.close()
        } catch (e: IOException) {
            Log.e("xxxx", "Exception thrown while trying to close Text Detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<FirebaseVisionText> {
        return mDetector.detectInImage(image)
    }

    override fun onSuccess(results: FirebaseVisionText, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        val blocks = results.blocks
        for (block in blocks) {
            for (line in block.lines) {
                for (element in line.elements) {
                    val textGraphic = TextGraphic(graphicOverlay, element)
                    graphicOverlay.add(textGraphic)
                }
            }
        }
    }

    override fun onFailure(e: Exception) {
        Log.e("xxxx", "onFailure: ${e.message}")
    }
}
