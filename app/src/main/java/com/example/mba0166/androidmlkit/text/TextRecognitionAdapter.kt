package com.example.mba0166.androidmlkit.text

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mba0166.androidmlkit.R

/**
 * Asian Tech Co., Ltd.
 * Created by nhanphant on 6/20/18
 */
class TextRecognitionAdapter(private val context: Context, private val results: MutableList<String>) : RecyclerView.Adapter<TextRecognitionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_text_recognition, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTvResult.text = results[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvResult = view.findViewById(R.id.tvResult) as TextView
    }
}