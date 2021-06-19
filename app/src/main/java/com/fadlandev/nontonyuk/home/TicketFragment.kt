package com.fadlandev.nontonyuk.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.TicketActivity
import com.fadlandev.nontonyuk.adapter.ComingSoonAdapter
import com.fadlandev.nontonyuk.model.Film
import com.fadlandev.nontonyuk.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_ticket.*

/**
 * A simple [Fragment] subclass.
 */
class TicketFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabaseReference: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)
        mDatabaseReference = FirebaseDatabase.getInstance("https://nontonyuk-93ba9-default-rtdb.firebaseio.com/").getReference("Film")

        rv_ticket_list.layoutManager = LinearLayoutManager(context)
        getData()
    }

    private fun getData() {
        mDatabaseReference.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context,""+databaseError.message,Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in dataSnapshot.children){
                    val film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_ticket_list.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context,TicketActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }

                tv_total.setText("${dataList.size} Movies")
            }

        })
    }

}
