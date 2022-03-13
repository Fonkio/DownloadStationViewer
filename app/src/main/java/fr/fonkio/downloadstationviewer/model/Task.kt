package fr.fonkio.downloadstationviewer.model

data class Task(
    val additional: Additional,
    val id: String,
    val size: String,
    val status: String,
    val status_extra: Any,
    val title: String,
    val type: String,
    val username: String
)