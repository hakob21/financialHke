package com.hakob.financialhke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
////            App()
//            TabNavigator(HomeTab) {
//                Scaffold(
//                    content = {
//                        it.calculateLeftPadding(LayoutDirection.Ltr)
//                        Navigator(SecondScreen())
//                    },
//                    bottomBar = {
//                        BottomNavigation {
//                            TabNavigationItem(HomeTab)
//                            TabNavigationItem(FavoritesTab)
////                            TabNavigationItem(ProfileTab)
//                        }
//                    }
//                )
//            }
//            TabNavigator(FavoritesTab) {
//                Scaffold(
//                    content = {
//                        it.calculateLeftPadding(LayoutDirection.Ltr)
//                        Navigator(HomeScreen())
//                    },
//                    bottomBar = {
//                        BottomNavigation {
//                            TabNavigationItem(HomeTab)
//                            TabNavigationItem(FavoritesTab)
////                            TabNavigationItem(ProfileTab)
//                        }
//                    }
//                )
//            }

//            Navigator(SecondScreen())

            Content()
        }
    }
}

//@Composable
//private fun RowScope.TabNavigationItem(tab: Tab) {
//    val tabNavigator = LocalTabNavigator.current
//
//    BottomNavigationItem(
//        selected = tabNavigator.current == tab,
//        onClick = { tabNavigator.current = tab },
//        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title!!) }
//    )
//}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}


