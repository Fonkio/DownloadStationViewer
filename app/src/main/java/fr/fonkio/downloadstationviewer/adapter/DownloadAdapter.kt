package fr.fonkio.downloadstationviewer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.downloadstationviewer.R
import fr.fonkio.downloadstationviewer.activity.DownloadListActivity
import fr.fonkio.downloadstationviewer.api.DownloadService
import fr.fonkio.downloadstationviewer.api.ServiceBuilder
import fr.fonkio.downloadstationviewer.api.model.APIMultiResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.pow
import kotlin.math.roundToInt

class DownloadAdapter(private val downloadList: List<Download>, private val sb: ServiceBuilder, private val sid : String, private val downloadListActivity: DownloadListActivity) : RecyclerView.Adapter<DownloadAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_download, parent,false)
        return ViewHolder(view, sb, sid, downloadListActivity)
    }

    override fun getItemCount(): Int {
        return downloadList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${downloadList.size}, $position")


        return holder.bind(downloadList[position])

    }

    class ViewHolder(itemView: View, private val sb: ServiceBuilder, private val sid : String, val downloadListActivity: DownloadListActivity) :RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val tvUsername: TextView = itemView.findViewById(R.id.textViewUsername)
        private val tvStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        private val tvSizeDownloaded: TextView = itemView.findViewById(R.id.textViewSizeDownloaded)
        private val tvSizeUploaded: TextView = itemView.findViewById(R.id.textViewSizeUpload)
        private val tvSize: TextView = itemView.findViewById(R.id.textViewSize)
        private val tvUnit: TextView = itemView.findViewById(R.id.textViewSizeUnit)
        private val tvPercentage: TextView = itemView.findViewById(R.id.textViewPercentage)
        private val tvSpeedDownload: TextView = itemView.findViewById(R.id.textViewSpeedDownload)
        private val tvSpeedUpload: TextView = itemView.findViewById(R.id.textViewSpeedUpload)
        private val tvSpeedUnit: TextView = itemView.findViewById(R.id.textViewSpeedUnit)
        private val bPlay: ImageButton = itemView.findViewById(R.id.buttonPlay)
        private val bPause: ImageButton = itemView.findViewById(R.id.buttonPause)
        private val bDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)

        private val pbDownload: ProgressBar = itemView.findViewById(R.id.progressBarDownload)

        fun bind(dl: Download) {
            val id = dl.id
            val title = dl.title
            val username = dl.username
            val status = dl.status
            val progressMax = dl.size
            val secondaryProgress = dl.sizeDownloaded
            val progress = (dl.sizeUploaded / 5).toInt()

            val sizeDownloadedOctet = dl.sizeDownloaded
            val sizeOctet = dl.size
            val sizeUploadedOctet = dl.sizeUploaded

            val percentageUpload = ((sizeUploadedOctet/(sizeOctet + 0F)) * 100F).toInt()
            val percentageDownload = ((sizeDownloadedOctet/(sizeOctet + 0F)) * 100F).toInt()
            val percentageToShow : String

            val maxUnitSize = maxConvertUnit(sizeOctet)
            val size = convertUnit(sizeOctet, maxUnitSize)
            val sizeDownloaded = convertUnit(sizeDownloadedOctet, maxUnitSize)
            val sizeUploaded = convertUnit(sizeUploadedOctet, maxUnitSize)
            val unitSize = getUnit(maxUnitSize)

            val speedUploadOctet = dl.speedUpload
            val speedDownloadOctet = dl.speedDownload

            val maxUnitSpeedUpload = maxConvertUnit(speedUploadOctet)
            val maxUnitSpeedDownload = maxConvertUnit(speedDownloadOctet)
            var unitSpeed = "/s"

            val speedUpload = convertUnit(speedUploadOctet, maxUnitSpeedUpload)
            val speedDownload = convertUnit(speedDownloadOctet, maxUnitSpeedDownload)

            when (status) {
                "finished" -> {
                    tvSizeUploaded.visibility = View.VISIBLE
                    tvSizeDownloaded.visibility = View.GONE
                    tvSpeedUpload.visibility = View.GONE
                    tvSpeedDownload.visibility = View.GONE
                    tvSpeedUnit.visibility = View.GONE
                    bPlay.visibility = View.GONE
                    bPause.visibility = View.GONE
                    bDelete.visibility = View.VISIBLE
                    percentageToShow = percentageUpload.toString()
                }
                "seeding" -> {
                    tvSizeUploaded.visibility = View.VISIBLE
                    tvSizeDownloaded.visibility = View.GONE
                    tvSpeedUpload.visibility = View.VISIBLE
                    tvSpeedDownload.visibility = View.GONE
                    tvSpeedUnit.visibility = View.VISIBLE
                    unitSpeed = "${getUnit(maxUnitSpeedUpload)}${unitSpeed}"
                    bPlay.visibility = View.GONE
                    bPause.visibility = View.VISIBLE
                    bDelete.visibility = View.VISIBLE
                    percentageToShow = percentageUpload.toString()
                }
                "downloading" -> {
                    tvSizeUploaded.visibility = View.GONE
                    tvSizeDownloaded.visibility = View.VISIBLE
                    tvSpeedUpload.visibility = View.GONE
                    tvSpeedDownload.visibility = View.VISIBLE
                    tvSpeedUnit.visibility = View.VISIBLE
                    unitSpeed = "${getUnit(maxUnitSpeedDownload)}${unitSpeed}"
                    bPlay.visibility = View.GONE
                    bPause.visibility = View.VISIBLE
                    bDelete.visibility = View.VISIBLE
                    percentageToShow = percentageDownload.toString()
                }
                "paused" -> {
                    tvSizeUploaded.visibility = View.GONE
                    tvSpeedUpload.visibility = View.GONE
                    tvSpeedDownload.visibility = View.GONE
                    tvSpeedUnit.visibility = View.GONE
                    if (sizeDownloadedOctet < sizeOctet) {
                        tvSizeUploaded.visibility = View.GONE
                        tvSizeDownloaded.visibility = View.VISIBLE
                        percentageToShow = percentageDownload.toString()
                    } else {
                        tvSizeUploaded.visibility = View.VISIBLE
                        tvSizeDownloaded.visibility = View.GONE
                        percentageToShow = percentageUpload.toString()
                    }
                    bPlay.visibility = View.VISIBLE
                    bPause.visibility = View.GONE
                    bDelete.visibility = View.VISIBLE
                }
                else -> {
                    tvSizeUploaded.visibility = View.GONE
                    tvSizeDownloaded.visibility = View.GONE
                    tvSpeedUpload.visibility = View.GONE
                    tvSpeedDownload.visibility = View.GONE
                    tvSpeedUnit.visibility = View.GONE
                    tvSpeedUnit.visibility = View.GONE
                    bPlay.visibility = View.GONE
                    bPause.visibility = View.GONE
                    bDelete.visibility = View.VISIBLE
                    percentageToShow = percentageDownload.toString()
                }
            }

            tvPercentage.text = percentageToShow
            tvTitle.text = title
            tvUsername.text = username
            tvStatus.text = status
            tvSizeDownloaded.text = roundToString(sizeDownloaded)
            tvSizeUploaded.text = roundToString(sizeUploaded)
            tvSize.text = roundToString(size)
            tvUnit.text = unitSize
            tvSpeedDownload.text = roundToString(speedDownload)
            tvSpeedUpload.text = roundToString(speedUpload)
            tvSpeedUnit.text = unitSpeed

            pbDownload.max = 100

            pbDownload.progress = percentageUpload
            pbDownload.secondaryProgress = percentageDownload


            bPlay.setOnClickListener {
                val downloadService  = sb.buildService(DownloadService::class.java)
                val requestCall = downloadService.resume(sid, id)
                requestCall.enqueue(object : Callback<APIMultiResponses> {
                    override fun onResponse(call: Call<APIMultiResponses>, response: Response<APIMultiResponses>) {
                        Log.d("Response", "onResponse: ${response.body()}")
                        if (response.isSuccessful){
                            downloadListActivity.loadList()
                        }else{
                            Log.d("Response", "Something went wrong ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<APIMultiResponses>, t: Throwable) {
                        Log.d("Response", "Something went wrong $t")
                    }
                })
            }
            bPause.setOnClickListener {
                val downloadService  = sb.buildService(DownloadService::class.java)
                val requestCall = downloadService.pause(sid, id)
                requestCall.enqueue(object : Callback<APIMultiResponses> {
                    override fun onResponse(call: Call<APIMultiResponses>, response: Response<APIMultiResponses>) {
                        Log.d("Response", "onResponse: ${response.body()}")
                        if (response.isSuccessful){
                            downloadListActivity.loadList()
                        }else{
                            Log.d("Response", "Something went wrong ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<APIMultiResponses>, t: Throwable) {
                        Log.d("Response", "Something went wrong $t")
                    }
                })
            }
            bDelete.setOnClickListener {
                val downloadService  = sb.buildService(DownloadService::class.java)
                val requestCall = downloadService.delete(sid, id)
                requestCall.enqueue(object : Callback<APIMultiResponses> {
                    override fun onResponse(call: Call<APIMultiResponses>, response: Response<APIMultiResponses>) {
                        Log.d("Response", "onResponse: ${response.body()}")
                        if (response.isSuccessful){
                            downloadListActivity.loadList()
                        }else{
                            Log.d("Response", "Something went wrong ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<APIMultiResponses>, t: Throwable) {
                        Log.d("Response", "Something went wrong $t")
                    }
                })
            }

        }

        private fun maxConvertUnit(octet : Long) : Int {
            var i = 0
            var octetRemaining = octet
            while (octetRemaining >= 1024L) {
                i++
                octetRemaining /= 1024L
            }
            return i.coerceAtMost(4)
        }

        private fun getUnit(nb : Int) = when (nb) {
            0 -> "B"
            1 -> "KB"
            2 -> "MB"
            3 -> "GB"
            else -> "TB"
        }

        private fun convertUnit(octet : Long, nb : Int) = octet / (1024F.pow(nb))

        private fun roundToString(sizeDownloaded: Float) =
            ((sizeDownloaded * 1000.0).roundToInt() / 1000.0).toString()

    }
}