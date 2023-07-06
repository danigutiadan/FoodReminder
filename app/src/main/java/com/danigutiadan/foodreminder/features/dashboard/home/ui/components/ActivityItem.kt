package com.danigutiadan.foodreminder.features.dashboard.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danigutiadan.foodreminder.R

@Composable
fun ActivityItem(title: String, index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black,
                        Color.Black,
                        Color.Black
                    ),
                    startY = 300F,
                    endY = Float.POSITIVE_INFINITY,
                )
            )
    ) {
        Image(
            painter = painterResource(id = if (index % 2 == 0) R.drawable.beach_sample else R.drawable.teide),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alpha = 0.75F
        )

        Text(
            text = title,
            style = TextStyle(fontSize = 18.sp),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 10.dp, bottom = 10.dp)
        )

    }

}

private fun ContentDrawScope.drawExternalShadow() {
    val shadowColor = Color.Black.copy(alpha = 0.2f)
    val shadowElevation = 8.dp

    drawRect(
        color = shadowColor,
        topLeft = Offset(-shadowElevation.toPx(), -shadowElevation.toPx()),
        size = Size(size.width + (shadowElevation * 2).toPx(), size.height + (shadowElevation * 2).toPx())
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun PreviewActivityItem() {
    Scaffold(
        content = {
            ActivityItem(title = "Playa de Formentera", index = 2)
        }
    )
}