import de.rahn.kotlin.multiplatform.tictactoe.label
import react.dom.attrs
import react.dom.button
import react.dom.onClick
import react.fc

val square = fc<SquareProps> { props ->
  button(classes = "square") {
    attrs {
      onClick = { props.onClick() }
    }

    +props.value.label()
  }
}
