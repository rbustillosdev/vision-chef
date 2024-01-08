package dev.rbustillos.vision_chef.vision.domain.repository

import android.graphics.Bitmap

interface VisionRepository {
    suspend fun getRecipeFromImage(bitmap: Bitmap): String
}