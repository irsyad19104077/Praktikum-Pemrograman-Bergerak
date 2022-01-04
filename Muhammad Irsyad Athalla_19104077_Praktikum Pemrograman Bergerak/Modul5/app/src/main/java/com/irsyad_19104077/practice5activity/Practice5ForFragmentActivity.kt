package com.irsyad_19104077.practice5activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager

class Practice5ForFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice5_for_fragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToSecondFragment.setOnClickListener{
            val namaSaya = inputNamaSaya.text.toString()
            if (namaSaya.isEmpty()) {
                inputNamaSaya.error = "Nama Tidak Boleh Kosong"
                return@setOnClickListener
            }
            val nimSaya = inputNimSaya.text.toString()
            if (nimSaya.isEmpty()) {
                inputNimSaya.error = "Nim Tidak Boleh Kosong"
                return@setOnClickListener
            }
            val mReadDataFragment = Practice5ReadDataFragment()
            val mBundle = Bundle()
            mBundle.putString(Practice5ReadDataFragment.EXTRA_NAMA, namaSaya)
            mReadDataFragment.arguments = mBundle
            mReadDataFragment.nim = nimSaya.toInt()
            val mFragmentManager = fragmentManager as FragmentManager
            mFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, mReadDataFragment, Practice5ReadDataFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }
}