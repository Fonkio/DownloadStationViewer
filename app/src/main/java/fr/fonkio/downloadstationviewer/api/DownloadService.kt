package fr.fonkio.downloadstationviewer.api

import fr.fonkio.downloadstationviewer.model.APIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DownloadService {

    @GET("/webapi/DownloadStation/task.cgi?api=SYNO.DownloadStation.Task&version=3&method=list")
    fun getDownloadList () : Call<APIResponse>

    @GET("/webapi/entry.cgi?api=SYNO.API.Auth&version=7&method=login&session=DownloadStation&format=cookie")
    fun login(@Query("account") account : String, @Query("passwd") passwd : String) : Call<APIResponse>

    @GET("/webapi/entry.cgi?api=SYNO.API.Auth&version=7&method=logout&session=DownloadStation")
    fun logout() : Call<APIResponse>

}