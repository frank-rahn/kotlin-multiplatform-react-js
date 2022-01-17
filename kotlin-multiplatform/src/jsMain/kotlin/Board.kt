import kotlinx.html.DIV
import react.dom.RDOMBuilder
import react.dom.div
import react.fc

val board = fc<BoardProps> { props ->
  div {
    div(classes = "board-row") {
      for (i in 0..2) renderSquare(props, i)
    }
    div(classes = "board-row") {
      for (i in 3..5) renderSquare(props, i)
    }
    div(classes = "board-row") {
      for (i in 6..8) renderSquare(props, i)
    }
  }
}

private fun RDOMBuilder<DIV>.renderSquare(props: BoardProps, i: Int) {
  child(square) {
    attrs {
      value = props.squares[i]
      onClick = { props.onClick(i) }
    }
  }
}
