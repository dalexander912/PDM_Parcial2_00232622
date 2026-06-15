package com.pdmcourse2026.basictemplate

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdmcourse2026.basictemplate.screens.options.OptionsScreen
import com.pdmcourse2026.basictemplate.screens.questions.QuestionScreen

@Composable
fun RankeUca() {
  val backStack = rememberNavBackStack(Routes.Questions)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<Routes.Questions> {
        QuestionScreen(
          onQuestionClick = { questionId -> backStack.add(Routes.Options(questionId)) }
        )
      }
      entry<Routes.Options> { key ->
        OptionsScreen(
          questionId = key.questionId,
          onBack = { backStack.removeLastOrNull() }
        )
      }
    }
  )
}