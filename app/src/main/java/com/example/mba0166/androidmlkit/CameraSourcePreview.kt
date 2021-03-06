package com.example.mba0166.androidmlkit

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import java.io.IOException

/**
 * Preview the camera image in the screen.
 */
class CameraSourcePreview(private val mContext: Context, attrs: AttributeSet) : ViewGroup(mContext, attrs), SurfaceHolder.Callback {

    private val surfaceView: SurfaceView
    private var startRequested: Boolean = false
    private var surfaceAvailable: Boolean = false
    private var cameraSource: CameraSource? = null

    private var overlay: GraphicOverlay? = null

    private val isPortraitMode: Boolean
        get() {
            val orientation = mContext.resources.configuration.orientation
            return orientation == Configuration.ORIENTATION_PORTRAIT
        }

    init {
        startRequested = false
        surfaceAvailable = false

        surfaceView = SurfaceView(mContext)
        surfaceView.holder.addCallback(this)
        addView(surfaceView)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val layoutWidth = right - left
        val layoutHeight = bottom - top

        for (i in 0 until childCount) {
            getChildAt(i).layout(0, 0, layoutWidth, layoutHeight)
        }

        try {
            startIfReady()
        } catch (e: IOException) {
            Log.e("xxxx", "Could not start camera source.", e)
        }
    }

    override fun surfaceCreated(surface: SurfaceHolder) {
        surfaceAvailable = true
        try {
            startIfReady()
        } catch (e: IOException) {
            Log.e("xxxx", "Could not start camera source.", e)
        }
    }

    override fun surfaceDestroyed(surface: SurfaceHolder) {
        surfaceAvailable = false
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource?) {
        if (cameraSource == null) {
            stop()
        }

        this.cameraSource = cameraSource

        if (this.cameraSource != null) {
            startRequested = true
            startIfReady()
        }
    }

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource, overlay: GraphicOverlay) {
        this.overlay = overlay
        start(cameraSource)
    }

    fun stop() {
        if (cameraSource != null) {
            cameraSource!!.stop()
        }
    }

    @SuppressLint("MissingPermission")
    @Throws(IOException::class)
    private fun startIfReady() {
        if (startRequested && surfaceAvailable) {
            cameraSource!!.start(surfaceView.holder)
            if (overlay != null) {
                val size = cameraSource!!.previewSize
                val min = Math.min(size.width, size.height)
                val max = Math.max(size.width, size.height)
                if (isPortraitMode) {
                    // Swap width and height sizes when in portrait, since it will be rotated by 90 degrees
                    overlay!!.setCameraInfo(min, max, cameraSource!!.cameraFacing)
                } else {
                    overlay!!.setCameraInfo(max, min, cameraSource!!.cameraFacing)
                }
                overlay!!.clear()
            }
            startRequested = false
        }
    }
}
