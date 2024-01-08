package dev.rbustillos.vision_chef.vision.data.remote

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dev.rbustillos.vision_chef.core.constants.VISION_FOOD_PROMPT
import javax.inject.Inject

class VisionDataSource @Inject constructor(private val generativeModel: GenerativeModel){
    suspend fun getRecipeFromImage(bitmap: Bitmap): String {
        val requestContent = content {
            image(bitmap)
            text(VISION_FOOD_PROMPT)
        }
        val response = generativeModel.generateContent(requestContent)
        return response.text!!
    }
}