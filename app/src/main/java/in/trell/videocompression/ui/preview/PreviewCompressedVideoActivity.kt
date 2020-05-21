package `in`.trell.videocompression.ui.preview

import `in`.trell.videocompression.R
import `in`.trell.videocompression.data.repository.COMPRESSED_VIDEO_URI
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_preview_compressed.*

class PreviewCompressedVideoActivity : AppCompatActivity() {

    private var isPlaying: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_compressed)

        val uri = intent?.extras?.get(COMPRESSED_VIDEO_URI) as Uri

        video_view.setVideoURI(uri)

        video_view.start()
    }

    fun togglePlayBack(view: View) {
        if (isPlaying) {
            isPlaying = false
            playback_controls.text = resources.getString(R.string.play)
            video_view.pause()
        } else {
            isPlaying = true
            playback_controls.text = resources.getString(R.string.pause)
            video_view.start()
        }
    }


}
