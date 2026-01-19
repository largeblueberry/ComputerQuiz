package com.largeblueberry.computerquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Color Palette ---
val HexaEmerald = Color(0xFF10B981)
val HexaSlate900 = Color(0xFF0F172A)
val HexaSlate500 = Color(0xFF64748B)
val HexaSlate100 = Color(0xFFF1F5F9)
val HexaBg = Color(0xFFF8FAFC)

// --- Data Models ---
data class Incident(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val difficulty: String,
    val hypotheses: Int,
    val participants: Int,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HexaMainScreen() {
    var selectedCategory by remember { mutableStateOf("전체") }
    val categories = listOf("전체", "비기너", "CS 지식", "안드로이드", "서버")

    Scaffold(
        containerColor = HexaBg,
        topBar = { HexaTopBar() },
        bottomBar = { HexaBottomNavigation() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 1. Category Filter
            item {
                CategoryChipBar(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }

            // 2. Daily Challenge Banner
            item {
                DailyChallengeBanner()
            }

            // 3. Section Title
            item {
                Text(
                    text = "새로운 사건 파일",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }

            // 4. Incident List
            val mockIncidents = listOf(
                Incident("INC-1024", "Git Merge Conflict 대공습", "중요한 배포 직전, 1,000줄의 컨플릭트가 발생했습니다.", listOf("Git", "OpenSource"), "Medium", 42, 128, "New"),
                Incident("INC-1025", "안드로이드 백그라운드 크래시", "Android 12 이상에서 PendingIntent 에러 분석", listOf("Android", "OS"), "Hard", 15, 85, "Solving"),
                Incident("INC-1026", "React 무한 리렌더링의 늪", "useEffect 의존성 배열 오류로 인한 프리징 해결", listOf("React", "Web"), "Easy", 56, 210, "Solved")
            )

            items(mockIncidents) { incident ->
                IncidentCard(incident)
            }
        }
    }
}

@Composable
fun HexaTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "HEXA LAB",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = HexaSlate900
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "JUNIOR ENGINEER LV.3",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = HexaSlate500
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TopBarButton(Icons.Default.Search)
            TopBarButton(Icons.Default.Notifications)
        }
    }
}

@Composable
fun TopBarButton(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(HexaSlate100)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = HexaSlate900, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun CategoryChipBar(categories: List<String>, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.background(Color.White)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Surface(
                modifier = Modifier.clickable { onCategorySelected(category) },
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) HexaSlate900 else HexaSlate100,
                contentColor = if (isSelected) Color.White else HexaSlate500
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DailyChallengeBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(HexaEmerald, Color(0xFF059669))
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Whatshot, contentDescription = null, tint = Color(0xFFFDE68A), modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("TODAY'S HOT INCIDENT", color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
            Spacer(Modifier.height(8.dp))
            Text("메모리 누수 사건 #12", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("현재 42명의 엔지니어가 가설 검증 중", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("사건 현장으로 가기", color = HexaEmerald, fontWeight = FontWeight.Black, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun IncidentCard(incident: Incident) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, HexaSlate100)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(incident.id, style = MaterialTheme.typography.labelSmall, color = HexaSlate500, fontWeight = FontWeight.Bold)
                StatusBadge(incident.status)
            }
            Spacer(Modifier.height(8.dp))
            Text(incident.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = HexaSlate900)
            Text(incident.description, style = MaterialTheme.typography.bodySmall, color = HexaSlate500, maxLines = 2, overflow = TextOverflow.Ellipsis)

            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                incident.tags.forEach { tag -> TagBadge(tag) }
                DifficultyBadge(incident.difficulty)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = HexaSlate100)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatItem(Icons.AutoMirrored.Outlined.Message, incident.hypotheses.toString())
                    StatItem(Icons.Outlined.CheckCircle, incident.participants.toString())
                }
                Text("분석 시작 >", color = HexaEmerald, fontWeight = FontWeight.Black, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when(status) {
        "New" -> Color(0xFF3B82F6)
        "Solving" -> Color(0xFFF59E0B)
        else -> HexaSlate500
    }
    Surface(color = color.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp)) {
        Text(status, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), color = color, fontSize = 10.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun TagBadge(tag: String) {
    Surface(color = HexaSlate100, shape = RoundedCornerShape(6.dp)) {
        Text("#$tag", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), color = HexaSlate500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DifficultyBadge(difficulty: String) {
    val color = when(difficulty) {
        "Hard" -> Color.Red
        "Medium" -> Color(0xFFF59E0B)
        else -> HexaEmerald
    }
    Surface(color = color.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp)) {
        Text(difficulty, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatItem(icon: ImageVector, count: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = HexaSlate500, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(4.dp))
        Text(count, color = HexaSlate500, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HexaBottomNavigation() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        // 1. 홈 (선택됨)
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Language, contentDescription = null) }, // 지구본 아이콘
            label = { Text("홈", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HexaEmerald,
                selectedTextColor = HexaEmerald,
                unselectedIconColor = HexaSlate500,
                unselectedTextColor = HexaSlate500,
                indicatorColor = Color.Transparent // 선택 시 배경 원 제거 (이미지 스타일)
            )
        )

        // 2. 내 가설
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Code, contentDescription = null) }, // </> 아이콘
            label = { Text("내 가설", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = HexaSlate500,
                unselectedTextColor = HexaSlate500
            )
        )

        // 3. 스터디
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Storage, contentDescription = null) }, // 데이터베이스 아이콘
            label = { Text("스터디", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = HexaSlate500,
                unselectedTextColor = HexaSlate500
            )
        )

        // 4. 프로필
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Search, contentDescription = null) }, // 돋보기 아이콘 (이미지 기준)
            label = { Text("프로필", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = HexaSlate500,
                unselectedTextColor = HexaSlate500
            )
        )
    }
}


@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun HexaMainScreenPreview() {
    MaterialTheme {
        HexaMainScreen()
    }
}