import de.rahn.kotlin.multiplatform.tictactoe.SquareType
import react.Props

external interface SquareProps : Props {
  var value: SquareType?
  var onClick: () -> Unit
}

external interface BoardProps : Props {
  var squares: Array<SquareType?>
  var onClick: (Int) -> Unit
}
