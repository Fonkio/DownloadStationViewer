package fr.fonkio.downloadstationviewer.adapter

class Download(
    val id: String,
    val title: String,
    val size: Long,
    val status: String,
    val username: String,
    val completedTime: Long,
    val connectedLeechers: Int,
    val connectedPeers: Int,
    val connectedSeeders: Int,
    val createTime: Long,
    val destination: String,
    val seedelapsed: Long,
    val startedTime: Long,
    val totalPeers: Int,
    val totalPieces: Int,
    val unzipPassword: String,
    val uri: String,
    val waitingSeconds: Long,
    val downloadedPieces: Int,
    val sizeDownloaded: Long,
    val sizeUploaded: Long,
    val speedDownload: Long,
    val speedUpload: Long
) {


}
