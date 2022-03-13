package fr.fonkio.downloadstationviewer.model

data class Detail(
    val connected_leechers: Int,
    val connected_seeders: Int,
    val create_time: String,
    val destination: String,
    val priority: String,
    val total_peers: Int,
    val uri: String
)