package com.example.quiz.model

data class SpectrumItem(
    val label: String,
    val score: Int,
    val maxScore: Int = 50,
    val visualBar: String
)

data class QuizResult(
    val primaryMember: CortisMember,
    val runnerUpMember: CortisMember,
    val isDualBias: Boolean,
    val scoreDifference: Int,
    val dynamicInsight: String,
    val spectrumItems: List<SpectrumItem>,
    val topDimensionName: String,
    val shareText: String
)

object QuizCalculator {

    fun calculateResult(selectedAnswers: Map<Int, QuizOption>): QuizResult {
        var totalWeights = MemberWeights(0, 0, 0, 0, 0)
        var totalDimensions = HiddenDimensions()

        selectedAnswers.values.forEach { option ->
            totalWeights = totalWeights + option.weights
            totalDimensions = totalDimensions + option.dimensions
        }

        // 排序成員得分
        val memberScores = CortisMember.values().map { member ->
            member to totalWeights.getScore(member)
        }.sortedByDescending { it.second }

        val firstEntry = memberScores[0]
        val secondEntry = memberScores[1]

        val primaryMember = firstEntry.first
        val runnerUpMember = secondEntry.first
        val scoreDiff = firstEntry.second - secondEntry.second

        // 雙擔判定門檻：分數差距 <= 1 分（極度接近或平手）時觸發
        val isDualBias = scoreDiff <= 1

        // 計算各維度數值並生成視覺化光譜棒 (例如 ━━━━━━━●━━)
        val dimensionValues = listOf(
            "可靠感" to totalDimensions.reliability,
            "溫柔感" to totalDimensions.gentleCare,
            "反差感" to totalDimensions.gapMoe,
            "默契感" to totalDimensions.resonance,
            "神秘感" to totalDimensions.mystery,
            "玩心" to totalDimensions.playfulness
        )

        val maxDimVal = dimensionValues.maxOfOrNull { it.second }?.coerceAtLeast(1) ?: 1
        val topDimension = dimensionValues.maxByOrNull { it.second }?.first ?: "可靠感"

        val spectrumItems = dimensionValues.map { (name, value) ->
            val ratio = (value.toFloat() / maxDimVal).coerceIn(0.1f, 1.0f)
            val barLength = 10
            val dotPosition = ((barLength - 1) * ratio).toInt().coerceIn(0, barLength - 1)
            val barBuilder = StringBuilder()
            for (i in 0 until barLength) {
                if (i == dotPosition) {
                    barBuilder.append("●")
                } else {
                    barBuilder.append("━")
                }
            }
            SpectrumItem(
                label = name,
                score = value,
                maxScore = maxDimVal,
                visualBar = barBuilder.toString()
            )
        }

        // 動態生成結合主擔與最高維度的深度解析文案，避免每次結果千篇一律
        val dynamicInsight = generateDynamicInsight(primaryMember, runnerUpMember, topDimension, isDualBias)

        // 分享文字格式
        val dualBiasText = if (isDualBias) "（雙擔傾向！危險對象：${runnerUpMember.fullName}）" else "差一點被 ${runnerUpMember.fullName} 拐走。"
        val shareText = """
《猜猜你的 CORTIS 擔是誰？》
我的 CORTIS 擔是：${primaryMember.fullName}！
$dualBiasText
我的心動關鍵是：${primaryMember.title}
心動光譜最顯著特質：$topDimension
        """.trimIndent()

        return QuizResult(
            primaryMember = primaryMember,
            runnerUpMember = runnerUpMember,
            isDualBias = isDualBias,
            scoreDifference = scoreDiff,
            dynamicInsight = dynamicInsight,
            spectrumItems = spectrumItems,
            topDimensionName = topDimension,
            shareText = shareText
        )
    }

