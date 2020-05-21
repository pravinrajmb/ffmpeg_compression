package `in`.trell.videocompression.data.repository

import android.app.Activity
import android.net.Uri

interface VideoRepository {
    fun openVideoSelection(activity: Activity)
    fun onVideoSelected(activity: Activity, uri: Uri)
    fun compressVideo(activity: Activity, uri: Uri, bitrate: String)
}