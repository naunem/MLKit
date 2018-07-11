package com.example.mba0166.androidmlkit

import android.content.res.Resources
import android.util.Log

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 7/10/18
 */
class ScreenUtil {

    companion object {
        fun getScreenWidth(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            return Resources.getSystem().displayMetrics.heightPixels
        }
    }
}