package com.fadlandev.nontonyuk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fadlandev.nontonyuk.model.Checkout
import com.fadlandev.nontonyuk.model.Film
import kotlinx.android.synthetic.main.activity_select_seat.*

class SelectSeatActivity : AppCompatActivity() {

    var statusA3 : Boolean = false
    var statusA4 : Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_seat)

        val data = intent.getParcelableExtra<Film>("data")
        tv_kursi.text = data.judul

        a3.setOnClickListener {
            if(statusA3){
                a3.setImageResource(R.drawable.ic_empty_seat)
                statusA3 = false
                total -= 1
                buyTicket(total)
            } else {
                a3.setImageResource(R.drawable.ic_selected_seat)
                statusA3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A3","70000")
                dataList.add(data)
            }
        }

        a4.setOnClickListener {
            if(statusA4){
                a4.setImageResource(R.drawable.ic_empty_seat)
                statusA4 = false
                total -= 1
                buyTicket(total)
            } else {
                a4.setImageResource(R.drawable.ic_selected_seat)
                statusA4 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A4","70000")
                dataList.add(data)
            }
        }
        
        btn_beli_tiket.setOnClickListener {
            var intent = Intent(this,CheckoutActivity::class.java).putExtra("data",dataList)
            startActivity(intent)
        }

    }

    private fun buyTicket(total: Int) {
        if(total == 0){
            btn_beli_tiket.setText("Beli Tiket")
            btn_beli_tiket.visibility = View.INVISIBLE
        } else {
            btn_beli_tiket.setText("Beli Tiket ("+ total +")")
            btn_beli_tiket.visibility = View.VISIBLE
        }
    }
}
