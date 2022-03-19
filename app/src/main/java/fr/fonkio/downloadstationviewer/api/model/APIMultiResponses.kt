package fr.fonkio.downloadstationviewer.api.model

data class APIMultiResponses(
    val data: List<Data>,
    val error: Error,
    val success: Boolean
)