package com.example.mba0166.androidmlkit

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

class MainActivity : AppCompatActivity() {

    private var mFace: FirebaseVisionFace? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_kim_so_hyun)
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setModeType(FirebaseVisionFaceDetectorOptions.FAST_MODE)
                .setLandmarkType(
                        FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationType(
                        FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setMinFaceSize(0.15f)
                .setTrackingEnabled(true)
                .build()

        val detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options)

        val result = detector.detectInImage(image)
                .addOnSuccessListener {
                    Log.d("xxxx", "$it")
                }
                .addOnFailureListener {
                    Log.d("xxxx", "${it.message}")
                }
    }
}
