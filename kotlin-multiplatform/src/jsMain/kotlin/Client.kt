import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.render

fun main2() {
  window.onload = {
    render(document.getElementById("root")!!) {
      child(Welcome::class) {
        attrs {
          name = "Kotlin/JS"
        }
      }
    }
  }
}
