package `in`.trell.videocompression.internal.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


class FileUtils(private val context: Context) {
    fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        val cursor: Cursor? = context.contentResolver.query(
            contentUri,
            proj,
            null,
            null,
            null
        ) //Since manageQuery is deprecated
        val columnIndex: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()
        return path
    }
}