package com.yutiliti.ydiff.app

import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val change by cssclass()
        val change_top by cssclass()
        val change_bottom by cssclass()
        val change_sole by cssclass()
    }

    init {
        val textHighlight = c("#ffff79")

        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        change {
            backgroundColor += textHighlight
        }
        change_sole {
            borderWidth += box(1.px, 0.px, 1.0.px, 0.px)
            borderColor += box(Color.BLACK)
            borderStyle += BorderStrokeStyle.SOLID
        }
        change_top {
            borderWidth += box(1.px, 0.px, 0.0.px, 0.px)
            borderColor += box(Color.BLACK)
            borderStyle += BorderStrokeStyle.SOLID
        }
        change_bottom {
            borderWidth += box(0.px, 0.px, 1.px, 0.px)
            borderColor += box(Color.BLACK)
            borderStyle += BorderStrokeStyle.SOLID
        }
    }
}