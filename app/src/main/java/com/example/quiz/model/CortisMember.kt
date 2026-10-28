package com.example.quiz.model

import androidx.compose.ui.graphics.Color

/**
 * CORTIS 成員角色原型設定（粉絲視角娛樂向）
 */
enum class CortisMember(
    val chineseName: String,
    val englishName: String,
    val title: String,
    val quote: String,
    val coreArchetype: String,
    val resultSummary: String,
    val detailedDescription: String,
    val closingRemark: String,
    val themeColor: Color,
    val badgeColor: Color
) {
    JAMES(
        chineseName = "趙雨凡",
        englishName = "James",
        title = "外瘋內穩的完美主義守護者",
        quote = "「看起來最鬧的人之一，真正需要他的時候，他永遠是最可靠的那個。」",
        coreArchetype = "看起來最鬧，但真正需要時永遠最可靠的扛霸子",
        resultSummary = "你容易被那種平常很鬧、甚至有點讓人摸不透，但真正重要的時候會突然變得超可靠的人吸引。",
        detailedDescription = "他不是只會說『沒事』，而是會直接走到你面前把問題處理好。他有自己獨特甚至有點怪的幽默感，但在自己負責的領域裡，有著『既然要做就要做到最好』的強烈堅持。",
        closingRemark = "你喜歡的可能不是完美無瑕，而是那種『我會把你放在心上，默默扛下一切』的踏實安全感。",
        themeColor = Color(0xFFFF5252), // Energetic Coral Red
        badgeColor = Color(0xFFFFEBEE)
    ),
    JUHOON(
        chineseName = "金主訓",
        englishName = "Juhoon",
        title = "安靜溫柔、偶爾偷偷皮的觀察者",
        quote = "「我可能不會一直說，但我其實有注意到。」",
        coreArchetype = "安靜不代表乖，有著細膩觀察力與靈魂深度的慢熟者",
        resultSummary = "你容易被不急著表現自己、自帶沉靜氣場的人吸引。",
        detailedDescription = "他可能不會一直把關心掛在嘴邊，但你會慢慢發現，他其實記得很多你以為他沒注意到的小事。他有著自己廣闊的精神世界與獨特美學，熟悉之後，偶爾露出的頑皮反差會讓人措手不及。",
        closingRemark = "最危險的是——你可能會在發現他不經意偷偷皮的那一瞬間，徹底越陷越深。",
        themeColor = Color(0xFF2979FF), // Creative Azure Blue
        badgeColor = Color(0xFFE3F2FD)
    ),
    MARTIN(
        chineseName = "馬丁",
        englishName = "Martin",
        title = "溫暖照顧型的小棉襖",
        quote = "「如果你對我很重要，那你的情緒，我也會放在心上。」",
        coreArchetype = "高情感共感力、視夥伴為第一的真誠小暖爐",
        resultSummary = "你真正容易心動的，可能不是單純的陽光開朗，而是那種會把你的情緒也認真放進心裡的人。",
        detailedDescription = "他可以陪著你天馬行空地大笑，也可以在周遭喧鬧中第一個察覺到你微弱的不適。他對身邊的人充滿真誠與保護慾，有時心思細膩得讓人心疼，需要一個懂得好好呵護他的人。",
        closingRemark = "你想要的其實是一種純粹的心意：『無論何時，我都正在被溫柔地好好放在心上。』",
        themeColor = Color(0xFFFF9100), // Warm Sunny Amber
        badgeColor = Color(0xFFFFF3E0)
    ),
    SEONGHYEON(
        chineseName = "嚴成玹",
        englishName = "Seonghyeon",
        title = "反差細節型的秩序派",
        quote = "「我不是對所有事情都在意，但我在意的事情，我真的很在意。」",
        coreArchetype = "擁有明確原則與秩序感，熟人限定反差萌的細節控",
        resultSummary = "你很容易被有自己標準、有想法、極度注意細節的人深深吸引。",
        detailedDescription = "他不是對所有事情都斤斤計較，但只要是他認定的原則與審美，就會有一股絕不妥協的認真勁。在不熟的人面前冷靜客氣，但一旦進入他的熟人圈，就會解鎖毫無包袱的幼稚搞笑面貌。",
        closingRemark = "你可能就是會被這種『平時高標準、只在熟人面前軟下來』的專屬反差感精準擊中。",
        themeColor = Color(0xFF7C4DFF), // Distinct Royal Purple
        badgeColor = Color(0xFFEDE7F6)
    ),
    KEONHO(
        chineseName = "安乾鎬",
        englishName = "Keonho",
        title = "外熱內冷的高牆系頑皮男孩",
        quote = "「我可以跟你玩得很瘋，但真正的我，不一定會全部給你看。」",
        coreArchetype = "炒熱氣氛的頑皮外表下，藏著高防禦心靈邊界的成熟靈魂",
        resultSummary = "你可能很容易先被他滿滿的玩心與搞笑氛圍吸引，但在熱鬧過後，會看見他內在深刻的沉著。",
        detailedDescription = "他可以跟所有人玩得超瘋超嗨，但在心靈深處其實築著一道若隱若現的高牆。他有著成熟通透的一面，不輕易把脆弱攤在陽光下，越想看懂他的真心，就越會被他隱約的疏離感所牽引。",
        closingRemark = "你喜歡的可能不是表面的熱鬧，而是熱鬧落幕後，那個還沒有被任何人完全看透的真實靈魂。",
        themeColor = Color(0xFF00B0FF), // Vibrant Sky Cyan
        badgeColor = Color(0xFFE1F5FE)
    );

    val fullName: String get() = "$chineseName / $englishName"
}
