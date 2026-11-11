
package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mygroup.nasugburoots.data.sampleSpots
import com.mygroup.nasugburoots.model.TouristSpot
import com.mygroup.nasugburoots.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    var currentScreen by remember { mutableStateOf("Home") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var savedSpotIds by remember { mutableStateOf(setOf<Int>()) }
    var exploreOverride by remember {
        mutableStateOf<List<TouristSpot>?>(null)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val categories = remember {
        listOf("All") + sampleSpots.map { it.category }.distinct()
    }

    val visibleSpots = remember(searchQuery, selectedCategory) {
        sampleSpots.filter { spot ->
            val matchesSearch =
                spot.placeName.contains(searchQuery, ignoreCase = true) ||
                        spot.description.contains(searchQuery, ignoreCase = true) ||
                        spot.category.contains(searchQuery, ignoreCase = true)

            val matchesCategory =
                selectedCategory == "All" ||
                        spot.category == selectedCategory

            matchesSearch && matchesCategory
        }
    }

    val savedSpots = sampleSpots.filter {
        it.placeId in savedSpotIds
    }

    val selectedTab = when (currentScreen) {
        "Explore Heritage Sites", "Explore Map" -> 1
        "My Bookings" -> 2
        "Favorites" -> 3
        else -> 0
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Cream
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Forest)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "🌴 Nasugbu Roots",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Stories live here.",
                        color = Cream,
                        fontSize = 14.sp
                    )
                }

                Spacer(Modifier.height(16.dp))

                val menuItems = listOf(
                    "Home",
                    "Explore Heritage Sites",
                    "Explore Map",
                    "Find Tour Guides",
                    "My Bookings",
                    "Favorites",
                    "About Us"
                )

                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item) },
                        selected = currentScreen == item,
                        onClick = {
                            currentScreen = item
                            exploreOverride = null

                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Gold.copy(alpha = 0.35f),
                            selectedTextColor = Forest,
                            unselectedTextColor = DeepForest
                        )
                    )
                }
            }
        }
    ) {
        Scaffold(
            containerColor = Cream,

            topBar = {
                BrandTopBar(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            bottomBar = {
                NavigationBar(
                    containerColor = Cream,
                    tonalElevation = 8.dp
                ) {
                    val navigationItems = listOf(
                        Triple(Icons.Filled.Home, "Home", 0),
                        Triple(Icons.Outlined.Explore, "Explore", 1),
                        Triple(Icons.Outlined.CalendarMonth, "Visits", 2),
                        Triple(Icons.Outlined.BookmarkBorder, "Saved", 3)
                    )

                    navigationItems.forEach { (icon, label, index) ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = {
                                exploreOverride = null

                                currentScreen = when (index) {
                                    0 -> "Home"
                                    1 -> "Explore Heritage Sites"
                                    2 -> "My Bookings"
                                    else -> "Favorites"
                                }
                            },
                            icon = {
                                Icon(
                                    icon,
                                    contentDescription = label
                                )
                            },
                            label = {
                                Text(label, fontSize = 11.sp)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Forest,
                                selectedTextColor = Forest,
                                indicatorColor = Color(0xFFE1ECE5),
                                unselectedIconColor = Muted
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->

            when (currentScreen) {

                "Explore Heritage Sites" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        SpotListScreen(
                            exploreOverride ?: sampleSpots
                        )
                    }
                }

                "Explore Map" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NasugbuMapScreen()
                    }
                }

                "Favorites" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (savedSpots.isEmpty()) {
                            EmptyPage(
                                title = "No saved destinations yet",
                                message = "Tap the bookmark icon on a destination to save it."
                            )
                        } else {
                            SpotListScreen(savedSpots)
                        }
                    }
                }

                "Find Tour Guides" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        EmptyPage(
                            title = "Find Local Guides",
                            message = "Discover local tour guides in Nasugbu. This feature is coming soon."
                        )
                    }
                }

                "My Bookings" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        EmptyPage(
                            title = "My Visits",
                            message = "Your tour bookings and visits will appear here."
                        )
                    }
                }

                "About Us" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        EmptyPage(
                            title = "About Nasugbu Roots",
                            message = "Preserving our roots, one journey at a time."
                        )
                    }
                }

                else -> {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {

                        item {
                            Hero(
                                onStartExploring = {
                                    exploreOverride = null
                                    currentScreen = "Explore Heritage Sites"
                                }
                            )
                        }

                        item {
                            SearchField(
                                value = searchQuery,
                                onValueChange = {
                                    searchQuery = it
                                },
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .offset(y = (-18).dp)
                            )
                        }

                        item {
                            QuickActions(
                                onNearby = {
                                    currentScreen = "Explore Map"
                                },
                                onGuides = {
                                    currentScreen = "Find Tour Guides"
                                },
                                onTrails = {
                                    exploreOverride = sampleSpots.filter {
                                        it.category.contains(
                                            "Hiking",
                                            ignoreCase = true
                                        )
                                    }
                                    currentScreen = "Explore Heritage Sites"
                                },
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .offset(y = (-4).dp)
                            )
                        }

                        item {
                            SectionTitle(
                                onSeeAll = {
                                    exploreOverride = visibleSpots
                                    currentScreen = "Explore Heritage Sites"
                                },
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    top = 22.dp
                                )
                            )
                        }

                        item {
                            CategoryChips(
                                categories = categories,
                                selected = selectedCategory,
                                onSelected = {
                                    selectedCategory = it
                                },
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }

                        item {
                            if (visibleSpots.isEmpty()) {
                                EmptySearch(
                                    modifier = Modifier.padding(20.dp)
                                )
                            } else {
                                PlaceCarousel(
                                    spots = visibleSpots,
                                    savedSpotIds = savedSpotIds,
                                    onSave = { id ->
                                        savedSpotIds =
                                            if (id in savedSpotIds) {
                                                savedSpotIds - id
                                            } else {
                                                savedSpotIds + id
                                            }
                                    },
                                    modifier = Modifier.padding(top = 14.dp)
                                )
                            }
                        }

                        item {
                            StoryOfTheWeek(
                                Modifier.padding(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BrandTopBar(
    onMenuClick: () -> Unit
) {
    Surface(color = Cream) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Open menu",
                    tint = Forest
                )
            }

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(
                        RoundedCornerShape(
                            13.dp, 13.dp, 13.dp, 4.dp
                        )
                    )
                    .background(Forest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Eco,
                    contentDescription = "Nasugbu Roots logo",
                    tint = Gold,
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(Modifier.width(10.dp))

            Column {
                Text(
                    "Nasugbu Roots",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = DeepForest
                )

                Text(
                    "STORIES LIVE HERE",
                    color = Muted,
                    fontSize = 9.sp,
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(Modifier.weight(1f))

            Surface(
                shape = CircleShape,
                color = Color(0xFFE8DCB9)
            ) {
                Box(
                    modifier = Modifier.size(38.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "NR",
                        color = Forest,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun Hero(
    onStartExploring: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
            .background(Forest)
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1735440135578-48eb4541db80?auto=format&fit=crop&w=1200&q=88",
            contentDescription = "Nasugbu coastal landscape",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0x22071B14),
                            Color(0xE0142E25)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 40.dp
                )
        ) {
            Text(
                "WELCOME TO NASUGBU",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.8.sp
            )

            Text(
                "Walk into\nour stories.",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                "Discover the places, people, and memories that shaped our home.",
                color = Color.White.copy(alpha = 0.88f),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 18.dp)
                    .widthIn(max = 320.dp)
            )

            Button(
                onClick = onStartExploring,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = DeepForest
                ),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(
                    horizontal = 18.dp,
                    vertical = 12.dp
                )
            ) {
                Icon(
                    Icons.Outlined.Map,
                    contentDescription = null,
                    modifier = Modifier.size(19.dp)
                )

                Spacer(Modifier.width(9.dp))

                Text(
                    "Start exploring",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                "Search places and stories",
                color = Color(0xFF91A09A),
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = null,
                tint = Muted
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(17.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                10.dp,
                RoundedCornerShape(17.dp)
            )
    )
}

@Composable
private fun QuickActions(
    onNearby: () -> Unit,
    onGuides: () -> Unit,
    onTrails: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        QuickAction(
            Icons.Outlined.NearMe,
            "Nearby",
            "Explore map",
            Color(0xFFE7F0E8),
            Forest,
            onNearby,
            Modifier.weight(1f)
        )

        QuickAction(
            Icons.Outlined.PersonPinCircle,
            "Local guides",
            "Meet a local",
            Color(0xFFFBECD1),
            Color(0xFF9B611D),
            onGuides,
            Modifier.weight(1f)
        )

        QuickAction(
            Icons.Outlined.Route,
            "Trails",
            "Curated walks",
            Color(0xFFECEAF6),
            Color(0xFF5C598B),
            onTrails,
            Modifier.weight(1f)
        )
    }
}

@Composable
private fun QuickAction(
    icon: ImageVector,
    title: String,
    note: String,
    iconBackground: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(17.dp),
        color = Color.White,
        border = BorderStroke(
            1.dp,
            Color(0xFFE5E8DF)
        )
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 13.dp,
                horizontal = 5.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(21.dp)
                )
            }

            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 7.dp),
                maxLines = 1
            )

            Text(
                note,
                color = Muted,
                fontSize = 9.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun SectionTitle(
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(
                "YOUR NEXT STORY",
                color = Color(0xFFB57B30),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.6.sp
            )

            Text(
                "Explore Nasugbu",
                style = MaterialTheme.typography.headlineSmall,
                color = DeepForest
            )
        }

        Spacer(Modifier.weight(1f))

        TextButton(onClick = onSeeAll) {
            Text(
                "See all",
                color = Forest,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = Forest,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun CategoryChips(
    categories: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = {
                    onSelected(category)
                },
                label = {
                    Text(category, fontSize = 12.sp)
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Forest,
                    selectedLabelColor = Color.White,
                    containerColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
private fun PlaceCarousel(
    spots: List<TouristSpot>,
    savedSpotIds: Set<Int>,
    onSave: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        items(spots) { spot ->
            PlaceCard(
                spot = spot,
                isSaved = spot.placeId in savedSpotIds,
                onSave = {
                    onSave(spot.placeId)
                }
            )
        }
    }
}

@Composable
private fun PlaceCard(
    spot: TouristSpot,
    isSaved: Boolean,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier.width(218.dp),
        shape = RoundedCornerShape(19.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(142.dp)
                .background(Color(0xFFE7F0E8)),
            contentAlignment = Alignment.Center
        ) {
            if (spot.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = spot.imageUrl,
                    contentDescription = spot.placeName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = when {
                        spot.category.contains("Hiking", true) -> "⛰️"
                        spot.category.contains("Beach", true) -> "🏝️"
                        else -> "🌴"
                    },
                    fontSize = 45.sp
                )
            }

            IconButton(
                onClick = onSave,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(9.dp)
                    .size(36.dp)
                    .background(
                        Color.White.copy(alpha = 0.92f),
                        CircleShape
                    )
            ) {
                Icon(
                    if (isSaved) {
                        Icons.Filled.Bookmark
                    } else {
                        Icons.Outlined.BookmarkBorder
                    },
                    contentDescription = "Save ${spot.placeName}",
                    tint = if (isSaved) {
                        Color(0xFFC17C26)
                    } else {
                        Forest
                    },
                    modifier = Modifier.size(19.dp)
                )
            }
        }

        Column(Modifier.padding(14.dp)) {
            Text(
                spot.category.uppercase(),
                color = Color(0xFFBD7F2E),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Text(
                spot.placeName,
                fontFamily = FontFamily.Serif,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DeepForest,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                if (spot.entryFee > 0) {
                    "Entry fee: ₱${spot.entryFee}"
                } else {
                    "No entry fee listed"
                },
                color = Muted,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun EmptySearch(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.SearchOff,
            contentDescription = null,
            tint = Muted,
            modifier = Modifier.size(32.dp)
        )

        Text(
            "No destinations found",
            color = Muted,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun StoryOfTheWeek(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(19.dp),
        color = Color(0xFFE8EFE8)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF245847)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.AccountBalance,
                    contentDescription = null,
                    tint = Gold,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    "STORY OF THE WEEK",
                    color = Color(0xFFA16C2B),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    "Discover Nasugbu's stories",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = DeepForest
                )

                Text(
                    "Explore the town's local heritage",
                    color = Muted,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
private fun EmptyPage(
    title: String,
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.Explore,
            contentDescription = null,
            tint = Forest,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = DeepForest
        )

        Spacer(Modifier.height(8.dp))

        Text(
            message,
            color = Muted,
            fontSize = 14.sp
        )
    }
}
