package fr.fonkio.downloadstationviewer.api.model

data class Detail(
    val completed_time: Long,
    val connected_leechers: Int,
    val connected_peers: Int,
    val connected_seeders: Int,
    val create_time: Long,
    val destination: String,
    val seedelapsed: Long,
    val started_time: Long,
    val priority: String,
    val total_peers: Int,
    val total_pieces: Int,
    val unzip_password: String,
    val waiting_seconds: Long,
    val uri: String
)