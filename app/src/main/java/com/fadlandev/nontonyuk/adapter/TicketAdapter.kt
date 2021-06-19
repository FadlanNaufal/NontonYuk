package com.fadlandev.nontonyuk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.model.Checkout
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row_item_now_playing.view.*
import java.text.NumberFormat
import java.util.*

class TicketAdapter(private var data: List<Checkout>,
                    private val listener: (Checkout) -> Unit) :
                        RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    lateinit var contexAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contexAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_white,parent,false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position],listener, contexAdapter)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvSeat: TextView = view.findViewById(R.id.tv_seat_number)

        fun bindItem(data : Checkout, listener: (Checkout) -> Unit, context : Context){

            tvSeat.setText("Seat No " + data.kursi)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
