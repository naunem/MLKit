package com.example.mba0166.androidmlkit

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.mba0166.androidmlkit.text.TextRecognitionActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBtnText: Button
    private lateinit var mBtnFaces: Button
    private lateinit var mBtnBarcode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnText = findViewById(R.id.btnText)
        mBtnFaces = findViewById(R.id.btnFaces)
        mBtnBarcode = findViewById(R.id.btnBarcode)

        mBtnText.setOnClickListener(this)
        mBtnFaces.setOnClickListener(this)
        mBtnBarcode.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnText -> {
                val intent = Intent(this, TextRecognitionActivity::class.java)
                startActivity(intent)
            }
            R.id.btnFaces -> {
                val intent = Intent(this, TextRecognitionActivity::class.java)
                startActivity(intent)
            }
            R.id.btnBarcode -> {

            }
        }
    }
}
