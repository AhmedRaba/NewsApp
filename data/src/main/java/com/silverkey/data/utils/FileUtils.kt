package com.silverkey.data.utils


import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    suspend fun downloadImageAndSaveToInternalStorage(
        context: Context,
        imageUrl: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(imageUrl).build()
            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            val inputStream = response.body.byteStream()

            val fileName = "article_image_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)
            outputStream.close()
            inputStream.close()

            file.absolutePath
        } catch (e: Exception) {
            null
        }
    }
}
