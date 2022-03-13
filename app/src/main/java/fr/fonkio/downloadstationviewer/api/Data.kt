package fr.fonkio.downloadstationviewer.api

class Data {
    lateinit var total : Integer
    lateinit var offset : Integer
    var tasks : MutableList<Task> = mutableListOf()
}