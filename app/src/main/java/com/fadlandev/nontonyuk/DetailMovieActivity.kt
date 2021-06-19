package com.fadlandev.nontonyuk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fadlandev.nontonyuk.adapter.PlayAdapter
import com.fadlandev.nontonyuk.model.Film
import com.fadlandev.nontonyuk.model.Play
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Play>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val data = intent.getParcelableExtra<Film>("data")
        mDatabase = FirebaseDatabase.getInstance("https://nontonyuk-93ba9-default-rtdb.firebaseio.com/").getReference("Film")
            .child(data.judul.toString())
            .child("play")

        tv_kursi.text = data.judul
        tv_genre.text = data.genre
        tv_description.text = data.desc
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        rv_who_played.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        getData()

        btn_pilih_bangku.setOnClickListener {
            var intent = Intent(this@DetailMovieActivity,SelectSeatActivity::class.java).putExtra("data",data)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DetailMovieActivity,""+databaseError,Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children){
                    var Film = getdataSnapshot.getValue(Play::class.java)
                    dataList.add(Film!!)
                }
                rv_who_played.adapter = PlayAdapter(dataList){

                }
            }

        })
    }
}

