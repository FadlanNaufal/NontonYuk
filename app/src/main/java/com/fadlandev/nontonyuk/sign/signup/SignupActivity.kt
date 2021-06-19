package com.fadlandev.nontonyuk.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.sign.signin.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var username: String
    lateinit var password: String
    lateinit var name: String
    lateinit var email: String

    lateinit var mFirebaseDatabaseReference: DatabaseReference
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mFirebaseInstance = FirebaseDatabase.getInstance("https://nontonyuk-93ba9-default-rtdb.firebaseio.com/")
        mDatabase = FirebaseDatabase.getInstance("https://nontonyuk-93ba9-default-rtdb.firebaseio.com/").getReference()
        mFirebaseDatabaseReference = mFirebaseInstance.getReference("User")

        btn_signup_one.setOnClickListener {
            username = et_username.text.toString()
            password = et_password.text.toString()
            name = et_name.text.toString()
            email = et_email.text.toString()

            if(username.equals("")){
                et_username.error = "Silahkan masukkan username Anda"
                et_username.requestFocus()
            } else if(password.equals("")){
                et_password.error = "Silahkan masukkan password Anda"
                et_password.requestFocus()
            } else if(name.equals("")){
                et_name.error = "Silahkan masukkan nama Anda"
                et_name.requestFocus()
            } else if(email.equals("")){
                et_email.error = "Silahkan masukkan email Anda"
                et_email.requestFocus()
            } else {
                saveUser(username, password, name, email)
            }

        }
    }

    private fun saveUser(username: String, password: String, name: String, email: String) {
        var user = User()
        user.email = email
        user.password = password
        user.nama = name
        user.email = email
        user.username = username

        if(username != null){
            checkingUsername(username, user)
        }
    }

    private fun checkingUsername(username: String, data: User) {
        mFirebaseDatabaseReference.child("username").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignupActivity,databaseError.message,Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    mFirebaseDatabaseReference.child(username).setValue(data)
                    var intent = Intent(this@SignupActivity,
                        SignupPhotoActivity::class.java)
                                        .putExtra("nama",data.nama)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignupActivity,"User telah ada",Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}
