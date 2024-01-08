package dev.rbustillos.vision_chef.vision.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rbustillos.vision_chef.core.common.result.ResultState
import dev.rbustillos.vision_chef.vision.domain.useCase.GetRecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisionViewModel @Inject constructor(private val getRecipeUseCase: GetRecipeUseCase) :
    ViewModel() {

    // to show privacy policy dialog
    var openAlertDialog by mutableStateOf(false)

    // to control switch
    var isViewingResult by mutableStateOf(false)

    // take from gallery
    var mainUri by mutableStateOf<Uri?>(null)

    var state by mutableStateOf<ResultState<String>?>(null)

    fun getRecipeFromImage(bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                state = ResultState.Loading
                state = ResultState.Success(getRecipeUseCase(bitmap))
                isViewingResult = true
            } catch (e: Exception) {
                state = ResultState.Error(e)
            }
        }
    }
}

