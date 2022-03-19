package fr.fonkio.downloadstationviewer.api

import fr.fonkio.downloadstationviewer.api.model.APIMultiResponses
import fr.fonkio.downloadstationviewer.api.model.APISingleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DownloadService {

    @GET("/webapi/entry.cgi?api=SYNO.API.Auth&version=3&method=login&session=DownloadStation&format=sid")
    fun login(@Query("account") account : String, @Query("passwd") passwd : String) : Call<APISingleResponse>

    @GET("/webapi/DownloadStation/task.cgi?api=SYNO.DownloadStation.Task&version=3&method=list&additional=detail,transfer")
    fun getDownloadList (@Query("_sid") sid : String) : Call<APISingleResponse>

    @GET("/webapi/DownloadStation/task.cgi?api=SYNO.DownloadStation.Task&version=1&method=pause")
    fun pause (@Query("_sid") sid : String, @Query("id") id : String) : Call<APIMultiResponses>

    @GET("/webapi/DownloadStation/task.cgi?api=SYNO.DownloadStation.Task&version=1&method=resume")
    fun resume (@Query("_sid") sid : String, @Query("id") id : String) : Call<APIMultiResponses>

    @GET("/webapi/DownloadStation/task.cgi?api=SYNO.DownloadStation.Task&version=1&method=delete&force_complete=true")
    fun delete (@Query("_sid") sid : String, @Query("id") id : String) : Call<APIMultiResponses>

    @GET("/webapi/entry.cgi?api=SYNO.API.Auth&version=7&method=logout&session=DownloadStation")
    fun logout() : Call<APISingleResponse>

}