package com.fadlandev.nontonyuk.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.sign.signin.SiginActivity
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        btn_onboarding_two.setOnClickListener {
            startActivity(Intent(this@OnboardingTwoActivity,OnboardingThreeActivity::class.java))
        }

        btn_skip_two.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@OnboardingTwoActivity, SiginActivity::class.java))
        }
    }
}
