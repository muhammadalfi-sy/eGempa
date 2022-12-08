package com.kelompok1.egempa.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompok1.egempa.R

class LaporActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lapor)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnSubmitLaporan = findViewById<Button>(R.id.btnSubmitLaporan)
        btnSubmitLaporan.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@LaporActivity, "Terima kasih atas laporan Anda. Segera evakuasi diri Anda ke tempat aman!", Toast.LENGTH_SHORT).show()
        }
    }
}