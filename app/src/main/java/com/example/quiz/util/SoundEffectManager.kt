package com.example.quiz.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.SoundPool
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * 夢幻 Bling-Bling 戀愛心動音效管理器 (Sweet Romantic Bling-Bling Audio)
 *
 * 營造如少女漫畫般冒出粉紅泡泡、閃閃發光 (Bling-Bling) 的微甜心動氛圍：
 * 1. playOptionClickSound(): 戀愛粉紅泡泡 Bling-Bling 雙重閃爍音 (Sweet Bling Sparkle)
 *    - 輕巧甜美的高八度雙星閃爍 (B5 987Hz -> E6 1318Hz -> B6 1975Hz)，輕柔不刺耳，帶有心跳漏一拍的閃爍顫音
 * 2. playActionSound(): 甜蜜魔法心動 chime (Romantic Heart Chime)
 * 3. playResultCelebrationSound(): 戀愛微風粉紅花瓣與星光交織琶音 (Romantic Celebration Shimmer)
 */
object SoundEffectManager {
    private const val TAG = "SoundEffectManager"
    private const val SAMPLE_RATE = 44100

    private var soundPool: SoundPool? = null
    private var blingSoundId: Int = 0
    private var actionSoundId: Int = 0
    private var celebrationSoundId: Int = 0

    private val loadedSoundIds = mutableSetOf<Int>()
    private val executor = Executors.newSingleThreadExecutor()

    private var blingPcmCache: ShortArray? = null
    private var actionPcmCache: ShortArray? = null
    private var celebrationPcmCache: ShortArray? = null

    fun init(context: Context) {
        executor.execute {
            try {
                // 1. 生成戀愛 Bling-Bling 心動 PCM
                val blingPcm = generateRomanticBlingPcm()
                val actionPcm = generateRomanticActionPcm()
                val celebrationPcm = generateRomanticCelebrationPcm()

                blingPcmCache = blingPcm
                actionPcmCache = actionPcm
                celebrationPcmCache = celebrationPcm

                val blingWav = createWavBytes(blingPcm, SAMPLE_RATE)
                val actionWav = createWavBytes(actionPcm, SAMPLE_RATE)
                val celebrationWav = createWavBytes(celebrationPcm, SAMPLE_RATE)

                val cacheDir = context.cacheDir
                val fileBling = File(cacheDir, "romantic_bling.wav")
                val fileAction = File(cacheDir, "romantic_action.wav")
                val fileCelebration = File(cacheDir, "romantic_celebration.wav")

                FileOutputStream(fileBling).use { it.write(blingWav) }
                FileOutputStream(fileAction).use { it.write(actionWav) }
                FileOutputStream(fileCelebration).use { it.write(celebrationWav) }

                val pool = SoundPool.Builder()
                    .setMaxStreams(8)
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .build()

                pool.setOnLoadCompleteListener { _, sampleId, status ->
                    if (status == 0) {
                        synchronized(loadedSoundIds) {
                            loadedSoundIds.add(sampleId)
                        }
                    }
                }

                blingSoundId = pool.load(fileBling.absolutePath, 1)
                actionSoundId = pool.load(fileAction.absolutePath, 1)
                celebrationSoundId = pool.load(fileCelebration.absolutePath, 1)

                soundPool = pool
            } catch (e: Exception) {
                Log.e(TAG, "SoundEffectManager initialization failed", e)
            }
        }
    }

    /**
     * 點擊選項時的戀愛 Bling-Bling 心動音 (Sweet Romantic Sparkle)
     */
    fun playOptionClickSound() {
        val pool = soundPool
        val id = blingSoundId
        val isLoaded = synchronized(loadedSoundIds) { loadedSoundIds.contains(id) }

        if (pool != null && id != 0 && isLoaded) {
            pool.play(id, 0.45f, 0.45f, 1, 0, 1.0f)
        } else {
            playPcmDirectly(blingPcmCache ?: generateRomanticBlingPcm(), 0.45f)
        }
    }

