package fr.fonkio.downloadstationviewer.api.model

data class Task(
    val additional: Additional,
    val id: String,
    val size: Long,
    val status: String,
    val status_extra: Any,
    val title: String,
    val type: String,
    val username: String
)