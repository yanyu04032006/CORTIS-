package com.example.quiz.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.ui.components.DoodleStar
import com.example.quiz.ui.components.StickerTag
import kotlinx.coroutines.delay

@Composable
fun RadarLoadingScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        delay(1800) // 1.8 秒揭曉動畫
        onComplete()
    }

    val transition = rememberInfiniteTransition(label = "radarTransition")

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarRotation"
    )

    val pulseScale by transition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFFEEF3) // 溫柔浪漫淡粉色背景
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StickerTag(
                text = "CORTIS HEART RADAR ✦",
                backgroundColor = Color(0xFFFFD54F),
                rotation = -2f
            )

            Spacer(modifier = Modifier.height(28.dp))

            // 雷達掃描視覺
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White, CircleShape)
                    .border(3.dp, Color(0xFF1F1F24), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // 背景雷達同心圓
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val cx = size.width / 2f
                    val cy = size.height / 2f
                    drawCircle(Color(0xFFE8E5EC), radius = size.width * 0.4f, style = Stroke(2f))
                    drawCircle(Color(0xFFE8E5EC), radius = size.width * 0.25f, style = Stroke(2f))
                    drawCircle(Color(0xFFE8E5EC), radius = size.width * 0.12f, style = Stroke(2f))

                    // 十字準心
                    drawLine(Color(0xFFE0DBE7), Offset(cx, 0f), Offset(cx, size.height), strokeWidth = 1.5f)
                    drawLine(Color(0xFFE0DBE7), Offset(0f, cy), Offset(size.width, cy), strokeWidth = 1.5f)
                }

                // 旋轉掃描針
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotation)
                ) {
                    val cx = size.width / 2f
                    val cy = size.height / 2f
                    drawLine(
                        color = Color(0xFFFF2E63),
                        start = Offset(cx, cy),
                        end = Offset(cx, 10f),
                        strokeWidth = 3f
                    )
                }

                // 核心心動標記
                Box(
                    modifier = Modifier
                        .size((34 * pulseScale).dp)
                        .background(Color(0xFFFF2E63), CircleShape)
                        .border(2.dp, Color(0xFF1F1F24), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    DoodleStar(size = 18.dp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "正在讀取你的心動雷達……",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1F1F24),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "正在交叉比對 10 題偏好與 6 大隱藏維度",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6F6A78),
                textAlign = TextAlign.Center
            )
        }
    }
}