    private fun generateDynamicInsight(
        primary: CortisMember,
        runnerUp: CortisMember,
        topDimension: String,
        isDualBias: Boolean
    ): String {
        if (isDualBias) {
            return "你的心動雷達正在兩端劇烈擺動——既放不下 ${primary.englishName} 的${primary.title}，又時常被 ${runnerUp.englishName} 的特質牽著走。你的心好像沒有打算只選一個！"
        }

        return when (primary) {
            CortisMember.JAMES -> when (topDimension) {
                "神秘感" -> "你明明最需要踏實可靠的安全感，卻偏偏容易被他身上那一抹摸不透的獨特距離感勾住。"
                "反差感" -> "平常看起來最鬧、最放得開，可一旦碰上你在意的事，他認真扛起一切的專注神情最令你心動。"
                "玩心" -> "你們能在一起像孩子一樣瘋狂大笑，但你心裡最篤定的，是他在關鍵時刻永遠會替你擋在最前面的可靠。"
                else -> "你喜歡的從來不是空泛的承諾，而是那種『不用你開口，他已經默默處理好一切』的絕對擔當。"
            }
            CortisMember.JUHOON -> when (topDimension) {
                "反差感" -> "原本以為他是個與世無爭的安靜慢熟者，結果在他不經意展現偷偷皮的瞬間，你徹底被擊中了。"
                "神秘感" -> "他深邃且豐富的個人世界就像一座未被完全探索的島嶼，每一次發現他的新想法都讓你心跳加速。"
                "默契感" -> "比起言語上的熱鬧，你更沉迷於他安靜注視後說出的那句：『我其實一直都有注意到。』"
                else -> "你不需要轟轟烈烈的喧囂，唯有那種潤物細無聲、懂得尊重彼此邊界的溫柔觀察，最能走進你心裡。"
            }
            CortisMember.MARTIN -> when (topDimension) {
                "默契感" -> "你真正想要的不是表面的一起玩樂，而是疲憊時一個眼神、一句關心，就知道彼此都在同一個頻率的深度共鳴。"
                "溫柔感" -> "他在人群中總是不自覺地照顧每個人，但唯有你被他當成獨一無二的情緒首位時，最讓你無法抗拒。"
                "可靠感" -> "外表像個溫暖舒服的小棉襖，但當他為了珍惜的夥伴挺身而出時，那份堅定真誠令人無比安心。"
                else -> "你想要的愛情很純粹：有人能像他一樣，把你的微小情緒視為世界上最重要的事，好好放在心上。"
            }
            CortisMember.SEONGHYEON -> when (topDimension) {
                "反差感" -> "對外是有條理、有明確標準的嚴謹派，但唯有在最熟的你面前，他才會徹底卸下防備展現幼稚可愛的一面。"
                "可靠感" -> "他對細節的偏執與對事情的合理秩序感，給了你最不可動搖的踏實感——你知道只要有他在，事情就不會失控。"
                "神秘感" -> "他有著強烈的自尊與獨特的審美哲學，這種不隨波逐流的清醒姿態，正是最吸引你的核心魅力。"
                else -> "他不是對所有事情都斤斤計較，但他認真對待你在意的小事的那個側臉，就是你的心動暴擊點。"
            }
            CortisMember.KEONHO -> when (topDimension) {
                "神秘感" -> "他在眾人面前是炒熱氣氛的開心果，但他心中那道隱約的高牆，反而成了最讓你想悄悄翻過去探尋的謎題。"
                "玩心" -> "跟著他在一起永遠不缺驚喜與歡樂，但在熱鬧褪去後，他身上流露出的成熟與冷靜更讓人難以移開視線。"
                "反差感" -> "外熱內冷的極致反差：越是嘻嘻哈哈，越代表真心不易交付，而當他願意為你留出一隅真實時，比任何告白都深情。"
                else -> "你可能先是被他的開朗頑皮吸引，但真正讓你甘願陷進去的，是他熱鬧身軀下那個清醒而溫柔的靈魂。"
            }
        }
    }
}
