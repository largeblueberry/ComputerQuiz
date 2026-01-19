package com.largeblueberry.computerquiz.hypothesis

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *
 * 이 디자인은 유지한다.
 * 다만, 지금 이걸 개발하는 것은 매우 어렵다.
 * 뒤로 미루고, 템플릿에 집중한다.
 * 카톡에 공유해서 엔지니어링 사고를 담을 수 있는 템플릿을 개발한다.
 */

// --- Data Models ---
data class Comment(val user: String, val text: String, val time: String)
data class Hypothesis(
    val id: Int,
    val user: String,
    val time: String,
    val hypothesis: String,
    val experiment: String,
    val analysis: String,
    val votes: Int,
    val comments: List<Comment>
)

// --- Theme Colors (Tailwind Slate equivalent) ---
val Slate50 = Color(0xFFF8FAFC)
val Slate100 = Color(0xFFF1F5F9)
val Slate200 = Color(0xFFE2E8F0)
val Slate400 = Color(0xFF94A3B8)
val Slate500 = Color(0xFF64748B)
val Slate600 = Color(0xFF475569)
val Slate700 = Color(0xFF334155)
val Slate800 = Color(0xFF1E293B)
val Slate900 = Color(0xFF0F172A)
val Emerald600 = Color(0xFF059669)
val Emerald700 = Color(0xFF047857)
val Blue600 = Color(0xFF2563EB)
val Orange600 = Color(0xFFEA580C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionScreen() {
    val incidentTitle = "메모리 누수 사건 #12"
    val incidentStatus = "Open"
    val incidentAuthor = "SeniorDev"

    val hypotheses = listOf(
        Hypothesis(
            id = 1,
            user = "KimCode",
            time = "2시간 전",
            hypothesis = "Fragment 내부의 익명 클래스에서 외부 Activity를 참조하고 있어 GC가 수거하지 못하는 것 같습니다.",
            experiment = "LeakCanary를 설치하고 Fragment destroy 시점에 릭 트레이스를 캡쳐합니다.",
            analysis = "Heap Dump에서 해당 Fragment의 인스턴스가 1개 이상 존재하면 가설이 성립합니다.",
            votes = 12,
            comments = listOf(
                Comment("LeeDev", "실험 방식이 아주 깔끔하네요. 저도 동의합니다.", "1시간 전")
            )
        ),
        Hypothesis(
            id = 2,
            user = "ParkTech",
            time = "5시간 전",
            hypothesis = "단순히 비트맵 이미지가 너무 커서 발생하는 OOM일 가능성이 높습니다.",
            experiment = "이미지 로딩 라이브러리의 캐시 설정을 확인합니다.",
            analysis = "메모리 점유율이 일정 수준에서 유지된다면 이 가설이 맞습니다.",
            votes = -3,
            comments = emptyList()
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(incidentTitle, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Slate900)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Status: ", fontSize = 10.sp, color = Slate500)
                            Text(incidentStatus, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                            Text(" • by $incidentAuthor", fontSize = 10.sp, color = Slate500)
                        }
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 8.dp)
                            .size(32.dp)
                            .background(Color(0xFFD1FAE5), RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Emerald700, modifier = Modifier.size(20.dp))
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Share, contentDescription = null, tint = Slate400) }
                    IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, contentDescription = null, tint = Slate400) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Slate900),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("나의 HEA 가설 등록하기", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Slate50)
                .padding(padding)
        ) {
            // Timeline Line
            Box(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Slate200)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(hypotheses) { h ->
                    HypothesisItem(h)
                }
            }
        }
    }
}

@Composable
fun HypothesisItem(h: Hypothesis) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Avatar on Timeline
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(4.dp, Slate50, CircleShape)
                    .background(Slate800, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Card content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, Slate200, RoundedCornerShape(12.dp))
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Slate50)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${h.user}님의 가설", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate700)
                    Text(h.time, fontSize = 10.sp, color = Slate400)
                }

                // Body
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Section(title = "Hypothesis", content = h.hypothesis, color = Emerald600)

                    // Experiment Box
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Slate50, RoundedCornerShape(8.dp))
                            .border(1.dp, Slate200, RoundedCornerShape(8.dp)) // Dash border is complex in Compose, using solid for now
                            .padding(12.dp)
                    ) {
                        Text("EXPERIMENT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Blue600)
                        Text(h.experiment, fontSize = 12.sp, color = Slate600, modifier = Modifier.padding(top = 4.dp))
                    }

                    Section(title = "Analysis", content = h.analysis, color = Orange600)
                }

                // Footer Actions
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Slate50
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ActionButton(icon = Icons.Outlined.ThumbUp, label = if (h.votes > 0) h.votes.toString() else "0")
                        ActionButton(icon = Icons.Outlined.ThumbDown, label = if (h.votes < 0) h.votes.absoluteValue().toString() else "0")
                    }
                    ActionButton(icon = Icons.Outlined.Email, label = "${h.comments.size} 댓글")
                }
            }
        }

        // Nested Comments
        h.comments.forEach { comment ->
            Row(
                modifier = Modifier
                    .padding(start = 44.dp, top = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // Substitute for CornerDownRight
                    contentDescription = null,
                    tint = Slate200,
                    modifier = Modifier.size(20.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Slate100, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(comment.user, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate700)
                        Text(comment.time, fontSize = 10.sp, color = Slate400)
                    }
                    Text(comment.text, fontSize = 12.sp, color = Slate600, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}

@Composable
fun Section(title: String, content: String, color: Color) {
    Column {
        Text(title.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
        Text(content, fontSize = 14.sp, color = Slate800, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun ActionButton(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        SizedIcon(icon, contentDescription = null, size = 16.dp, tint = Slate500)
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate500)
    }
}

fun Int.absoluteValue() = if (this < 0) -this else this

/**
 * Helper Composable to simplify Icon sizing.
 * Fixed: Added @Composable annotation and renamed to avoid confusion.
 */
@Composable
private fun SizedIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    size: androidx.compose.ui.unit.Dp,
    tint: Color
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(size),
        tint = tint
    )
}

// --- Preview ---
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun DiscussionScreenPreview() {
    MaterialTheme {
        DiscussionScreen()
    }
}