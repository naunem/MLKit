package com.example.mba0166.androidmlkit.barcode

import android.util.Log
import com.example.mba0166.androidmlkit.FrameMetadata
import com.example.mba0166.androidmlkit.GraphicOverlay
import com.example.mba0166.androidmlkit.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.lang.Exception

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/25/18
 */
class BarcodeDetectionProcessor : VisionProcessorBase<List<FirebaseVisionBarcode>>() {

    private var mDetector: FirebaseVisionBarcodeDetector

    init {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
                .build()

        mDetector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
    }

    override fun detectInImage(image: FirebaseVisionImage?): Task<List<FirebaseVisionBarcode>> {
        return mDetector.detectInImage(image!!)
    }

    override fun onSuccess(results: List<FirebaseVisionBarcode>, frameMetadata: FrameMetadata, graphicOverlay: GraphicOverlay) {
        graphicOverlay.clear()
        for (barcode in results) {
            val barcodeGraphic = BarcodeGraphic(barcode, graphicOverlay)
            graphicOverlay.add(barcodeGraphic)
        }
    }

    override fun onFailure(e: Exception) {
        Log.e("xxxx", "onFailure: ${e.message}")
    }
}