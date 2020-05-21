package `in`.trell.videocompression.ui.select

import `in`.trell.videocompression.data.repository.VideoRepository
import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel

class SelectVideoViewModel(
    private val videoRepository: VideoRepository
) : ViewModel() {
    fun openVideoPicker(activity: Activity) {
        videoRepository.openVideoSelection(activity)
    }

    fun onVideoSelected(activity: Activity, uri: Uri) {
        videoRepository.onVideoSelected(activity, uri)
    }
}