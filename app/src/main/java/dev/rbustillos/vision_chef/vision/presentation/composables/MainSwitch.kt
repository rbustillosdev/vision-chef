package dev.rbustillos.vision_chef.vision.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.rbustillos.vision_chef.core.constants.PADDING
import dev.rbustillos.vision_chef.core.constants.SWITCH_ITEM_HEIGHT
import dev.rbustillos.vision_chef.core.constants.SWITCH_ITEM_WIDTH
import dev.rbustillos.vision_chef.vision.presentation.VisionViewModel

@Composable
fun MainSwitch(visionViewModel: VisionViewModel) {
    val context = LocalContext.current

    val normalBackground = Modifier
    val selectedBackground =
        Modifier.background(MaterialTheme.colorScheme.primary, shape = CircleShape)

    Row(
        Modifier
            .padding(PADDING.dp)
            .background(Color.LightGray, shape = CircleShape)) {
        Box(
            modifier = (if (visionViewModel.isViewingResult) {
                normalBackground
            } else {
                selectedBackground
            }).withDimens(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Image",
                modifier = Modifier
                    .clickable(onClick = {
                        visionViewModel.isViewingResult = false
                    }),
                color = Color.White
            )
        }
        Spacer(Modifier.width(PADDING.dp))
        Box(
            modifier = (if (visionViewModel.isViewingResult) {
                selectedBackground
            } else {
                normalBackground
            }).withDimens(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Result",
                modifier = Modifier
                    .padding(PADDING.dp)
                    .clickable(onClick = {
                        if (visionViewModel.state != null) {
                            visionViewModel.isViewingResult = true
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "You need to select an image first",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }),
                color = Color.White
            )
        }
    }
}

private fun Modifier.withDimens(): Modifier = width(SWITCH_ITEM_WIDTH.dp).height(SWITCH_ITEM_HEIGHT.dp)