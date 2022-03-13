package fr.fonkio.downloadstationviewer.api

class Detail {

    lateinit var connected_leechers : Integer
    lateinit var connected_seeders : Integer
    var create_time : Long = 0L
    lateinit var destination : String
    lateinit var priority : String
    lateinit var total_peers : Integer
    lateinit var uri : String
}
