package fr.fonkio.downloadstationviewer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DownloadAdapter(private val downloadList : List<Download>) : RecyclerView.Adapter<DownloadAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_download, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return downloadList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${downloadList.size} ")


        return holder.bind(downloadList[position])

    }

    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        var tvNom = itemView.findViewById<TextView>(R.id.textViewNom)
        var pbDownload = itemView.findViewById<TextView>(R.id.progressBarDownload)

        fun bind(dl: Download) {
            val name = dl.name
            tvNom.text = name
        }

    }
}