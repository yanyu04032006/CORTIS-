package com.example.quiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.quiz.ui.components.DoodleCross
import com.example.quiz.ui.components.DoodleLightning
import com.example.quiz.ui.components.DoodleStar
import com.example.quiz.ui.components.StickerTag

@Composable
fun CoverScreen(
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFFEEF3) // 溫柔浪漫淡粉色背景
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 480.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 頂部裝飾區
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DoodleStar(size = 22.dp)
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        StickerTag(
                            text = "CORTIS STAGE QUIZ ★",
                            backgroundColor = Color(0xFFFFD54F),
                            rotation = -1.5f
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        DoodleLightning(size = 20.dp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // 主標題
                    Text(
                        text = "《猜猜你的 CORTIS 擔是誰？》",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1F1F24),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 副標題
                    Text(
                        text = "「10 個問題，看看舞台上發光的他們誰會走進你心裡。」",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF5D5962),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // CORTIS 舞台可愛插畫卡片 (造型修正版：淺藍牛仔褲橘棕髮、斜戴帽金髮、黑短髮反戴帽黑背心棉褲、GIANT銀鍊)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .border(2.dp, Color(0xFF1F1F24), RoundedCornerShape(20.dp))
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_cortis_stage_revised),
                            contentDescription = "CORTIS 五位成員修正版舞台穿搭插畫",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 10f)
                                .clip(RoundedCornerShape(14.dp)),
                            contentScale = ContentScale.Crop
                        )

                        // 浮貼小角標
                        StickerTag(
                            text = "LIVE ON STAGE ♡",
                            backgroundColor = Color(0xFFE1BEE7),
                            textColor = Color(0xFF311B92),
                            rotation = 3f,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 8.dp, bottom = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 首頁心動標語 (Color outside the lines)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DoodleCross(size = 14.dp, color = Color(0xFFFF4081))
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = "✨ Color outside the lines ✨",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE91E63),
                            letterSpacing = 0.6.sp
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        DoodleCross(size = 14.dp, color = Color(0xFF2979FF))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 操作按鈕與宣告
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 明顯的【開始測驗 →】按鈕
                    Button(
                        onClick = onStartQuiz,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .shadow(6.dp, RoundedCornerShape(30.dp))
                            .testTag("start_quiz_button"),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF2E63), // 鮮明活力熱粉紅
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "開始測驗",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "前進箭頭",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 宣告小字
                    Text(
                        text = "＊純娛樂性質｜以粉絲視角設計的角色原型，不代表真人性格診斷。",
                        fontSize = 11.sp,
                        color = Color(0xFF75707C),
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Footer
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Fan-made interactive quiz  •  Made for fun ♡",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF8C8694)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
