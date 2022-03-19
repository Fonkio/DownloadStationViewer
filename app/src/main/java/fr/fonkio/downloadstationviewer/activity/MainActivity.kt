package fr.fonkio.downloadstationviewer.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import fr.fonkio.downloadstationviewer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("ID", Context.MODE_PRIVATE)

        if (sharedPref.getBoolean("connectionAuto", false)) {
            gotoDownloadListActivity()
        }

        setContentView(R.layout.activity_main)
    }

    fun connect(v : View) {
        val editTextIp = findViewById<EditText>(R.id.editTextIP)
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        val sharedPref = getSharedPreferences("ID", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("ip", editTextIp.text.toString())
        editor.putString("username", editTextUsername.text.toString())
        editor.putString("password", editTextPassword.text.toString())
        editor.putBoolean("connectionAuto", true)
        editor.apply()

        gotoDownloadListActivity()
    }

    private fun gotoDownloadListActivity() {
        val myIntent = Intent(this, DownloadListActivity::class.java)
        startActivity(myIntent)
        finish()
    }

}