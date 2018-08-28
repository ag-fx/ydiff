package com.yutiliti.ydiff.view

import javafx.application.Platform
import tornadofx.*

class MainView : View("ydiff") {
    override val root = borderpane {
        top<MainMenu>()
        center {
            textarea("""
                Hello Everyone.
                how the hell are you.
            """.trimIndent()) {
                selectAll()
            }

        }
        bottom {
            hbox(spacing = 20) {
                button("Mazi") {
                    action {
                        primaryStage.isMaximized = true
                    }
                }
                button("Close") {
                    action {
                        Platform.exit()
                    }
                }
            }
        }

    }
}
