package fr.fonkio.downloadstationviewer.api.model

data class APISingleResponse(
    val data: Data,
    val error: Error,
    val success: Boolean
)