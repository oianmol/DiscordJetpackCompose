package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatScreenContent(
  modifier: Modifier = Modifier,
  viewModel: ChatScreenViewModel,
  sheetState: ModalBottomSheetState,
  isReplyMode: MutableState<Boolean>,
  userName: State<String>
) {
  BoxWithConstraints(
    modifier = modifier
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
  ) {
    val constraints = decoupledConstraints()
    ConstraintLayout(constraints) {
      ChatMessages(
        modifier = Modifier
          .layoutId("chatMessages")
          .fillMaxSize(),
        userName = userName,
        viewModel = viewModel,
        bottomSheetState = sheetState
      )
      if (isReplyMode.value) {
        ReplyBanner(
          modifier = Modifier.layoutId("chatReplyBar"),
          userName = userName,
          onCloseClicked = {
            isReplyMode.value = false
          }
        )
      }
      ChatMessageEditor(
        modifier = Modifier.layoutId("chatMessageEditor"),
        userName = userName,
        isReplyMode = isReplyMode,
        viewModel = viewModel
      )
    }
  }
}

private fun decoupledConstraints(): ConstraintSet {
  return ConstraintSet {
    val chatMessages = createRefFor("chatMessages")
    val chatReplyBar = createRefFor("chatReplyBar")
    val chatMessageEditor = createRefFor("chatMessageEditor")

    constrain(chatMessages) {
      start.linkTo(parent.start)
      top.linkTo(parent.top)
      end.linkTo(parent.end)
      bottom.linkTo(chatMessageEditor.top, margin = 64.dp)
    }
    constrain(chatReplyBar) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(chatMessageEditor.top, margin = 0.dp)
    }
    constrain(chatMessageEditor) {
      start.linkTo(parent.start)
      end.linkTo(parent.end)
      bottom.linkTo(parent.bottom, margin = 8.dp)
    }
  }
}