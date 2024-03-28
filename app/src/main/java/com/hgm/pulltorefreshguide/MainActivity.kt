package com.hgm.pulltorefreshguide

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hgm.pulltorefreshguide.ui.theme.PullToRefreshGuideTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                  PullToRefreshGuideTheme {
                        Surface(
                              modifier = Modifier.fillMaxSize(),
                              color = MaterialTheme.colorScheme.background
                        ) {
                              val items = remember {
                                    (1..100).map { "Item $it" }
                              }
                              var isRefreshing by remember {
                                    mutableStateOf(false)
                              }
                              val scope = rememberCoroutineScope()

                              Box(
                                    modifier = Modifier.fillMaxSize()
                              ) {
                                    PullToRefreshLazyColumn(
                                          items = items,
                                          content = { title ->
                                                Text(
                                                      text = title,
                                                      modifier = Modifier.padding(16.dp)
                                                )
                                          },
                                          isRefreshing = isRefreshing,
                                          onRefresh = {
                                                scope.launch {
                                                      // 模拟网络请求，刷新数据
                                                      delay(3000L)
                                                      isRefreshing = false
                                                }
                                                Log.i("TAG", "onRefresh: 刷新执行了")
                                          }
                                    )

                                    Button(
                                          onClick = { isRefreshing = true },
                                          modifier = Modifier.align(Alignment.BottomCenter)
                                    ) {
                                          Text(text = "Refresh")
                                    }
                              }
                        }
                  }
            }
      }
}
