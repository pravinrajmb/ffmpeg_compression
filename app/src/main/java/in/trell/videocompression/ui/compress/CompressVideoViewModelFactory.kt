package `in`.trell.videocompression.ui.compress

import `in`.trell.videocompression.data.repository.VideoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class CompressVideoViewModelFactory(private val videoRepository: VideoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompressVideoViewModel(videoRepository) as T;
    }
}