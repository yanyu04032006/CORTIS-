package com.example.quiz.model

// 成員權重矩陣
data class MemberWeights(
    val james: Int = 0,
    val juhoon: Int = 0,
    val martin: Int = 0,
    val seonghyeon: Int = 0,
    val keonho: Int = 0
) {
    operator fun plus(other: MemberWeights): MemberWeights {
        return MemberWeights(
            james = this.james + other.james,
            juhoon = this.juhoon + other.juhoon,
            martin = this.martin + other.martin,
            seonghyeon = this.seonghyeon + other.seonghyeon,
            keonho = this.keonho + other.keonho
        )
    }

    fun getScore(member: CortisMember): Int = when (member) {
        CortisMember.JAMES -> james
        CortisMember.JUHOON -> juhoon
        CortisMember.MARTIN -> martin
        CortisMember.SEONGHYEON -> seonghyeon
        CortisMember.KEONHO -> keonho
    }
}

// 六大心動隱藏維度
data class HiddenDimensions(
    val reliability: Int = 0,   // 可靠感
    val gentleCare: Int = 0,    // 溫柔感
    val gapMoe: Int = 0,        // 反差感
    val resonance: Int = 0,     // 默契感
    val mystery: Int = 0,       // 神秘感
    val playfulness: Int = 0    // 玩心
) {
    operator fun plus(other: HiddenDimensions): HiddenDimensions {
        return HiddenDimensions(
            reliability = this.reliability + other.reliability,
            gentleCare = this.gentleCare + other.gentleCare,
            gapMoe = this.gapMoe + other.gapMoe,
            resonance = this.resonance + other.resonance,
            mystery = this.mystery + other.mystery,
            playfulness = this.playfulness + other.playfulness
        )
    }
}

data class QuizOption(
    val letter: String,
    val text: String,
    val weights: MemberWeights,
    val dimensions: HiddenDimensions
)

data class QuizQuestion(
    val id: Int,
    val prompt: String,
    val options: List<QuizOption>
)

