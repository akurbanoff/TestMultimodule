package ru.axas.core.base.extension

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.axas.core.R
import ru.axas.core.base.utils.logV
import ru.axas.core.base.utils.logW
import ru.axas.core.base.utils.PermissionsModule.Companion.launchPermissionCameraAndGallery
import ru.axas.core.theme.res.TextApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Date


const val DEFAULT_CHANNEL_ID_FCM_DOWNLOAD = "fcm_default_channel_download"

@SuppressLint("Recycle")
fun Context.outputFile(uri: Uri): File? {
    val input = this.contentResolver.openInputStream(uri) ?: return null
    val dataName = System.currentTimeMillis().toTimeStringForeFile()
    val outputFile = this.filesDir.resolve("${dataName}_new_picture.jpg")
    input.copyTo(outputFile.outputStream())
    return outputFile
}

fun File.getUriForFile(context: Context): Uri {
    return FileProvider.getUriForFile(context,
        "${context.applicationInfo.packageName}.fileprovider",
        this)
}

@Throws(IOException::class)
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = Date().toTimeStringForeFile()
    val storageDir = cacheDir
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        deleteOnExit()
    }
}


fun Context.makeUri() = this.createImageFile().getUriForFile(this)


fun Context.downloadFile(link: String): Long = try {
    val nameFile = link.substringAfterLast("/")
    val request = DownloadManager.Request(Uri.parse(link))
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, nameFile)
    val manager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
} catch (e: Exception) {
    e.printStackTrace()
    0L
}


fun downloadFromUrlExt(context: Context, link: String) {

        val nameFileDownload = link.substringAfterLast("/")
        val fillNamePatch = patchFromPictures(nameFileDownload)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(fillNamePatch.absolutePath), getFileMimeType(nameFileDownload))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(context, 101, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID_FCM_DOWNLOAD)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle(nameFileDownload)
                .setAutoCancel(true)
                .setVibrate(null)
                .setSound(null)
                .setOnlyAlertOnce(false)
                .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(DEFAULT_CHANNEL_ID_FCM_DOWNLOAD,
            "ru.axas.winbi",
            NotificationManager.IMPORTANCE_HIGH)

        notificationManager.createNotificationChannel(notificationChannel)
    CoroutineScope(Dispatchers.Main).launch {
        withContext(Dispatchers.IO){
            try {
                val connection = URL(link).openConnection() as? HttpURLConnection
                connection?.let {
                    it.connect()
                    val inputStream = it.inputStream
                    val fileOutputStream = FileOutputStream(fillNamePatch)
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    var totalBytesRead: Long = 0
                    val totalFileSize = connection.contentLength.toLong()
                    var progress: Int

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead.toLong()
                        progress = ((totalBytesRead * 100) / totalFileSize).toInt()
                        builder.setProgress(100, progress, false)
                        notificationManager.notify(101, builder.build())
                    }

                    fileOutputStream.flush()
                    fileOutputStream.close()
                    inputStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        builder.setProgress(0, 0, false)
        notificationManager.notify(101, builder.build())
    }
}

@Composable
fun rememberDownloadManagerFromUrlTest3(link: String): () -> Unit {
    val context = LocalContext.current
    return launchPermissionCameraAndGallery { isPermission ->
        if (isPermission) {
            val nameFile = link.substringAfterLast("/")
            val request = DownloadManager.Request(Uri.parse(link))
                .setTitle(nameFile)
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                    File(TextApp.FOLDER_NAME, nameFile).absolutePath)
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadID = manager.enqueue(request)

            val query = DownloadManager.Query().setFilterById(downloadID)

            val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    //Fetching the download id received with the broadcast
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    logW("Download Completed $id")
                    //Checking if the received broadcast is for our enqueued download by matching download id
                    if (downloadID == id) {
                        logW("Download Completed $id")
                    }
                }
            }
            context.registerReceiver(onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }
}


@Composable
fun rememberDownloadManagerFromUrl(link: String): () -> Unit {
    val context = LocalContext.current
    return launchPermissionCameraAndGallery { isPermission ->
        if (isPermission) {
            createDownload(context, context.getSystemService(DownloadManager::class.java), link)
        }
    }
}


private fun createDownload(context: Context, downloadManager: DownloadManager, url: String) {
    val nameFile = url.substringAfterLast("/")
    val downloadRequest = DownloadManager.Request(Uri.parse(url))
        .setTitle(nameFile)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_PICTURES, File(TextApp.FOLDER_NAME, nameFile).absolutePath)
    val downloadId = downloadManager.enqueue(downloadRequest)
    val query = DownloadManager.Query().setFilterById(downloadId)
    val contentProviderObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?, flags: Int) {
            downloadManager.query(query).use { cursor ->
                val downloadBytesColumnIndex =
                    cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                val totalBytesColumnIndex =
                    cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                cursor.moveToFirst()
                val curr = cursor.getInt(downloadBytesColumnIndex)
                val total = cursor.getInt(totalBytesColumnIndex)
                if (total != -1) {
                    logV("createDownload", curr, total)
//                    TODO("Добавить обработку полученных данных")
                }
            }
        }
    }

    downloadManager.query(query).use { cursor ->
        if (cursor.moveToFirst()) {
            logV("createDownload cursor",
                cursor.columnNames.joinToString(separator = ", ") { it?.toString() ?: "null" })
            val index = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            logV("createDownload COLUMN_BYTES_DOWNLOADED_SO_FAR", "$index")

//            val localUri = cursor.getString(index)
//            if (!localUri.isNullOrEmpty()) {
////                context.contentResolver.registerContentObserver(Uri.parse(localUri),
////                    false,
////                    contentProviderObserver)
//            } else {
//                logV("createDownload localUri", "пустое или нулевое значение")
//            }
        } else {
            logV("createDownload localUri", "курсор пустой или нет данных")
        }
    }
}


fun patchFromPictures(fileName: String): File {
    val directory = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        TextApp.FOLDER_NAME)
    directory.mkdirs()
    return File(directory, fileName)
}

val collection: Uri =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

fun getFileMimeType(fileName: String): String? {
    val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileName)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
}


