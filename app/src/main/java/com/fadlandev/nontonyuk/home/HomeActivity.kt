package com.fadlandev.nontonyuk.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.fadlandev.nontonyuk.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var fragmentHome = DashboardFragment()
        var fragmentTicket = TicketFragment()
        var fragmentProfile = ProfileFragment()
        setFragment(fragmentHome)

        menu_home.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(menu_home,R.drawable.ic_home_active)
            changeIcon(menu_ticket,R.drawable.ic_tiket)
            changeIcon(menu_profile,R.drawable.ic_profile)
        }

        menu_ticket.setOnClickListener {
            setFragment(fragmentTicket)
            changeIcon(menu_home,R.drawable.ic_home)
            changeIcon(menu_ticket,R.drawable.ic_tiket_active)
            changeIcon(menu_profile,R.drawable.ic_profile)
        }

        menu_profile.setOnClickListener {
            setFragment(fragmentProfile)
            changeIcon(menu_home,R.drawable.ic_home)
            changeIcon(menu_ticket,R.drawable.ic_tiket)
            changeIcon(menu_profile,R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int : Int){
        imageView.setImageResource(int)
    }

}
