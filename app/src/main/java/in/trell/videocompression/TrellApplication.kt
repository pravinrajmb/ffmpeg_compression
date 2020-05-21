package `in`.trell.videocompression

import `in`.trell.videocompression.data.repository.VideoRepository
import `in`.trell.videocompression.data.repository.VideoRepositoryImpl
import `in`.trell.videocompression.internal.PermissionHelper
import `in`.trell.videocompression.ui.compress.CompressVideoViewModelFactory
import `in`.trell.videocompression.ui.select.SelectVideoViewModelFactory
import android.app.Application
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

const val TAG = "FFmpeg"

class TrellApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@TrellApplication))

        bind() from singleton { PermissionHelper(this@TrellApplication) }
        bind<VideoRepository>() with singleton {
            VideoRepositoryImpl(this@TrellApplication, instance<PermissionHelper>())
        }
        bind() from provider { SelectVideoViewModelFactory(instance<VideoRepository>()) }
        bind() from provider { CompressVideoViewModelFactory(instance<VideoRepository>()) }
    }

    override fun onCreate() {
        super.onCreate()
        FFmpeg.getInstance(this@TrellApplication).apply {
            try {
                this.loadBinary(object : FFmpegLoadBinaryResponseHandler {
                    override fun onFinish() {

                    }

                    override fun onSuccess() {

                    }

                    override fun onFailure() {

                    }

                    override fun onStart() {

                    }

                })
            } catch (e: FFmpegNotSupportedException) {

            }
        }
    }
}