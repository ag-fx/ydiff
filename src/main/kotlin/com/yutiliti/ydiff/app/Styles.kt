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

        private val bgColor = c(249, 249, 250)
        private val bdrColor = c(152, 152, 154)
    }

    init {
        val textHighlight = c("#ffff79")

        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        val thinBorder = mixin {
            borderColor += box(Color.BLACK)
            borderStyle += BorderStrokeStyle.SOLID
        }
        change {
            backgroundColor += textHighlight
        }
        change_sole {
            borderWidth += box(1.px, 0.px, 1.0.px, 0.px)
            +thinBorder
        }
        change_top {
            borderWidth += box(1.px, 0.px, 0.0.px, 0.px)
            +thinBorder
        }
        change_bottom {
            borderWidth += box(0.px, 0.px, 1.px, 0.px)
            +thinBorder
        }

        menuBar {
            borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, bdrColor, Color.TRANSPARENT)
        }
        menuItem {
            prefWidth = 100.px
        }
        contextMenu {
            padding = box(0.px)
        }

        toolBar {
            padding = box(1.px, 5.px)
            spacing = 1.px
            borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, bdrColor, Color.TRANSPARENT)
            button {
                prefWidth = 33.px
                backgroundColor += c(244, 244, 244, 0.0)
                padding = box(4.px)
            }
            button and hover {
                backgroundColor += Color.WHITE
            }
            button and pressed {
                backgroundColor += c(224, 224, 225)
            }
        }
    }
}