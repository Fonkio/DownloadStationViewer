package fr.fonkio.downloadstationviewer.runnable

import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import fr.fonkio.downloadstationviewer.activity.DownloadListActivity

class UpdateRunnable(private val downloadListActivity: DownloadListActivity) : Runnable {

    override fun run() {
        downloadListActivity.loadList()
        Handler(Looper.getMainLooper()).postDelayed(this,2000)
    }
}