package fr.fonkio.downloadstationviewer.model

data class APIResponse(
    val data: Data,
    val error: Error,
    val success: Boolean
)