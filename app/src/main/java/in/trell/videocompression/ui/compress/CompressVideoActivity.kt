package `in`.trell.videocompression.ui.compress

import `in`.trell.videocompression.R
import `in`.trell.videocompression.data.repository.ACTUAL_VIDEO_URI
import `in`.trell.videocompression.internal.PERMISSION_REQUEST_CODE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_compress_videp.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class CompressVideoActivity : AppCompatActivity(), KodeinAware {

    private lateinit var data: Uri

    private val viewModelFactory by instance<CompressVideoViewModelFactory>()

    private lateinit var viewModel: CompressVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compress_videp)

        data = intent?.extras?.get(ACTUAL_VIDEO_URI) as Uri

        viewModel = ViewModelProviders.of(this@CompressVideoActivity, viewModelFactory)
            .get(CompressVideoViewModel::class.java)

        video_player.setVideoURI(data)
        video_player.start()
    }

    fun compressVideo(view: View?) {
        video_player.pause()
        viewModel.compressVideo(
            this@CompressVideoActivity,
            data,
            bit_rate_to_compress.text.toString().trim()
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            var allPermissionsGranted = true
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    allPermissionsGranted = false
                }
            }
            if (allPermissionsGranted) {
                compressVideo(null)
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override val kodein: Kodein by closestKodein()
}
