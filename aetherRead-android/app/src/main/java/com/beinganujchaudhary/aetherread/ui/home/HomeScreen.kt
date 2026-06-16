package com.beinganujchaudhary.aetherread.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToReader: (String) -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedBottomNav by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0D0D0D))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Placeholder for Logo
                    Icon(
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = "Logo",
                        tint = Color(0xFFF21826),
                        modifier = Modifier.size(28.dp)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                        
                        Box {
                            Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Color.White)
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFFF21826), CircleShape)
                                    .align(Alignment.TopEnd)
                            )
                        }
                        
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Gray, CircleShape)
                                .clickable { onNavigateToProfile() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
                Text(
                    text = "Welcome back, Anuj",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF4C8DFF), // Blue FAB color
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Black)
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0D0D0D),
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedBottomNav == 0,
                    onClick = { selectedBottomNav = 0 },
                    icon = { Icon(if (selectedBottomNav == 0) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "Home") },
                    label = { Text("Home", color = if (selectedBottomNav == 0) Color(0xFF4C8DFF) else Color.Gray) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4C8DFF),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 1,
                    onClick = { selectedBottomNav = 1 },
                    icon = { Icon(Icons.Outlined.AddCircleOutline, contentDescription = "Create") },
                    label = { Text("Create", color = Color.Gray) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 2,
                    onClick = { selectedBottomNav = 2 },
                    icon = { Icon(Icons.Outlined.CloudQueue, contentDescription = "PDF Spaces") },
                    label = { Text("PDF Spaces", color = Color.Gray) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 3,
                    onClick = { selectedBottomNav = 3 },
                    icon = { Icon(Icons.Outlined.InsertDriveFile, contentDescription = "Files") },
                    label = { Text("Files", color = Color.Gray) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 4,
                    onClick = { selectedBottomNav = 4 },
                    icon = { Icon(Icons.Outlined.GridView, contentDescription = "Tools") },
                    label = { Text("Tools", color = Color.Gray) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        },
        containerColor = Color(0xFF0D0D0D) // Black background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                // Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF2A2A38)) // Dark purplish blue
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFF1E1E2E), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF8E8CD8), modifier = Modifier.size(32.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                                Text("Easily create standout content", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Get a jumpstart on flyers, social posts and more with templates for easy customization.",
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Try it now", color = Color(0xFF4C8DFF), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.End))
                        }
                    }
                }
            }

            item {
                // Quick Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QuickActionItem("AI Assistant", Icons.Default.ChatBubbleOutline, Color(0xFF384666), badgeColor = Color(0xFF8E8CD8))
                    QuickActionItem("Export PDF", Icons.Default.ArrowCircleRight, Color(0xFF1E3A3A), badgeIcon = Icons.Default.Star) // Placeholder for crown
                    QuickActionItem("Organize pages", Icons.Default.ViewModule, Color(0xFF2A3A22), badgeIcon = Icons.Default.Star)
                    QuickActionItem("Edit PDF", Icons.Default.Edit, Color(0xFF402230), badgeIcon = Icons.Default.Star)
                    QuickActionItem("Compress", Icons.Default.Compress, Color(0xFF1E3A3A))
                }
            }

            item {
                // Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFF0D0D0D),
                    contentColor = Color.White,
                    edgePadding = 16.dp,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color.White
                        )
                    },
                    divider = {}
                ) {
                    Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Recent", fontWeight = FontWeight.Bold) })
                    Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Starred", color = Color.Gray) })
                    Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("On device", color = Color.Gray) })
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // File List
            items(5) { index ->
                FileListItem(
                    title = if (index == 0) "Hands-On Large Language Models by Jay Alammar, Maarte..." else "Machine Learning With PyTorch and Scikit Learn",
                    date = if (index == 0) "Yesterday" else "13 Jun 2026",
                    size = if (index == 0) "19.9 MB" else "29.3 MB",
                    onNavigateToReader = onNavigateToReader
                )
            }
            
            item {
                 Spacer(modifier = Modifier.height(80.dp)) // padding for FAB
            }
        }
    }
}

@Composable
fun QuickActionItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color,
    badgeColor: Color? = null,
    badgeIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(containerColor, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(28.dp))
            
            if (badgeColor != null) {
                Box(modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)) {
                    Icon(Icons.Default.Star, contentDescription = "Badge", tint = badgeColor, modifier = Modifier.size(16.dp))
                }
            } else if (badgeIcon != null) {
                Box(modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).background(Color(0xFF8E8CD8), CircleShape).padding(2.dp)) {
                    Icon(badgeIcon, contentDescription = "Premium", tint = Color.White, modifier = Modifier.size(12.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 12.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            maxLines = 2,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun FileListItem(title: String, date: String, size: String, onNavigateToReader: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToReader("dummy_id") }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Red PDF icon with white logo
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
             Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF", tint = Color(0xFFF21826), modifier = Modifier.size(32.dp))
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(Color(0xFFF21826), RoundedCornerShape(2.dp)).padding(horizontal = 4.dp, vertical = 2.dp)) {
                    Text("PDF", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(date, color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(size, color = Color.Gray, fontSize = 12.sp)
            }
        }
        
        IconButton(onClick = { /* TODO */ }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = Color.Gray)
        }
    }
}
