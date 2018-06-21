package com.example.mba0166.androidmlkit.text

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.mba0166.androidmlkit.*
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.IOException


/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/15/18
 */
class TextRecognitionActivity : AppCompatActivity(), OnRecognitionListener {

    private var mPreview: CameraSourcePreview? = null
    private var mGraphicOverlay: GraphicOverlay? = null
    private var mCameraSource: CameraSource? = null
    private var mDisplayList: MutableList<String> = mutableListOf()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: TextRecognitionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces_detection)

        initView(this)
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

    private fun initView(context: Context) {
        mPreview = findViewById(R.id.Preview)
        mGraphicOverlay = findViewById(R.id.Overlay)
        mRecyclerView = findViewById(R.id.mRecyclerView)
        mAdapter = TextRecognitionAdapter(context, mDisplayList)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun startCameraSource() {
        if (mCameraSource != null) {
            try {
                mPreview!!.start(mCameraSource, mGraphicOverlay)
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
            mCameraSource!!.setMachineLearningFrameProcessor(TextRecognitionProcessor(this))
        } catch (e: Exception) {
            Log.d("xxxx", "createCameraSource can not create camera source: " + e.cause)
            e.printStackTrace()
        }
    }

    override fun success(results: FirebaseVisionText) {
        val blocks = results.blocks
//        mDisplayList.clear()
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            Log.i("xxxx", "$lines")
            for (j in lines.indices) {
                val elements = lines[j].elements
                Log.i("xxxx", "$elements")
                for (k in elements.indices) {
                    Log.d("xxxx", "success: " + elements[k].text)
                    mDisplayList.add(elements[k].text)
                    mRecyclerView.scrollToPosition(mDisplayList.size - 1)
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun error(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}