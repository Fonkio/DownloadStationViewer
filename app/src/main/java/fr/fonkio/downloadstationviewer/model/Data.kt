package fr.fonkio.downloadstationviewer.model

data class Data(
    val offset: Int,
    val tasks: List<Task>,
    val total: Int
)