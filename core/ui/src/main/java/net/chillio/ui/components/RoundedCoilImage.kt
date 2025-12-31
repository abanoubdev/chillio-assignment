package net.chillio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest

@Composable
fun RoundedCoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    contentDescription: String? = imageUrl,
    cornerRadius: Dp = 16.dp,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color = Color(0xFF1E1E26),
    errorColor: Color = Color(0xFF3A3A46),
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.clip(RoundedCornerShape(cornerRadius)),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(placeholderColor)
            )
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(errorColor)
            )
        },
        success = {
            SubcomposeAsyncImageContent()
        }
    )
}

@Composable
private fun RoundedLocalImageForPreview(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp
) {
    Image(
        painter = painterResource(id = android.R.drawable.ic_dialog_email),
        contentDescription = "Preview",
        modifier = modifier.clip(RoundedCornerShape(cornerRadius)),
        contentScale = ContentScale.Crop
    )
}

@Preview(name = "Rounded - Light", showBackground = true)
@Composable
private fun PreviewRoundedImageLight() {
    RoundedLocalImageForPreview(
        modifier = Modifier
            .size(180.dp)
            .aspectRatio(16f / 9f),
        cornerRadius = 20.dp
    )
}

@Preview(name = "Rounded - Dark", showBackground = true, backgroundColor = 0xFF0B0B0E)
@Composable
private fun PreviewRoundedImageDark() {
    RoundedLocalImageForPreview(
        modifier = Modifier
            .size(180.dp)
            .aspectRatio(16f / 9f),
        cornerRadius = 20.dp
    )
}