object QuizRepository {
    // 預設靜態題目庫（已徹底打散選項順序，消除任何固定成員排序）
    val questions: List<QuizQuestion> = listOf(
        // Q1
        QuizQuestion(
            id = 1,
            prompt = "第一次認識一個人，你最容易注意到什麼？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "他看起來很有自己的規則和標準。",
                    weights = MemberWeights(james = 3, juhoon = 2, martin = 1, seonghyeon = 5, keonho = 3),
                    dimensions = HiddenDimensions(reliability = 4, mystery = 3, gapMoe = 3)
                ),
                QuizOption(
                    letter = "B",
                    text = "他安靜待在自己的世界裡，反而讓你很好奇。",
                    weights = MemberWeights(james = 1, juhoon = 5, martin = 1, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(mystery = 5, gentleCare = 3, gapMoe = 2)
                ),
                QuizOption(
                    letter = "C",
                    text = "他明明一直在搞笑，卻有一種說不出的距離感。",
                    weights = MemberWeights(james = 3, juhoon = 2, martin = 1, seonghyeon = 2, keonho = 5),
                    dimensions = HiddenDimensions(playfulness = 5, mystery = 4, gapMoe = 4)
                ),
                QuizOption(
                    letter = "D",
                    text = "他明明沒說什麼，卻一直在照顧別人。",
                    weights = MemberWeights(james = 4, juhoon = 3, martin = 5, seonghyeon = 1, keonho = 1),
                    dimensions = HiddenDimensions(reliability = 4, gentleCare = 5, resonance = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "他好像很在乎身邊人的感受。",
                    weights = MemberWeights(james = 2, juhoon = 3, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 4, reliability = 2)
                )
            )
        ),

        // Q2
        QuizQuestion(
            id = 2,
            prompt = "後來發現一個人跟第一印象完全不一樣，你會覺得？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "原來他一直默默注意著身邊的人。",
                    weights = MemberWeights(james = 4, juhoon = 4, martin = 5, seonghyeon = 2, keonho = 1),
                    dimensions = HiddenDimensions(gentleCare = 5, reliability = 4, resonance = 3)
                ),
                QuizOption(
                    letter = "B",
                    text = "原來他的笑鬧背後，其實藏著很多自己的世界。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 2, seonghyeon = 2, keonho = 5),
                    dimensions = HiddenDimensions(mystery = 5, gapMoe = 4, playfulness = 3)
                ),
                QuizOption(
                    letter = "C",
                    text = "原來他其實比想像中還要瘋。",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 3, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(gapMoe = 5, playfulness = 5, reliability = 2)
                ),
                QuizOption(
                    letter = "D",
                    text = "原來他對很多小細節都有自己的堅持。",
                    weights = MemberWeights(james = 4, juhoon = 3, martin = 2, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 4, gapMoe = 3, mystery = 2)
                ),
                QuizOption(
                    letter = "E",
                    text = "原來他只是安靜，不是真的乖。",
                    weights = MemberWeights(james = 2, juhoon = 5, martin = 2, seonghyeon = 4, keonho = 3),
                    dimensions = HiddenDimensions(gapMoe = 5, mystery = 4, playfulness = 3)
                )
            )
        ),

        // Q3
        QuizQuestion(
            id = 3,
            prompt = "朋友今天看起來心情很差，你會希望他怎麼被對待？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "不逼他說話，但默默陪著。",
                    weights = MemberWeights(james = 2, juhoon = 5, martin = 4, seonghyeon = 3, keonho = 3),
                    dimensions = HiddenDimensions(gentleCare = 5, mystery = 3, resonance = 4)
                ),
                QuizOption(
                    letter = "B",
                    text = "有人仔細觀察，找到真正讓他不舒服的地方。",
                    weights = MemberWeights(james = 3, juhoon = 4, martin = 3, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 4, gentleCare = 4, gapMoe = 2)
                ),
                QuizOption(
                    letter = "C",
                    text = "有人直接陪著他，把事情一起處理掉。",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 3, seonghyeon = 4, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 5, resonance = 3, gentleCare = 2)
                ),
                QuizOption(
                    letter = "D",
                    text = "先陪他玩、讓他放鬆，之後再悄悄關心。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 4, seonghyeon = 1, keonho = 5),
                    dimensions = HiddenDimensions(playfulness = 5, resonance = 4, gentleCare = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "有一個很溫柔的人先注意到他的情緒。",
                    weights = MemberWeights(james = 2, juhoon = 4, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 5, reliability = 2)
                )
            )
        ),

        // Q4
        QuizQuestion(
            id = 4,
            prompt = "如果你們一起完成一件很重要的事情，你最喜歡哪種搭檔？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "表面一直鬧，真正重要的時候卻很可靠。",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 3, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(gapMoe = 5, reliability = 5, playfulness = 4)
                ),
                QuizOption(
                    letter = "B",
                    text = "說到做到，遇到問題會直接扛起來。",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 3, seonghyeon = 4, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 5, resonance = 3)
                ),
                QuizOption(
                    letter = "C",
                    text = "很在乎每個人的狀態，不希望有人被落下。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 5, reliability = 3)
                ),
                QuizOption(
                    letter = "D",
                    text = "安靜地把事情做好，偶爾突然皮一下。",
                    weights = MemberWeights(james = 2, juhoon = 5, martin = 2, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(gapMoe = 4, playfulness = 4, gentleCare = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "有明確標準，連小細節都不放過。",
                    weights = MemberWeights(james = 4, juhoon = 2, martin = 1, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 5, gapMoe = 3)
                )
            )
        ),

        // Q5
        QuizQuestion(
            id = 5,
            prompt = "你覺得跟一個人最容易聊到停不下來的是？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "突然聊到一個很奇怪的想法，然後越聊越有趣。",
                    weights = MemberWeights(james = 5, juhoon = 4, martin = 2, seonghyeon = 4, keonho = 4),
                    dimensions = HiddenDimensions(playfulness = 4, mystery = 4, gapMoe = 3)
                ),
                QuizOption(
                    letter = "B",
                    text = "從一個很蠢的話題一路笑到停不下來。",
                    weights = MemberWeights(james = 4, juhoon = 3, martin = 4, seonghyeon = 3, keonho = 5),
                    dimensions = HiddenDimensions(playfulness = 5, resonance = 4)
                ),
                QuizOption(
                    letter = "C",
                    text = "聊人生、夢想、未來。",
                    weights = MemberWeights(james = 4, juhoon = 5, martin = 4, seonghyeon = 4, keonho = 3),
                    dimensions = HiddenDimensions(resonance = 5, mystery = 4, reliability = 3)
                ),
                QuizOption(
                    letter = "D",
                    text = "表面上一直互嘴，其實慢慢發現彼此很懂對方。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 2, seonghyeon = 4, keonho = 5),
                    dimensions = HiddenDimensions(gapMoe = 5, resonance = 5, playfulness = 4)
                ),
                QuizOption(
                    letter = "E",
                    text = "聊一些只有你們兩個才懂的事情。",
                    weights = MemberWeights(james = 3, juhoon = 4, martin = 3, seonghyeon = 5, keonho = 4),
                    dimensions = HiddenDimensions(resonance = 5, gapMoe = 4, mystery = 3)
                )
            )
        ),

        // Q6
        QuizQuestion(
            id = 6,
            prompt = "如果一個平常很會鬧的人突然安靜下來，你會怎麼想？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "他可能只是想待在自己的世界裡。",
                    weights = MemberWeights(james = 2, juhoon = 5, martin = 2, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(mystery = 5, gentleCare = 3)
                ),
                QuizOption(
                    letter = "B",
                    text = "他看起來沒事，但我總覺得他沒有全部說出來。",
                    weights = MemberWeights(james = 3, juhoon = 4, martin = 3, seonghyeon = 3, keonho = 5),
                    dimensions = HiddenDimensions(mystery = 5, gapMoe = 4, resonance = 3)
                ),
                QuizOption(
                    letter = "C",
                    text = "他是不是在想別人的事情？",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 4)
                ),
                QuizOption(
                    letter = "D",
                    text = "他是不是其實自己扛了很多？",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 4, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(reliability = 4, resonance = 4, gapMoe = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "是不是有什麼地方讓他覺得不舒服？",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 3, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(gapMoe = 3, gentleCare = 3, mystery = 3)
                )
            )
        ),

        // Q7
        QuizQuestion(
            id = 7,
            prompt = "哪一個瞬間最容易讓你心動？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "他真的很在乎你的感受，而且不是嘴上說說。",
                    weights = MemberWeights(james = 4, juhoon = 4, martin = 5, seonghyeon = 3, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 5, reliability = 3)
                ),
                QuizOption(
                    letter = "B",
                    text = "他沒說什麼，卻已經把你需要的東西準備好了。",
                    weights = MemberWeights(james = 5, juhoon = 4, martin = 4, seonghyeon = 4, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 5, gentleCare = 4)
                ),
                QuizOption(
                    letter = "C",
                    text = "你們明明一直在互嘴，某一刻卻突然很有默契。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 3, seonghyeon = 4, keonho = 5),
                    dimensions = HiddenDimensions(resonance = 5, gapMoe = 4, playfulness = 4)
                ),
                QuizOption(
                    letter = "D",
                    text = "他記得你很久以前隨口說過的小事。",
                    weights = MemberWeights(james = 3, juhoon = 5, martin = 4, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 4, resonance = 5, gapMoe = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "他只在很熟的人面前露出完全不同的一面。",
                    weights = MemberWeights(james = 4, juhoon = 4, martin = 3, seonghyeon = 5, keonho = 4),
                    dimensions = HiddenDimensions(gapMoe = 5, mystery = 3, playfulness = 3)
                )
            )
        ),

        // Q8
        QuizQuestion(
            id = 8,
            prompt = "如果跟喜歡的人一起旅行，你最想要哪種感覺？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "表面玩得超瘋，私底下卻有只有你們懂的小暗號。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 2, seonghyeon = 3, keonho = 5),
                    dimensions = HiddenDimensions(playfulness = 5, resonance = 4, mystery = 4)
                ),
                QuizOption(
                    letter = "B",
                    text = "慢慢走，聊一些只有彼此才懂的事情。",
                    weights = MemberWeights(james = 2, juhoon = 5, martin = 4, seonghyeon = 3, keonho = 3),
                    dimensions = HiddenDimensions(resonance = 5, gentleCare = 4, mystery = 3)
                ),
                QuizOption(
                    letter = "C",
                    text = "不一定照計畫走，但重要的小細節不能亂。",
                    weights = MemberWeights(james = 4, juhoon = 2, martin = 2, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 4, gapMoe = 3)
                ),
                QuizOption(
                    letter = "D",
                    text = "一路注意大家開不開心，有人累了也會發現。",
                    weights = MemberWeights(james = 4, juhoon = 3, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 4, reliability = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "行程亂一點沒關係，但有人會照顧大家。",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 5, gentleCare = 4)
                )
            )
        ),

        // Q9
        QuizQuestion(
            id = 9,
            prompt = "如果他遇到挫折，你最想對他說什麼？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "你不用一個人扛。",
                    weights = MemberWeights(james = 5, juhoon = 2, martin = 4, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(reliability = 5, resonance = 4)
                ),
                QuizOption(
                    letter = "B",
                    text = "我知道你可能很在意，但我們可以慢慢處理。",
                    weights = MemberWeights(james = 4, juhoon = 3, martin = 3, seonghyeon = 5, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 4, gentleCare = 3, gapMoe = 3)
                ),
                QuizOption(
                    letter = "C",
                    text = "你想說的時候，我會聽。",
                    weights = MemberWeights(james = 3, juhoon = 5, martin = 4, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 4, mystery = 3)
                ),
                QuizOption(
                    letter = "D",
                    text = "你現在不想說也沒關係，我還是在。",
                    weights = MemberWeights(james = 3, juhoon = 4, martin = 4, seonghyeon = 3, keonho = 5),
                    dimensions = HiddenDimensions(mystery = 4, gentleCare = 4, resonance = 4)
                ),
                QuizOption(
                    letter = "E",
                    text = "你不用一直顧慮別人的感受，也可以照顧自己。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 5, seonghyeon = 2, keonho = 2),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 5)
                )
            )
        ),

        // Q10
        QuizQuestion(
            id = 10,
            prompt = "如果你真的喜歡上一個人，最後讓你留下來的會是？",
            options = listOf(
                QuizOption(
                    letter = "A",
                    text = "他只讓你看到別人看不到的那一面。",
                    weights = MemberWeights(james = 4, juhoon = 4, martin = 3, seonghyeon = 5, keonho = 5),
                    dimensions = HiddenDimensions(gapMoe = 5, mystery = 4)
                ),
                QuizOption(
                    letter = "B",
                    text = "你慢慢發現，原來你們的默契比想像中還深。",
                    weights = MemberWeights(james = 3, juhoon = 4, martin = 4, seonghyeon = 4, keonho = 5),
                    dimensions = HiddenDimensions(resonance = 5, mystery = 3, gapMoe = 4)
                ),
                QuizOption(
                    letter = "C",
                    text = "有一天他突然說：『我其實一直有注意到。』",
                    weights = MemberWeights(james = 3, juhoon = 5, martin = 4, seonghyeon = 3, keonho = 3),
                    dimensions = HiddenDimensions(gentleCare = 5, resonance = 5, gapMoe = 3)
                ),
                QuizOption(
                    letter = "D",
                    text = "他總是在不知不覺中保護著身邊的人。",
                    weights = MemberWeights(james = 5, juhoon = 3, martin = 4, seonghyeon = 3, keonho = 2),
                    dimensions = HiddenDimensions(reliability = 5, gentleCare = 3)
                ),
                QuizOption(
                    letter = "E",
                    text = "你開始期待每天跟他一起笑、一起分享小事。",
                    weights = MemberWeights(james = 3, juhoon = 3, martin = 5, seonghyeon = 3, keonho = 4),
                    dimensions = HiddenDimensions(resonance = 5, playfulness = 4, gentleCare = 4)
                )
            )
        )
    )

    /**
     * 動態產生打亂選項順序的題目清單，每道題目的選項都會隨機重排，
     * 並由上至下依序指派 A、B、C、D、E 字母，確保沒有任何可被摸透的特定模式。
     */
    fun getShuffledQuestions(): List<QuizQuestion> {
        val letters = listOf("A", "B", "C", "D", "E")
        return questions.map { question ->
            val shuffledOptions = question.options.shuffled().mapIndexed { index, option ->
                option.copy(letter = letters[index])
            }
            question.copy(options = shuffledOptions)
        }
    }
}
