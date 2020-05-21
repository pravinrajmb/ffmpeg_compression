package `in`.trell.videocompression.ui.compress

import `in`.trell.videocompression.data.repository.VideoRepository
import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel

class CompressVideoViewModel(
    private val videoRepository: VideoRepository
) : ViewModel() {
    fun compressVideo(activity: Activity, uri: Uri, bitrate: String) {
        videoRepository.compressVideo(activity, uri, bitrate)
    }
}