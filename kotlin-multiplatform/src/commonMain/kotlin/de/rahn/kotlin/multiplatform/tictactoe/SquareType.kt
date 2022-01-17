package de.rahn.kotlin.multiplatform.tictactoe

enum class SquareType { X, O }

fun SquareType?.label() = when (this) {
  SquareType.X -> "X"
  SquareType.O -> "O"
  null -> ""
}