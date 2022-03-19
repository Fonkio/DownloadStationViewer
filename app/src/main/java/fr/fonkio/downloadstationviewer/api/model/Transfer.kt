package fr.fonkio.downloadstationviewer.api.model

data class Transfer(
    val downloaded_pieces: Int,
    val size_downloaded: Long,
    val size_uploaded: Long,
    val speed_download: Long,
    val speed_upload: Long
)