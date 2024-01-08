package dev.rbustillos.vision_chef.core.extension

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dev.rbustillos.vision_chef.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun Context.createTempPictureUri(
    provider: String = "${BuildConfig.APPLICATION_ID}.provider",
    fileName: String = "picture_${System.currentTimeMillis()}",
    fileExtension: String = ".png"
): Uri {
    val tempFile = File.createTempFile(
        fileName, fileExtension, cacheDir
    ).apply {
        createNewFile()
    }

    return FileProvider.getUriForFile(applicationContext, provider, tempFile)
}

