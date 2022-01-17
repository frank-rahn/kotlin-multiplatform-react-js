package de.rahn.kotlin.multiplatform.tictactoe

data class History(
  val squares: Array<SquareType?> = Array(9) { null },
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is History) return false

    if (!squares.contentEquals(other.squares)) return false

    return true
  }

  override fun hashCode(): Int {
    return squares.contentHashCode()
  }
}
