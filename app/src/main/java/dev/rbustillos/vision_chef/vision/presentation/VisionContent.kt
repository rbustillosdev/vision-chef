package dev.rbustillos.vision_chef.vision.presentation

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import dev.rbustillos.vision_chef.R
import dev.rbustillos.vision_chef.core.common.result.ResultState
import dev.rbustillos.vision_chef.core.constants.BOTTOM_BAR_PADDING
import dev.rbustillos.vision_chef.core.constants.BUTTON_HEIGHT
import dev.rbustillos.vision_chef.core.constants.ICON_SIZE_L
import dev.rbustillos.vision_chef.core.constants.ICON_SIZE_XL
import dev.rbustillos.vision_chef.core.constants.PADDING
import dev.rbustillos.vision_chef.core.extension.createTempPictureUri
import dev.rbustillos.vision_chef.vision.domain.constants.ANY_IMAGE_TYPE
import dev.rbustillos.vision_chef.vision.presentation.composables.LoadingScreen
import dev.rbustillos.vision_chef.vision.presentation.composables.MainSwitch
import dev.rbustillos.vision_chef.vision.presentation.composables.PolicyDialog

@Composable
fun VisionContent(viewModel: VisionViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scrollState = rememberScrollState(0)

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.mainUri = uri
            viewModel.mainUri?.let {
                viewModel.getRecipeFromImage(
                    BitmapFactory.decodeStream(
                        context.contentResolver.openInputStream(it)
                    )
                )
            }
        }
    )

    // to take from camera
    val tempUri = context.createTempPictureUri()

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            viewModel.mainUri = tempUri
            viewModel.mainUri?.let {
                viewModel.getRecipeFromImage(
                    BitmapFactory.decodeStream(
                        context.contentResolver.openInputStream(it)
                    )
                )
            }
        }

    if (viewModel.state is ResultState.Error) {
        Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                MainSwitch(visionViewModel = viewModel)
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.mainUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = viewModel.mainUri),
                        contentDescription = "Selected image",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("No image selected", modifier = Modifier.align(Alignment.Center))
                }

                if ((viewModel.state is ResultState.Success) && (viewModel.isViewingResult)) {
                    val result = (viewModel.state as ResultState.Success<String>).data
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = BOTTOM_BAR_PADDING.dp)
                            .background(color = Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        SelectionContainer {
                            Text(
                                text = result,
                                color = Color.White,
                                modifier = Modifier
                                    .verticalScroll(scrollState)
                                    .padding(horizontal = PADDING.dp)
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(PADDING.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    contentAlignment = Alignment.TopStart
                )
                {
                    IconButton(
                        onClick = {
                            imagePicker.launch(ANY_IMAGE_TYPE)
                        },
                        modifier = Modifier
                            .width(BUTTON_HEIGHT.dp)
                            .height(BUTTON_HEIGHT.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_photo_library),
                            contentDescription = "Select a picture",
                            modifier = Modifier.size(ICON_SIZE_L.dp),
                            tint = Color.White
                        )
                    }
                }

                Button(
                    modifier = Modifier.requiredWidth(IntrinsicSize.Max),
                    onClick = { cameraLauncher.launch(tempUri) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_photo_camera),
                        contentDescription = "Take a photo",
                        modifier = Modifier.size(ICON_SIZE_XL.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.TopStart
                )
                {
                    IconButton(
                        onClick = {
                            viewModel.openAlertDialog = true
                        },
                        modifier = Modifier
                            .width(BUTTON_HEIGHT.dp)
                            .height(BUTTON_HEIGHT.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_policy),
                            contentDescription = "Select a picture",
                            modifier = Modifier.size(ICON_SIZE_L.dp),
                            tint = Color.White
                        )
                    }
                }

            }
        }

        if (viewModel.state is ResultState.Loading) {
            LoadingScreen()
        }

        PolicyDialog(viewModel = viewModel)
    }
}
