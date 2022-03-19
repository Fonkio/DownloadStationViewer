package fr.fonkio.downloadstationviewer.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.downloadstationviewer.adapter.Download
import fr.fonkio.downloadstationviewer.adapter.DownloadAdapter
import fr.fonkio.downloadstationviewer.R
import fr.fonkio.downloadstationviewer.api.DownloadService
import fr.fonkio.downloadstationviewer.api.ServiceBuilder
import fr.fonkio.downloadstationviewer.api.model.APISingleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DownloadListActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var sb : ServiceBuilder
    private val downloadList = mutableListOf<Download>()
    private var sid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_list)
        recyclerView = findViewById(R.id.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.downloadmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemDisconnect -> {
                val sharedPref = getSharedPreferences("ID", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()

                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
                finish()
            }
            R.id.itemRefresh -> {
                loadList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences("ID", Context.MODE_PRIVATE)
        val user = sharedPref.getString("username", "user")
        val url = sharedPref.getString("ip", "")
        val pwd = sharedPref.getString("password", "pwd")
        if (url != null && user != null && pwd != null) {
            sb = ServiceBuilder(url)
            login(user, pwd)
        }
    }

    fun loadList() {
        downloadList.removeAll(downloadList)
        val sidNow = sid
        if (sidNow != null) {
            loadDownload(sidNow)
        }
    }

    override fun onPause() {
        logout()
        super.onPause()
    }

    fun refreshClicked(v: View) {
        loadList()
        Toast.makeText(this, R.string.refreched, Toast.LENGTH_SHORT).show()
    }

    private fun loadDownload(sid: String) {
        //initiate the service

        val downloadService  = sb.buildService(DownloadService::class.java)
        val requestCall = downloadService.getDownloadList(sid)
        //make network call asynchronously
        requestCall.enqueue(object : Callback<APISingleResponse> {
            override fun onResponse(call: Call<APISingleResponse>, response: Response<APISingleResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val responseDownload  = response.body()!!
                    for(task in responseDownload.data.tasks) {
                        val download = Download(
                            task.id,
                            task.title,
                            task.size,
                            task.status,
                            task.username,
                            task.additional.detail.completed_time,
                            task.additional.detail.connected_leechers,
                            task.additional.detail.connected_peers,
                            task.additional.detail.connected_seeders,
                            task.additional.detail.create_time,
                            task.additional.detail.destination,
                            task.additional.detail.seedelapsed,
                            task.additional.detail.started_time,
                            task.additional.detail.total_peers,
                            task.additional.detail.total_pieces,
                            task.additional.detail.unzip_password,
                            task.additional.detail.uri,
                            task.additional.detail.waiting_seconds,
                            task.additional.transfer.downloaded_pieces,
                            task.additional.transfer.size_downloaded,
                            task.additional.transfer.size_uploaded,
                            task.additional.transfer.speed_download,
                            task.additional.transfer.speed_upload
                        )
                        downloadList.add(download)

                    }
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@DownloadListActivity,1)
                        adapter = DownloadAdapter(downloadList, sb, sid, this@DownloadListActivity)
                    }
                }else{
                    Toast.makeText(this@DownloadListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<APISingleResponse>, t: Throwable) {
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
        requestCall.enqueue(object : Callback<APISingleResponse> {
            override fun onResponse(call: Call<APISingleResponse>, response: Response<APISingleResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val loginResponse  = response.body()!!
                    Log.d("Response", "connecté !")
                    sid = loginResponse.data.sid
                    loadList()
                }else{
                    Toast.makeText(this@DownloadListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<APISingleResponse>, t: Throwable) {
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
        requestCall.enqueue(object : Callback<APISingleResponse> {
            override fun onResponse(call: Call<APISingleResponse>, response: Response<APISingleResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    sid = null
                    Log.d("Response", "Déconnecté !")
                }else{
                    Toast.makeText(this@DownloadListActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<APISingleResponse>, t: Throwable) {
                Toast.makeText(this@DownloadListActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }


}