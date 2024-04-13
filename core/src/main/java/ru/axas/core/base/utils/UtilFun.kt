package ru.axas.core.base.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

fun Context.getFileRealPatch(documentUri: Uri): File {
    val inputStream = contentResolver?.openInputStream(documentUri)
//    val tika = Tika()
    val file: File
    inputStream.use { input ->
        val extension =  getExtensionFromUri(this,documentUri)
        file = File(cacheDir, System.currentTimeMillis().toString() + ".${extension ?: "jpeg"}")
        FileOutputStream(file).use { output ->
            val buffer = ByteArray(4 * 1024)
            var read: Int = -1
            while (input?.read(buffer).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
    }

    inputStream?.close()
    return file
}

fun getExtensionFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst()) {
            val fileName = cursor.getString(nameIndex)
            val dotIndex = fileName.lastIndexOf('.')
            if (dotIndex >= 0) {
                cursor.close()
                return fileName.substring(dotIndex + 1)
            }
        }
    }
    cursor?.close()
    return null
}
