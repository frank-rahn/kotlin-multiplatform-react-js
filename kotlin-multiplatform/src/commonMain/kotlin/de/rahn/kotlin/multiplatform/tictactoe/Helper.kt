package de.rahn.kotlin.multiplatform.tictactoe

fun calculateWinner(squares: Array<SquareType?>): SquareType? {
  val lines = arrayOf(
    Triple(0, 1, 2),
    Triple(3, 4, 5),
    Triple(6, 7, 8),
    Triple(0, 3, 6),
    Triple(1, 4, 7),
    Triple(2, 5, 8),
    Triple(0, 4, 8),
    Triple(2, 4, 6),
  )

  for ((a, b, c) in lines) {
    if (squares[a] !== null && squares[a] === squares[b] && squares[a] === squares[c]) {
      return squares[a]
    }
  }

  return null
}
