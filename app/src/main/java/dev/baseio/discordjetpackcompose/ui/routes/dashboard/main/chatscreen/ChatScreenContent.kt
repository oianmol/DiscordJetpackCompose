package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.systemBarsPadding
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel

@Composable
fun ChatScreenContent(
  viewModel: ChatScreenViewModel,
  userName: State<String>
) {
  BoxWithConstraints(
    modifier = Modifier
      .systemBarsPadding(top = true)
      .imePadding()
  ) {
    val constraints = decoupledConstraints()
    ConstraintLayout(constraints) {
      ChatMessages(
        modifier = Modifier
          .layoutId("chatMessages")
          .fillMaxSize(),
        viewModel = viewModel
      )
      ChatMessageEditor(
        modifier = Modifier.layoutId("chatMessageEditor"),
        userName = userName,
        viewModel = viewModel
      )
    }
  }
}

private fun decoupledConstraints(): ConstraintSet {
  return ConstraintSet {
    val chatMessages = createRefFor("chatMessages")
    val chatMessageEditor = createRefFor("chatMessageEditor")

    constrain(chatMessages) {
      start.linkTo(parent.start)
      top.linkTo(parent.top)
      end.linkTo(parent.end)
      bottom.linkTo(chatMessageEditor.top, margin = 64.dp)
    }
    constrain(chatMessageEditor) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(parent.bottom, margin = 16.dp)
    }
  }
}