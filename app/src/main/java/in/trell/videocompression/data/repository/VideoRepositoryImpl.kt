package `in`.trell.videocompression.data.repository

import `in`.trell.videocompression.internal.PermissionHelper
import `in`.trell.videocompression.internal.utils.FileUtils
import `in`.trell.videocompression.ui.compress.CompressVideoActivity
import `in`.trell.videocompression.ui.preview.PreviewCompressedVideoActivity
import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import java.io.File

const val TYPE_VIDEO = "video/*"
const val VIDEO_PICKER_REQUEST_CODE = 12
const val ACTUAL_VIDEO_URI = "ACTUAL_VIDEO_URI"
const val COMPRESSED_VIDEO_URI = "COMPRESSED_VIDEO_URI"

class VideoRepositoryImpl(
    private val context: Context,
    private val permissionHelper: PermissionHelper
) : VideoRepository {
    override fun openVideoSelection(activity: Activity) {
        activity.startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            ).apply {
                this.type = TYPE_VIDEO
            }, VIDEO_PICKER_REQUEST_CODE
        )
    }

    override fun onVideoSelected(activity: Activity, uri: Uri) {
        activity.startActivity(Intent(activity, CompressVideoActivity::class.java).apply {
            this.putExtra(ACTUAL_VIDEO_URI, uri)
        })
    }

    override fun compressVideo(activity: Activity, uri: Uri, bitrate: String) {
        if (permissionHelper.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            val folder = context.getExternalFilesDir("CompressedVideos")

            if (!folder!!.exists())
                folder.mkdir()

            val filePath = folder.absolutePath + "/${System.currentTimeMillis()}.mp4"

            val command = arrayOf(
                "-i",
                FileUtils(context).getRealPathFromURI(uri),
                "-b:v",
                bitrate,
                filePath
            )

            val progressDialog = ProgressDialog(activity).apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                setMessage("Compressing...")
            }

            try {
                FFmpeg.getInstance(context)
                    .execute(command, object : ExecuteBinaryResponseHandler() {

                        override fun onStart() {
                            super.onStart()
                            progressDialog.show()
                        }

                        override fun onFailure(message: String?) {
                            super.onFailure(message)
                            if (progressDialog.isShowing) progressDialog.dismiss()
                            Toast.makeText(activity, "Invalid bitrate", Toast.LENGTH_SHORT).show()
                        }

                        override fun onSuccess(message: String?) {
                            super.onSuccess(message)
                            if (progressDialog.isShowing) progressDialog.dismiss()
                            activity.startActivity(
                                Intent(
                                    activity,
                                    PreviewCompressedVideoActivity::class.java
                                ).apply {
                                    flags = FLAG_ACTIVITY_NEW_TASK
                                    this.putExtra(
                                        COMPRESSED_VIDEO_URI,
                                        Uri.fromFile(File(filePath))
                                    )
                                })
                        }

                        override fun onFinish() {
                            super.onFinish()
                            if (progressDialog.isShowing) progressDialog.dismiss()
                        }
                    })
            } catch (e: FFmpegCommandAlreadyRunningException) {

            }
        } else permissionHelper.requestPermission(
            activity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }


}