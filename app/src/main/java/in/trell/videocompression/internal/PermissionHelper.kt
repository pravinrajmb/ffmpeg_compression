package `in`.trell.videocompression.internal

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val PERMISSION_REQUEST_CODE = 2

class PermissionHelper(private val context: Context) {
    private val preferencesName = "Permission_Preferences"

    private val sharedPreferences = context.getSharedPreferences(preferencesName, MODE_PRIVATE)

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        rationaleDialogMessage: String = "This permission is mandatory to proceed further in the application.",
        openSettingsDialogMessage: String = "This permission is mandatory to proceed further in the application. Please enable this permission from the settings screen."
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions.first())) {
            sharedPreferences.edit().apply {
                putBoolean(permissions.first(), true)
                    .apply()
            }

            showAlertDialog(activity, rationaleDialogMessage) {
                requestForPermission(
                    activity,
                    permissions
                )
            }

        } else {
            if (sharedPreferences.getBoolean(permissions.first(), false)) {
                showAlertDialog(activity, openSettingsDialogMessage) { openSettings(activity) }
            } else {
                requestForPermission(activity, permissions)
            }

        }
    }

    private fun requestForPermission(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
    }

    private fun openSettings(activity: Activity) {
        activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", activity.packageName, null)
        })
    }

    private fun showAlertDialog(activity: Activity, message: String, action: () -> Unit) {
        AlertDialog.Builder(activity)
            .setTitle("Action required.")
            .setMessage(message)
            .setPositiveButton(
                "Grant Permission"
            ) { dialog, _ ->
                dialog?.dismiss()
                action()
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog?.dismiss() }
            .create().apply { show() }
    }
}