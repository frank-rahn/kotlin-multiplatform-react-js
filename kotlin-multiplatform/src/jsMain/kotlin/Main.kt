import kotlinext.js.require
import kotlinx.browser.document
import react.dom.render

/**
 * https://blog.plan99.net/reacts-tictactoe-tutorial-in-kotlin-javafx-715c75a947d2
 * https://github.com/JetBrains/kotlin-wrappers/blob/master/examples/src/main/kotlin/example/TicTacToe.kt
 * https://github.com/kotlin-hands-on/web-app-react-kotlin-js-gradle/tree/finished/src/main/kotlin
 */
fun main() {
  require("./index.css")

  val element = document.getElementById("root") ?: error("Element mit der Id 'root' nicht gefunden")
  render(element) { child(game) }
}
