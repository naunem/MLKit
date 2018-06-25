package com.example.mba0166.androidmlkit.faces

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.example.mba0166.androidmlkit.*
import java.io.IOException

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/21/18
 */
class FaceDetectionActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private var mPreview: CameraSourcePreview? = null
    private var mGraphicOverlay: GraphicOverlay? = null
    private var mTbSwitchCamera: ToggleButton? = null
    private var mCameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_detection)

        initView()
        createCameraSource()
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

    private fun createCameraSource() {
        if (mCameraSource == null) {
            mCameraSource = CameraSource(this, mGraphicOverlay)
        }

        try {
            mCameraSource!!.setMachineLearningFrameProcessor(FaceDetectionProcessor())
        } catch (e: Exception) {
            Log.d("xxxx", "createCameraSource can not create camera source: " + e.cause)
            e.printStackTrace()
        }
    }
}