    /**
     * 點擊確認或推進題目時的戀愛心動 Chime
     */
    fun playActionSound() {
        val pool = soundPool
        val id = actionSoundId
        val isLoaded = synchronized(loadedSoundIds) { loadedSoundIds.contains(id) }

        if (pool != null && id != 0 && isLoaded) {
            pool.play(id, 0.48f, 0.48f, 1, 0, 1.0f)
        } else {
            playPcmDirectly(actionPcmCache ?: generateRomanticActionPcm(), 0.48f)
        }
    }

    /**
     * 到達結果頁時的夢幻浪漫琶音
     */
    fun playResultCelebrationSound() {
        val pool = soundPool
        val id = celebrationSoundId
        val isLoaded = synchronized(loadedSoundIds) { loadedSoundIds.contains(id) }

        if (pool != null && id != 0 && isLoaded) {
            pool.play(id, 0.52f, 0.52f, 1, 0, 1.0f)
        } else {
            playPcmDirectly(celebrationPcmCache ?: generateRomanticCelebrationPcm(), 0.52f)
        }
    }

    // ====== Bling-Bling 戀愛聲音合成演算法 ======

    /**
     * 輕柔戀愛 Bling-Bling 雙重星芒閃爍音 (時長約 280ms)
     * - 第一顆 Bling (1174Hz, D6): 溫柔輕靈的微光
     * - 第二顆 Bling (1760Hz, A6): 緊接著泛起甜甜微光，伴隨 14Hz 輕柔晶瑩顫音
     * - 正弦柔和漸入與平滑衰減，清甜夢幻，滿滿心動戀愛氛圍
     */
    private fun generateRomanticBlingPcm(): ShortArray {
        val durationMs = 280
        val totalSamples = (SAMPLE_RATE * durationMs / 1000)
        val pcm = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE

            // 第一道心動閃光 (Bling 1: D6 1174.66Hz)
            val bling1 = if (t < 0.16) {
                val attack = if (t < 0.015) sin((t / 0.015) * (PI / 2.0)) else 1.0
                val decay = exp(-t * 14.0)
                0.35 * attack * decay * sin(2.0 * PI * 1174.66 * t)
            } else 0.0

            // 第二道心動閃光 (Bling 2: A6 1760Hz + 微閃顫音)
            val bling2Start = 0.045
            val bling2 = if (t >= bling2Start) {
                val bt = t - bling2Start
                val attack = if (bt < 0.018) sin((bt / 0.018) * (PI / 2.0)) else 1.0
                val decay = exp(-bt * 11.5)
                // 14Hz 戀愛心動泡泡輕顫
                val shimmer = 1.0 + 0.12 * sin(2.0 * PI * 14.0 * bt)
                0.42 * attack * decay * shimmer * sin(2.0 * PI * 1760.0 * bt)
            } else 0.0

            val sample = (bling1 + bling2).coerceIn(-1.0, 1.0)
            pcm[i] = (sample * 16000).toInt().toShort()
        }
        return pcm
    }

    /**
     * 甜蜜心動 Action 音 (時長約 320ms)
     */
    private fun generateRomanticActionPcm(): ShortArray {
        val durationMs = 320
        val totalSamples = (SAMPLE_RATE * durationMs / 1000)
        val pcm = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE

            // 溫暖主音 (G5 783.99Hz)
            val note1 = if (t < 0.18) {
                val attack = if (t < 0.018) sin((t / 0.018) * (PI / 2.0)) else 1.0
                val decay = exp(-t * 12.0)
                0.32 * attack * decay * sin(2.0 * PI * 783.99 * t)
            } else 0.0

            // 甜甜星光 (E6 1318.51Hz)
            val note2Start = 0.065
            val note2 = if (t >= note2Start) {
                val bt = t - note2Start
                val attack = if (bt < 0.02) sin((bt / 0.02) * (PI / 2.0)) else 1.0
                val decay = exp(-bt * 9.5)
                0.40 * attack * decay * sin(2.0 * PI * 1318.51 * bt)
            } else 0.0

            val sample = (note1 + note2).coerceIn(-1.0, 1.0)
            pcm[i] = (sample * 16000).toInt().toShort()
        }
        return pcm
    }

    /**
     * 浪漫心動琶音 (時長 680ms)
     */
    private fun generateRomanticCelebrationPcm(): ShortArray {
        val durationMs = 680
        val totalSamples = (SAMPLE_RATE * durationMs / 1000)
        val pcm = ShortArray(totalSamples)

        // 溫馨甜蜜大七和弦 (F#5, A5, C#6, E6, A6)
        val notes = listOf(739.99, 880.0, 1108.73, 1318.51, 1760.0)
        val noteSpacing = 0.085

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            var mix = 0.0

            for ((idx, freq) in notes.withIndex()) {
                val noteStart = idx * noteSpacing
                if (t >= noteStart) {
                    val nt = t - noteStart
                    val attack = if (nt < 0.02) sin((nt / 0.02) * (PI / 2.0)) else 1.0
                    val decay = exp(-nt * 6.5)
                    val shimmer = 1.0 + 0.08 * sin(2.0 * PI * 12.0 * nt)
                    val sparkle = 0.32 * attack * decay * shimmer * sin(2.0 * PI * freq * nt)
                    mix += sparkle
                }
            }

            val sample = mix.coerceIn(-1.0, 1.0)
            pcm[i] = (sample * 16000).toInt().toShort()
        }
        return pcm
    }

    private fun playPcmDirectly(pcm: ShortArray, volumeScale: Float = 0.45f) {
        executor.execute {
            try {
                val scaledPcm = ShortArray(pcm.size) { idx ->
                    (pcm[idx] * volumeScale).toInt().toShort()
                }

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(SAMPLE_RATE)
                            .build()
                    )
                    .setBufferSizeInBytes(scaledPcm.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack.write(scaledPcm, 0, scaledPcm.size)
                audioTrack.play()
                val sleepTimeMs = (scaledPcm.size.toDouble() / SAMPLE_RATE * 1000).toLong() + 30
                Thread.sleep(sleepTimeMs)
                audioTrack.stop()
                audioTrack.release()
            } catch (e: Exception) {
                Log.w(TAG, "playPcmDirectly interrupted or failed", e)
            }
        }
    }

    private fun createWavBytes(pcmData: ShortArray, sampleRate: Int): ByteArray {
        val totalAudioLen = pcmData.size * 2
        val totalDataLen = totalAudioLen + 36
        val byteRate = sampleRate * 2

        val header = ByteArray(44)
        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()
        header[4] = (totalDataLen and 0xff).toByte()
        header[5] = ((totalDataLen shr 8) and 0xff).toByte()
        header[6] = ((totalDataLen shr 16) and 0xff).toByte()
        header[7] = ((totalDataLen shr 24) and 0xff).toByte()
        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()
        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()
        header[16] = 16
        header[17] = 0
        header[18] = 0
        header[19] = 0
        header[20] = 1
        header[21] = 0
        header[22] = 1
        header[23] = 0
        header[24] = (sampleRate and 0xff).toByte()
        header[25] = ((sampleRate shr 8) and 0xff).toByte()
        header[26] = ((sampleRate shr 16) and 0xff).toByte()
        header[27] = ((sampleRate shr 24) and 0xff).toByte()
        header[28] = (byteRate and 0xff).toByte()
        header[29] = ((byteRate shr 8) and 0xff).toByte()
        header[30] = ((byteRate shr 16) and 0xff).toByte()
        header[31] = ((byteRate shr 24) and 0xff).toByte()
        header[32] = 2
        header[33] = 0
        header[34] = 16
        header[35] = 0
        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()
        header[40] = (totalAudioLen and 0xff).toByte()
        header[41] = ((totalAudioLen shr 8) and 0xff).toByte()
        header[42] = ((totalAudioLen shr 16) and 0xff).toByte()
        header[43] = ((totalAudioLen shr 24) and 0xff).toByte()

        val wavBytes = ByteArray(44 + totalAudioLen)
        System.arraycopy(header, 0, wavBytes, 0, 44)
        for (i in pcmData.indices) {
            val sample = pcmData[i]
            wavBytes[44 + i * 2] = (sample.toInt() and 0xff).toByte()
            wavBytes[44 + i * 2 + 1] = ((sample.toInt() shr 8) and 0xff).toByte()
        }
        return wavBytes
    }
}
