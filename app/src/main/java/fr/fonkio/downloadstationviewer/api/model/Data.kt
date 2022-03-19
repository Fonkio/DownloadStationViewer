package fr.fonkio.downloadstationviewer.api.model

data class Data(
    val offset: Int,
    val tasks: List<Task>,
    val total: Int,
    val account: String,
    val device_id: String,
    val ik_message: String,
    val is_portal_port: String,
    val sid: String,
    val synotoken: String,
    val error: Int,
    val id: String
)