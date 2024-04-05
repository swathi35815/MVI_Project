package com.example.mviproject.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mviproject.R
import com.example.mviproject.model.ImageData
import com.squareup.picasso.Picasso

class ImageAdapter(private val imageDataList : ArrayList<ImageData>?) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    class MyViewHolder(private val itemView : View) : ViewHolder(itemView) {
        fun myBindData(imageData : ImageData) {
            itemView.findViewById<TextView>(R.id.textViewImageDataID).text = imageData.id.toString()
            itemView.findViewById<TextView>(R.id.textViewImageDataTitle).text = imageData.title
            itemView.findViewById<TextView>(R.id.textViewImageDataURL).text = imageData.url
            val imageView = itemView.findViewById<ImageView>(R.id.imageViewImageData)
            Picasso.with(itemView.context)
                .load(imageData.url)
                .into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return imageDataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        imageDataList?.get(position)?.let { holder.myBindData(it) }
    }

    fun newImageData(newImageDataList : List<ImageData>) {
        imageDataList?.clear()
        imageDataList?.addAll(newImageDataList)
        notifyDataSetChanged()
    }
}