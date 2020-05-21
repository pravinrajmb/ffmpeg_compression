package `in`.trell.videocompression.ui.select

import `in`.trell.videocompression.R
import `in`.trell.videocompression.data.repository.VIDEO_PICKER_REQUEST_CODE
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SelectVideoActivity : AppCompatActivity(), KodeinAware {

    private val viewModelFactory by instance<SelectVideoViewModelFactory>()

    private lateinit var viewModel: SelectVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_video)

        viewModel = ViewModelProviders.of(this@SelectVideoActivity, viewModelFactory)
            .get(SelectVideoViewModel::class.java)
    }

    fun selectVideoFromGallery(view: View) {
        viewModel.openVideoPicker(this@SelectVideoActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        if (requestCode == VIDEO_PICKER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.onVideoSelected(this@SelectVideoActivity, intent?.data!!)
            }
        }

        super.onActivityResult(requestCode, resultCode, intent)
    }

    override val kodein: Kodein by closestKodein()
}
