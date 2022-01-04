package com.irsyad_19104077.practice5activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Practice5ReadDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice5_read_data)

        val prodi = intent.getStringExtra(EXTRA_PRODI)
        lblProdiSaya.text = "Prodi Saya Adalah $prodi"
    }
    companion object{
        const val EXTRA_PRODI = "extra_prodi"
    }
}