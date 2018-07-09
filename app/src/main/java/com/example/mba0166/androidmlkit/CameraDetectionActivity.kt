package com.example.mba0166.androidmlkit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.example.mba0166.androidmlkit.barcode.BarcodeDetectionProcessor
import com.example.mba0166.androidmlkit.cloudlandmark.CloudLandmarkRecognitionProcessor
import com.example.mba0166.androidmlkit.faces.FaceDetectionProcessor
import com.example.mba0166.androidmlkit.labelimages.LabelImageProcessor
import com.example.mba0166.androidmlkit.text.TextRecognitionProcessor
import java.io.IOException

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/21/18
 */
class CameraDetectionActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private var mPreview: CameraSourcePreview? = null
    private var mGraphicOverlay: GraphicOverlay? = null
    private var mTbSwitchCamera: ToggleButton? = null
    private var mCameraSource: CameraSource? = null

    val TYPE = "type"
    val FACE_DETECTION = "face"
    val TEXT_RECOGNITION = "text"
    val LABEL_IMAGE_RECOGNITION = "labelImage"
    val BARCODE_RECOGNITION = "barcode"
    val CLOUD_LANDMARK_RECOGNITION = "cloudLandmark"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_detection)

        initView()

        val type = intent.getStringExtra(TYPE)
        createCameraSource(type)
    }

    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    override fun onPause() {
        super.onPause()
        mPreview!!.stop()
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (mCameraSource != null) {
            mCameraSource!!.release()
        }
    }

    private fun initView() {
        mPreview = findViewById(R.id.preview)
        mGraphicOverlay = findViewById(R.id.graphicOverlay)
        mTbSwitchCamera = findViewById(R.id.tbSwitchCamera)

        mTbSwitchCamera?.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        mCameraSource?.let {
            if (isChecked) {
                mCameraSource?.setFacing(CameraSource.CAMERA_FACING_BACK)
            } else {
                mCameraSource?.setFacing(CameraSource.CAMERA_FACING_FRONT)
            }
        }
        mPreview?.stop()
        startCameraSource()
    }

    private fun startCameraSource() {
        mCameraSource?.let {
            try {
                mPreview!!.start(mCameraSource!!, mGraphicOverlay!!)
            } catch (e: IOException) {
                Log.d("xxxx", "startCameraSource : Unable to start camera source." + e.message)
                mCameraSource!!.release()
                mCameraSource = null
            }
        }
    }

    private fun createCameraSource(type: String?) {
        if (mCameraSource == null) {
            mCameraSource = CameraSource(this, mGraphicOverlay)
        }

        try {
            when (type) {
                FACE_DETECTION -> {
                    mCameraSource!!.setMachineLearningFrameProcessor(FaceDetectionProcessor())
                }

                TEXT_RECOGNITION -> {
                    mCameraSource!!.setMachineLearningFrameProcessor(TextRecognitionProcessor())
                }

                LABEL_IMAGE_RECOGNITION -> {
                    mCameraSource!!.setMachineLearningFrameProcessor(LabelImageProcessor())
                }

                BARCODE_RECOGNITION -> {
                    mCameraSource!!.setMachineLearningFrameProcessor(BarcodeDetectionProcessor())
                }

                CLOUD_LANDMARK_RECOGNITION -> {
                    mCameraSource!!.setMachineLearningFrameProcessor(CloudLandmarkRecognitionProcessor())
                }
            }
        } catch (e: Exception) {
            Log.d("xxxx", "createCameraSource can not create camera source: " + e.cause)
            e.printStackTrace()
        }
    }
}