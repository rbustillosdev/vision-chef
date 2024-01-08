package dev.rbustillos.vision_chef.vision.domain.useCase

import android.graphics.Bitmap
import dev.rbustillos.vision_chef.vision.domain.repository.VisionRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(private val repository: VisionRepository) {
    suspend operator fun invoke(bitmap: Bitmap): String = with(Dispatchers.IO) {
        repository.getRecipeFromImage(bitmap)
    }
}