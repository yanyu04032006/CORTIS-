package com.example.quiz.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.model.QuizResult
import com.example.quiz.ui.components.DoodleCross
import com.example.quiz.ui.components.DoodleLightning
import com.example.quiz.ui.components.DoodleStar
import com.example.quiz.ui.components.StickerTag

@Composable
fun ResultScreen(
    result: QuizResult,
    onRetest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val primary = result.primaryMember
    val runnerUp = result.runnerUpMember

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
                    .widthIn(max = 520.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 頂部揭曉標籤
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    DoodleStar(size = 22.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    StickerTag(
                        text = "答案揭曉 ✦",
                        backgroundColor = Color(0xFFFFD54F),
                        textColor = Color(0xFF1F1F24),
                        rotation = -2f
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DoodleLightning(size = 20.dp)
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 雙擔警告條（當第一名與第二名極度接近時展示）
                if (result.isDualBias) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, RoundedCornerShape(16.dp))
                            .background(Color(0xFFFFF3E0), RoundedCornerShape(16.dp))
                            .border(2.dp, Color(0xFFFF9800), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "雙擔愛心",
                                    tint = Color(0xFFFF5722),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "你有明顯的雙擔傾向！",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE65100)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "主擔：${primary.fullName}\n危險對象：${runnerUp.fullName}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF3E2723),
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "「你的心好像沒有打算只選一個。」",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFBF360C)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }

                // 主擔角色揭曉大卡片
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(24.dp))
                        .background(Color.White, RoundedCornerShape(24.dp))
                        .border(2.5.dp, Color(0xFF1F1F24), RoundedCornerShape(24.dp))
                        .padding(20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "你的 CORTIS 擔是——",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF75707C),
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 成員姓名大標題
                        Text(
                            text = primary.fullName,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = primary.themeColor,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // 成員核心稱號 Badge
                        Box(
                            modifier = Modifier
                                .background(primary.badgeColor, RoundedCornerShape(20.dp))
                                .border(1.5.dp, Color(0xFF1F1F24), RoundedCornerShape(20.dp))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = primary.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1F1F24),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // 代表金句
                        Text(
                            text = primary.quote,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF4A4652),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp,
                            modifier = Modifier
                                .background(Color(0xFFF9F7FA), RoundedCornerShape(10.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 粉絲視角原型深入解析文案
                        Text(
                            text = primary.resultSummary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F1F24),
                            lineHeight = 23.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = primary.detailedDescription,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF3E3C44),
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = primary.closingRemark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primary.themeColor,
                            lineHeight = 21.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 第二名（差一點被他拐走）卡片
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(18.dp))
                        .background(Color.White, RoundedCornerShape(18.dp))
                        .border(1.5.dp, Color(0xFF1F1F24), RoundedCornerShape(18.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "差一點就被他拐走：",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5B5763)
                            )
                            StickerTag(
                                text = "RUNNER UP",
                                backgroundColor = Color(0xFFE0F7FA),
                                textColor = Color(0xFF006064),
                                rotation = 2f
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = runnerUp.fullName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = runnerUp.themeColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "「${runnerUp.title}」\n${runnerUp.coreArchetype}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF4C4854),
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 個性化微觀心動洞察
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                        .background(Color(0xFFF3E5F5), RoundedCornerShape(16.dp))
                        .border(1.5.dp, Color(0xFFAB47BC), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        DoodleStar(size = 20.dp, color = Color(0xFFBA68C8))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "你的心動微雷達：",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6A1B9A)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = result.dynamicInsight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF4A148C),
                                lineHeight = 19.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // CORTIS 心動光譜 (不顯示百分比，只顯示橫向視覺條)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp))
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, Color(0xFF1F1F24), RoundedCornerShape(20.dp))
                        .padding(18.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DoodleLightning(size = 18.dp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "你的 CORTIS 心動光譜",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1F1F24)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        result.spectrumItems.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2C2B30),
                                    modifier = Modifier.width(55.dp)
                                )

                                Text(
                                    text = item.visualBar,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF2E63),
                                    fontFamily = FontFamily.Monospace,
                                    letterSpacing = 1.5.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 分享與複製按鈕
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // 分享按鈕
                    Button(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "《猜猜你的 CORTIS 擔是誰？》測驗結果")
                                putExtra(Intent.EXTRA_TEXT, result.shareText)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "分享我的 CORTIS 測驗結果"))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(3.dp, RoundedCornerShape(26.dp))
                            .testTag("share_result_button"),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF2E63),
                            contentColor = Color.White
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "分享結果", modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "分享我的結果", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // 複製結果按鈕
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("CORTIS Quiz Result", result.shareText)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "測驗結果已複製到剪貼簿！♡", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("copy_result_button"),
                        shape = RoundedCornerShape(26.dp),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1F1F24)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1F1F24)
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "複製文字", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "複製結果文字", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // 再測一次按鈕
                    OutlinedButton(
                        onClick = onRetest,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("retest_button"),
                        shape = RoundedCornerShape(26.dp),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF5D5962)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFF5F3F7),
                            contentColor = Color(0xFF1F1F24)
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "再測一次", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "再測一次", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Footer
                Text(
                    text = "＊純娛樂性質｜以粉絲視角設計的角色原型，不代表真人性格診斷。",
                    fontSize = 11.sp,
                    color = Color(0xFF75707C),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Fan-made interactive quiz  •  Made for fun ♡",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8C8694)
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
