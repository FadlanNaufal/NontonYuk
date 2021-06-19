package com.fadlandev.nontonyuk.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.sign.signin.SiginActivity
import com.fadlandev.nontonyuk.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference = Preferences(this)

        if(preference.getValues("onboarding").equals("1")){
            finishAffinity()
            var intent = Intent(this@OnboardingOneActivity, SiginActivity::class.java)
            startActivity(intent)
        }

        btn_onboarding_one.setOnClickListener {
            startActivity(Intent(this@OnboardingOneActivity,OnboardingTwoActivity::class.java))
        }

        btn_skip_one.setOnClickListener {
            preference.setValues("onboarding","1")
            finishAffinity()
            startActivity(Intent(this@OnboardingOneActivity, SiginActivity::class.java))
        }
    }
}
