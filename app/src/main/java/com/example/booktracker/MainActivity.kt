package com.example.booktracker

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val tv = findViewById<TextView>(R.id.tv)
        val cover = findViewById<ImageView>(R.id.coverView)
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13)
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(this)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                tv.text = barcode.displayValue
                val isbn = tv.text
                lifecycleScope.launch {
                    try {
                        Log.d("isbn", isbn.toString().uppercase())
                        val book = RetrofitClient.api.getBookById(isbn.toString())

                        Log.d("API_RESULT", "User: ${book.title}")
                        

                        Glide.with(baseContext).load("https://covers.openlibrary.org/b/isbn/${isbn}-M.jpg").into(cover)
                    } catch (e: Exception) {
                        Log.e("API_ERROR", "Error: ${e.localizedMessage}")
                    }
                }
            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
            }


    }
}