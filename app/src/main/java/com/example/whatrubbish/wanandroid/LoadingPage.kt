package com.example.whatrubbish.wanandroid

//import android.content.Context
//import android.content.Context
import android.content.Context
//import android.util.Size
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import com.example.whatrubbish.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
//import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.Purple700

@Composable
fun LoadingPage(context: Context = LocalContext.current) {
//    Context
//    LocalContext l
    val animate by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(500, easing = LinearEasing), RepeatMode.Restart)
    )
//    context.
    val radius = context.dp2px(80f)
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(size.width / 2 - radius, size.height / 2-radius) {
            drawArc(
                Purple700,
                0f,
                animate,
                false,
                size = Size(radius*2f, radius*2f),
                style = Stroke(context.dp2px(4f)),
                alpha = 0.6f
            )
        }
    }
}
