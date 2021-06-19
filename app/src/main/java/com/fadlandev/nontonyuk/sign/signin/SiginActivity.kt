package com.fadlandev.nontonyuk.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fadlandev.nontonyuk.home.HomeActivity
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.sign.signup.SignupActivity
import com.fadlandev.nontonyuk.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sigin.*


class SiginActivity : AppCompatActivity() {

    lateinit var username: String
    lateinit var password: String

    lateinit var mDatabase: DatabaseReference
    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

        mDatabase = FirebaseDatabase.getInstance("https://nontonyuk-93ba9-default-rtdb.firebaseio.com/").getReference("User")
        preference = Preferences(this)
        preference.setValues("onboarding","1")

        if(preference.getValues("status").equals("1")){
            finishAffinity()
            var intent = Intent(this@SiginActivity,
                HomeActivity::class.java)
            startActivity(intent)
        }

        btn_signin.setOnClickListener {
            username = et_username.text.toString()
            password = et_password.text.toString()

            if(username.equals("")){
                et_username.error = "Silahkan masukkan username Anda"
                et_username.requestFocus()
            } else if(password.equals("")){
                et_password.error = "Silahkan masukkan password Anda"
                et_password.requestFocus()
            } else {
                pushLogin(username, password)
            }

        }

        btn_signup.setOnClickListener {
            var intent = Intent(this@SiginActivity,
                SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(username: String, password: String) {
            mDatabase.child(username).addValueEventListener(object: ValueEventListener{
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@SiginActivity ,databaseError.message,Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var user = dataSnapshot.getValue(User::class.java)
                    if (user == null){
                        Toast.makeText(this@SiginActivity ,
                            "Username tidak ditemukan",Toast.LENGTH_LONG).show()
                    } else {
                        if (user.password.equals(password)){

                            preference.setValues("nama", user.nama.toString())
                            preference.setValues("username", user.username.toString())
                            preference.setValues("url", user.url.toString())
                            preference.setValues("email", user.email.toString())
                            preference.setValues("saldo", user.saldo.toString())
                            preference.setValues("status","1")


                            var intent = Intent(this@SiginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }else {
                            Toast.makeText(this@SiginActivity ,
                                "Password tidak ditemukan",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }
}
