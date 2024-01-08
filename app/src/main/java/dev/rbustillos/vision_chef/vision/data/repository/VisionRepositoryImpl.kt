package dev.rbustillos.vision_chef.vision.data.repository

import android.graphics.Bitmap
import dev.rbustillos.vision_chef.vision.data.remote.VisionDataSource
import dev.rbustillos.vision_chef.vision.domain.repository.VisionRepository
import javax.inject.Inject

class VisionRepositoryImpl @Inject constructor(
    private val visionDataSource: VisionDataSource
) : VisionRepository {
    override suspend fun getRecipeFromImage(bitmap: Bitmap): String {
        val newBitmap = Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * 0.25).toInt(),
            (bitmap.height * 0.25).toInt(),
            false
        )
        return visionDataSource.getRecipeFromImage(newBitmap)
    }
}