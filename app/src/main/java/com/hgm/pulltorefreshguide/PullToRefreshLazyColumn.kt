package com.hgm.pulltorefreshguide


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp


/**
 * @author：HGM
 * @created：2024/3/27 0027
 * @description：通用的下拉刷新列表组件
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyColumn(
      items: List<T>,
      isRefreshing: Boolean,
      onRefresh: () -> Unit,
      modifier: Modifier = Modifier,
      content: @Composable (T) -> Unit,
      lazyListState: LazyListState = rememberLazyListState()
) {
      val pullToRefreshState = rememberPullToRefreshState()

      // 我们需要把列表放入任何一个容器当中
      Box(
            modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
      ) {
            LazyColumn(
                  state = lazyListState,
                  contentPadding = PaddingValues(8.dp),
                  modifier = Modifier.fillMaxSize(),
                  verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                  items(items) {
                        content(it)
                  }
            }

            if (pullToRefreshState.isRefreshing) {
                  LaunchedEffect(true) {
                        onRefresh()
                  }
            }

            LaunchedEffect(isRefreshing) {
                  if (isRefreshing) {
                        pullToRefreshState.startRefresh()
                        Log.i("TAG", "startRefresh: 开始刷新")
                  } else {
                        pullToRefreshState.endRefresh()
                        Log.i("TAG", "endRefresh: 结束刷新")
                  }
            }

            PullToRefreshContainer(
                  state = pullToRefreshState,
                  modifier = Modifier.align(Alignment.TopCenter),
                  containerColor = MaterialTheme.colorScheme.primary,
                  contentColor = MaterialTheme.colorScheme.onPrimary
            )
      }
}
