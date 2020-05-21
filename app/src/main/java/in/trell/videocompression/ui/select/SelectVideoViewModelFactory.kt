package `in`.trell.videocompression.ui.select

import `in`.trell.videocompression.data.repository.VideoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class SelectVideoViewModelFactory(private val videoRepository: VideoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SelectVideoViewModel(videoRepository) as T;
    }
}