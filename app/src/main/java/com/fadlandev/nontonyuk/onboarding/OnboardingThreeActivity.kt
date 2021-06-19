package com.fadlandev.nontonyuk.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlandev.nontonyuk.R
import com.fadlandev.nontonyuk.sign.signin.SiginActivity
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class OnboardingThreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        btn_skip_three.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@OnboardingThreeActivity, SiginActivity::class.java))
        }
    }
}
