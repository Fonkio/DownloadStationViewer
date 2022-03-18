package fr.fonkio.downloadstationviewer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.downloadstationviewer.api.DownloadService
import fr.fonkio.downloadstationviewer.api.ServiceBuilder
import fr.fonkio.downloadstationviewer.model.APIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DownloadListActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var sb : ServiceBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_list)
        recyclerView = findViewById(R.id.recyclerView)

        val sharedPref = getSharedPreferences("ID", Context.MODE_PRIVATE)
        val user = sharedPref.getString("username", "user")
        val url = sharedPref.getString("ip", "")
        val pwd = sharedPref.getString("password", "pwd")
        if (url != null && user != null && pwd != null) {
            sb = ServiceBuilder(url)
            login(user, pwd)

        }
    }

    private fun loadDownload() {
        //initiate the service

        val downloadService  = sb.buildService(DownloadService::class.java)
        val requestCall = downloadService.getDownloadList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val countryList  = response.body()!!
                    Log.d("Response", "countrylist size : ${countryList.error.code} ")
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@DownloadListActivity,2)

                        val dataList = response.body()!!

                        adapter = DownloadAdapter(mutableListOf())
                        logout()
                    }
                }else{
                    Toast.makeText(this@DownloadListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(this@DownloadListActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun login(account: String, pwd: String) {
        //initiate the service

        val downloadService  = sb.buildService(DownloadService::class.java)
        val requestCall = downloadService.login(
            account, pwd
        )
        //make network call asynchronously
        requestCall.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    Log.d("Response", "connecté !")
                    loadDownload()
                }else{
                    Toast.makeText(this@DownloadListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(
                    this@DownloadListActivity,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun logout() {
        //initiate the service

        val downloadService  = sb.buildService(DownloadService::class.java)
        val requestCall = downloadService.logout()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    Log.d("Response", "Déconnecté !")
                }else{
                    Toast.makeText(this@DownloadListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(this@DownloadListActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}