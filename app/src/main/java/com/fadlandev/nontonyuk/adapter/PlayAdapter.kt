package com.fadlandev.nontonyuk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.model.Play
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row_item_now_playing.view.*

class PlayAdapter(private var data: List<Play>,
                  private val listener: (Play) -> Unit) :
                        RecyclerView.Adapter<PlayAdapter.ViewHolder>() {

    lateinit var contexAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contexAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_who_played,parent,false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlayAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position],listener, contexAdapter)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNama: TextView = view.findViewById(R.id.tv_nama)
        private val imageView: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data : Play, listener: (Play) -> Unit, context : Context){
            tvNama.setText(data.nama)

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
