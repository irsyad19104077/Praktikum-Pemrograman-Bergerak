package com.irsyad_19104077.practice5activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Practice5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions()

        btnProdi.setOnClickListener{
            val namaProdi = inputProdi.text.toString()
            if(namaProdi.isEmpty()){
                inputProdi.error="Prodi Tidak Boleh Kosong"
                return@setOnClickListener
            }
            val moveWitDataIntent = Intent(this@Practice5Activity,Practice5ReadDataActivity::class.java)
            moveWitDataIntent.putExtra(Practice5ReadDataActivity.EXTRA_PRODI,namaProdi)
            startActivity(moveWitDataIntent)
        }
        btnCallBrowser.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://ittelkom-pwt.ac.id/")
            startActivity(intent)
        }
        btnCallCamera.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }
        btnCallPhone.setOnClickListener{
            val phoneNumber = inputPhoneNumber.getText()
            if (phoneNumber.isEmpty()) {
                inputPhoneNumber.error = "Nomor Telpon Tidak Boleh Kosong"
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + phoneNumber)
            startActivity(intent)
        }
        btnFragment.setOnClickListener{
            val intent = Intent(this, Practice5ForFragmentActivity::class.java)
            startActivity(intent)
        }
    }
    val CALL_REQUEST_CODE = 100
    @SuppressLint("MissingPermission")
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_REQUEST_CODE)
        }
    }

}