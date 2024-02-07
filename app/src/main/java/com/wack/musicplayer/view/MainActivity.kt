package com.wack.musicplayer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wack.musicplayer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frm_container, PlayerFragment.newInstance())
            .commit()
    }
}