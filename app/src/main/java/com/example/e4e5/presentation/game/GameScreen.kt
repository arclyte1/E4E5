package com.example.e4e5.presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e4e5.domain.BoardThemeState
import com.example.e4e5.presentation.game.components.BoardComposable
import com.example.e4e5.presentation.game.components.PromotionDialog

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val theme = BoardThemeState.theme.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        if (viewModel.gameState.value.isPromotion) {
            PromotionDialog(
                theme = theme.value,
                playerSide = viewModel.gameState.value.playerSide,
                promotionSelect = viewModel::onPromotionSelected,
                undoPromotion = viewModel::undoPromotion,
            )
        }

        BoardComposable(
            theme = theme.value,
            board = viewModel.board.value,
            gameState = viewModel.gameState.value,
            onSquareClick = viewModel::onSquareClick,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
