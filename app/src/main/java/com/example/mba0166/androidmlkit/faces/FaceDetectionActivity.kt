package com.example.mba0166.androidmlkit.faces

import android.graphics.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.mba0166.androidmlkit.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/15/18
 */
class FaceDetectionActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var mImgObject: ImageView
    private lateinit var mBtnProcess: Button
    private var mFirebaseVisionFaces: MutableList<FirebaseVisionFace> = arrayListOf()
    private lateinit var mCanvas: Canvas
    private val mPaint = Paint()
    private lateinit var mTempBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces_detection)

        mImgObject = findViewById(R.id.imgObject)
        mBtnProcess = findViewById(R.id.btnProcess)
        mBtnProcess.setOnClickListener(this)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_momo)
        mImgObject.setImageBitmap(bitmap)

        initDrawer(bitmap)

        initDetector(bitmap)
    }

    private fun initDrawer(bitmap: Bitmap) {
        mTempBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mTempBitmap)
        mCanvas.drawBitmap(bitmap, 0f, 0f, null)
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
    }

    private fun initDetector(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setModeType(FirebaseVisionFaceDetectorOptions.FAST_MODE)
                .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setMinFaceSize(0.15f)
                .setTrackingEnabled(true)
                .build()

        val detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options)

        detector.detectInImage(image)
                .addOnSuccessListener {
                    Log.d("xxxx", "success: $it")
                    mFirebaseVisionFaces.addAll(it)
                }
                .addOnFailureListener {
                    Log.d("xxxx", "failure: ${it.message}")
                }
    }

    override fun onClick(v: View?) {
        Log.d("xxxx", "onClick: $mFirebaseVisionFaces")
        for (face: FirebaseVisionFace in mFirebaseVisionFaces) {
            mCanvas.drawRect(face.boundingBox, mPaint)
            mImgObject.setImageBitmap(mTempBitmap)
        }
    }
}