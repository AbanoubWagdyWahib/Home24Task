package com.home24task.UI.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.home24task.R
import com.home24task.UI.selection.SelectionActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnStart.setOnClickListener {
            val intent = Intent(applicationContext, SelectionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
