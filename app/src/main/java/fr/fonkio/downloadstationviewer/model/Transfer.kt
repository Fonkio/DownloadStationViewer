package fr.fonkio.downloadstationviewer.model

data class Transfer(
    val size_downloaded: String,
    val size_uploaded: String,
    val speed_download: String,
    val speed_upload: String
)