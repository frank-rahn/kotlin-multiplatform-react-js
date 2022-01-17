import de.rahn.kotlin.multiplatform.tictactoe.History
import de.rahn.kotlin.multiplatform.tictactoe.SquareType
import de.rahn.kotlin.multiplatform.tictactoe.calculateWinner
import react.Props
import react.dom.attrs
import react.dom.button
import react.dom.div
import react.dom.events.MouseEvent
import react.dom.li
import react.dom.ol
import react.dom.onClick
import react.fc
import react.useState

val game = fc<Props> {
  val initialHistory by useState(arrayOf(History()))

  var xIsNext by useState(true)
  var stepNumber by useState(0)
  var history by useState(initialHistory)
  var showPlayerSelection by useState(true)

  fun handleClick(i: Int) {
    val newHistory = history.slice(0..stepNumber).toMutableList()
    val current = newHistory.last()
    val squares = current.squares.copyOf()

    if (calculateWinner(squares) !== null || squares[i] !== null) {
      return
    }

    squares[i] = if (xIsNext) SquareType.X else SquareType.O
    newHistory.add(History(squares))
    xIsNext = !xIsNext
    stepNumber = newHistory.size - 1
    history = newHistory.toTypedArray()
    showPlayerSelection = false
  }

  fun jumpTo(step: Int) {
    stepNumber = step
    xIsNext = step % 2 == 0
  }

  val current = history[stepNumber]
  val winner = calculateWinner(current.squares)

  var isStepLeft = true
  val moves = history.mapIndexed { move: Int, step: History ->
    isStepLeft = step.squares.any { it === null }
    val desc = if (move != 0) "Go to move #$move" else "Go to game start"
    Pair(move, desc)
  }

  val status = when {
    winner is SquareType -> "Winner: ${winner.name}"
    isStepLeft -> "Next player: ${if (xIsNext) SquareType.X else SquareType.O}"
    else -> "Nobody won :("
  }

  div(classes = "game") {
    if (showPlayerSelection) {
      div(classes = "players") {
        button {
          attrs {
            onClick = {
              xIsNext = true
              showPlayerSelection = false
            }

            +SquareType.X.name
          }
        }
        button {
          attrs {
            onClick = {
              xIsNext = false
              showPlayerSelection = false
            }

            +SquareType.O.name
          }
        }
      }
    }
    div(classes = "game-board") {
      child(board) {
        attrs {
          squares = current.squares
          onClick = { i -> handleClick(i) }
        }
      }
    }
    div(classes = "game-info") {
      div {
        +status
      }
      if (winner is SquareType || !isStepLeft) {
        button {
          attrs {
            onClick = {
              jumpTo(0)
              history = initialHistory
              showPlayerSelection = true
            }

            +"Start new game"
          }
        }
      }
      ol {
        for ((move, desc) in moves) {
          li {
            key = "$move"

            button {
              attrs {
                onClick = { jumpTo(move) }
              }

              +desc
            }
          }
        }
      }
    }
  }
}
