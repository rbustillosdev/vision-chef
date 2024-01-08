package dev.rbustillos.vision_chef.vision.presentation.composables

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import dev.rbustillos.vision_chef.core.constants.PRIVACY_POLICY
import dev.rbustillos.vision_chef.vision.presentation.VisionViewModel

@Composable
fun PolicyDialog(viewModel: VisionViewModel) {
    val uriHandler = LocalUriHandler.current
    val privacyPolicyString = buildAnnotatedString {
        append("None of your images are stored on any server or in the app's memory. But we recommend you to read our ")

        pushStringAnnotation(tag = "data processing policy and privacy policy", annotation = PRIVACY_POLICY)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append("data processing policy and privacy policy")
        }
        pop()
    }
    if (viewModel.openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "About privacy")
            },
            text = {
                ClickableText(text = privacyPolicyString, onClick = { offset ->
                    privacyPolicyString.getStringAnnotations(tag = "data processing policy and privacy policy", start = offset, end = offset).firstOrNull()?.let {
                        uriHandler.openUri(PRIVACY_POLICY)
                    }
                })
            },
            onDismissRequest = {
                viewModel.openAlertDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.openAlertDialog = false
                    }
                ) {
                    Text("Got it!")
                }
            }
        )
    }

}