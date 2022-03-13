package fr.fonkio.downloadstationviewer.api

class Task {
    lateinit var id : String
    lateinit var type : String
    lateinit var username : String
    lateinit var title : String
    var size : Long = 0L
    lateinit var status : String
    lateinit var additional : Additional
}
