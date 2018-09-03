package com.yutiliti.ydiff.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Platform
import javafx.scene.control.ButtonBar
import tornadofx.*
import java.io.File

class MainView : View("ydiff") {

    init {
        runLater {
            preferences("ydiff") {
                val maximized = getBoolean("maximized", false)
                if (!maximized) {
                    primaryStage.width = getDouble("width", 200.0)
                    primaryStage.height = getDouble("height", 200.0)
                    primaryStage.centerOnScreen()
                }
                primaryStage.isMaximized = maximized
            }
            primaryStage.widthProperty().addListener { _, _, n ->
                preferences("ydiff") {
                    putDouble("width", n.toDouble())
                }
            }
            primaryStage.heightProperty().addListener { _, _, n ->
                preferences("ydiff") {
                    putDouble("height", n.toDouble())
                }
            }
            primaryStage.maximizedProperty().addListener { _, _, n ->
                preferences("ydiff") {
                    putBoolean("maximized", n)
                }
            }
        }
    }

    override val root = borderpane {
        top = vbox {
            menubar {
                menu("File") {
                    item("New")
                    item("Exit").action {
                        Platform.exit()
                    }
                }
                menu("Edit")
                menu("View")
                menu("Search")
                menu("Help")
            }

            buttonbar {
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.REFRESH))
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE))
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.UNDO))
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT))
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT))
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT))
                button(type = ButtonBar.ButtonData.LEFT, graphic = FontAwesomeIconView(FontAwesomeIcon.SEARCH))
            }
        }

        bottom {
            // May be status bar
        }


    }

    override fun onDock() {
        with(root) {
            var f1 = File("/Users/sixface/temp/code.right")
            var f2 = File("/Users/sixface/temp/code.left")
            val dv = DiffView(f1, f2)
            center {
                add(dv)
            }
        }
    }
}
