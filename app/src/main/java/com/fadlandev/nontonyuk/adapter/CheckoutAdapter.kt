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

class CheckoutAdapter(private var data: List<Checkout>,
                      private val listener: (Checkout) -> Unit) :
                        RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contexAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contexAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout,parent,false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position],listener, contexAdapter)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvSeat: TextView = view.findViewById(R.id.tv_seat_number)
        private val tvHarga: TextView = view.findViewById(R.id.tv_harga)

        fun bindItem(data : Checkout, listener: (Checkout) -> Unit, context : Context){

            val localID = Locale("id","ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)

            tvHarga.setText(formatRupiah.format(data.harga!!.toDouble()))
            tvSeat.setText(data.kursi)

            if(data.kursi!!.startsWith("Total")){
                tvSeat.setText(data.kursi)
                tvSeat.setCompoundDrawables(null,null,null,null)
            }else{
                tvSeat.setText("Seat No."+data.kursi)
            }


            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
