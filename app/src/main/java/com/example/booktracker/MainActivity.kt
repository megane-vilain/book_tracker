package com.example.booktracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
            }*/

        findViewById<BottomNavigationView>(R.id.bottom_nav_menu).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
            }
            return@setOnItemSelectedListener true
        }

        findViewById<FloatingActionButton>(R.id.add_button).setOnClickListener() {
            //val isbn: String? = scanQrCode()
            val isbn = "9780553573428"
            val dialog = BookDialogFragment.newInstance(isbn)
            dialog.show(supportFragmentManager, "New book")
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun scanQrCode(): String? {
        val scanner = GmsBarcodeScanning.getClient(this)
        var barcode: String? = null;
        scanner.startScan()
            .addOnSuccessListener { scan ->
                barcode = scan.displayValue
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // TODO: Log the error
            }
        return barcode
    }
}