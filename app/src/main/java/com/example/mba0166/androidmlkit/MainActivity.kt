package com.example.mba0166.androidmlkit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val PERMISSION_REQUESTS = 1
    private lateinit var mBtnText: Button
    private lateinit var mBtnFaces: Button
    private lateinit var mBtnLabelImage: Button
    private lateinit var mBtnBarcode: Button
    private lateinit var mBtnCloudLandmark: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnText = findViewById(R.id.btnText)
        mBtnFaces = findViewById(R.id.btnFaces)
        mBtnLabelImage = findViewById(R.id.btnLabelImage)
        mBtnBarcode = findViewById(R.id.btnBarcode)
        mBtnCloudLandmark = findViewById(R.id.btnCloudLandmark)

        mBtnText.setOnClickListener(this)
        mBtnFaces.setOnClickListener(this)
        mBtnLabelImage.setOnClickListener(this)
        mBtnBarcode.setOnClickListener(this)
        mBtnCloudLandmark.setOnClickListener(this)

        getRuntimePermissions()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnText -> {
                val intent = Intent(this, CameraDetectionActivity::class.java)
                intent.putExtra(CameraDetectionActivity().TYPE, CameraDetectionActivity().TEXT_RECOGNITION)
                startActivity(intent)
            }
            R.id.btnFaces -> {
                val intent = Intent(this, CameraDetectionActivity::class.java)
                intent.putExtra(CameraDetectionActivity().TYPE, CameraDetectionActivity().FACE_DETECTION)
                startActivity(intent)
            }

            R.id.btnLabelImage -> {
                val intent = Intent(this, CameraDetectionActivity::class.java)
                intent.putExtra(CameraDetectionActivity().TYPE, CameraDetectionActivity().LABEL_IMAGE_RECOGNITION)
                startActivity(intent)
            }

            R.id.btnBarcode -> {
                val intent = Intent(this, CameraDetectionActivity::class.java)
                intent.putExtra(CameraDetectionActivity().TYPE, CameraDetectionActivity().BARCODE_RECOGNITION)
                startActivity(intent)
            }

            R.id.btnCloudLandmark -> {
                val intent = Intent(this, CameraDetectionActivity::class.java)
                intent.putExtra(CameraDetectionActivity().TYPE, CameraDetectionActivity().CLOUD_LANDMARK_RECOGNITION)
                startActivity(intent)
            }
        }
    }

    private fun getRequiredPermissions(): Array<String> {
        return try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            val permissions = info.requestedPermissions
            if (permissions != null && permissions.isNotEmpty()) {
                permissions
            } else {
                arrayOf()
            }
        } catch (e: Exception) {
            arrayOf()
        }
    }

    private fun isPermissionGranted(context: Context, permission: String) =
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    private fun getRuntimePermissions() {
        val allNeedPermissions = arrayListOf<String>()
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeedPermissions.add(permission)
            }
        }

        if (!allNeedPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, allNeedPermissions.toArray(arrayOf()), PERMISSION_REQUESTS)
        }
    }
}
