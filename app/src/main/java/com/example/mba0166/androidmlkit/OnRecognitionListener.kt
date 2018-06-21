package com.example.mba0166.androidmlkit

import com.google.firebase.ml.vision.text.FirebaseVisionText

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/21/18
 */
interface OnRecognitionListener {

    fun success(results: FirebaseVisionText)

    fun error(message: String)
}