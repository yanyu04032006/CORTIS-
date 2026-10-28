package com.example.quiz.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 塗鴉小星星
@Composable
fun DoodleStar(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFFD600),
    strokeColor: Color = Color(0xFF1F1F24),
    size: Dp = 24.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val cx = w / 2f
        val cy = h / 2f

        val path = Path().apply {
            moveTo(cx, 0f)
            quadraticTo(cx, cy, w, cy)
            quadraticTo(cx, cy, cx, h)
            quadraticTo(cx, cy, 0f, cy)
            quadraticTo(cx, cy, cx, 0f)
            close()
        }
        drawPath(path, color = color)
        drawPath(path, color = strokeColor, style = Stroke(width = 3.5f))
    }
}

// 街頭風小閃電
@Composable
fun DoodleLightning(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFFEB3B),
    strokeColor: Color = Color(0xFF1F1F24),
    size: Dp = 20.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            moveTo(w * 0.6f, 0f)
            lineTo(w * 0.15f, h * 0.55f)
            lineTo(w * 0.5f, h * 0.55f)
            lineTo(w * 0.35f, h)
            lineTo(w * 0.85f, h * 0.4f)
            lineTo(w * 0.5f, h * 0.4f)
            close()
        }
        drawPath(path, color = color)
        drawPath(path, color = strokeColor, style = Stroke(width = 3f))
    }
}

// 紙膠帶／手繪標籤貼紙
@Composable
fun StickerTag(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFFFF9C4),
    textColor: Color = Color(0xFF1F1F24),
    rotation: Float = -2f
) {
    Box(
        modifier = modifier
            .rotate(rotation)
            .shadow(2.dp, RoundedCornerShape(6.dp))
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .border(1.5.dp, Color(0xFF1F1F24), RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}

// 塗鴉小十字符號
@Composable
fun DoodleCross(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF4081),
    size: Dp = 16.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val s = this.size.width
        drawLine(
            color = color,
            start = Offset(s * 0.2f, s * 0.5f),
            end = Offset(s * 0.8f, s * 0.5f),
            strokeWidth = 4f
        )
        drawLine(
            color = color,
            start = Offset(s * 0.5f, s * 0.2f),
            end = Offset(s * 0.5f, s * 0.8f),
            strokeWidth = 4f
        )
    }
}
