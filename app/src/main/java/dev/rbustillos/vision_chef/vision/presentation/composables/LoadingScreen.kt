package dev.rbustillos.vision_chef.vision.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rbustillos.vision_chef.core.constants.CIRCULAR_PROGRESS_SIZE
import dev.rbustillos.vision_chef.core.constants.CIRCULAR_PROGRESS_STROKE
import dev.rbustillos.vision_chef.core.constants.PADDING

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = {})
            .background(color = Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(CIRCULAR_PROGRESS_SIZE.dp)
                    .padding(PADDING.dp),
                strokeWidth = CIRCULAR_PROGRESS_STROKE.dp,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = PADDING.dp)
                    .background(color = Color.Gray.copy(alpha = 0.75f), shape = CircleShape)
            ) {
                Text(
                    "Hold on, lemme see what we can do...",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(PADDING.dp)
                )
            }

        }
    }
}