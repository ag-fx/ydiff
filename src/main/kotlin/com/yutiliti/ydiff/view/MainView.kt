package com.yutiliti.ydiff.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Platform
import javafx.geometry.Orientation
import tornadofx.*

class MainView : View("ydiff") {

    init {
        // Setup to remember window position
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

            toolbar {
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.REFRESH))
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE))
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.UNDO))
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.EDIT))
                separator(Orientation.VERTICAL)
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT))
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT))
                separator(Orientation.VERTICAL)
                button(graphic = FontAwesomeIconView(FontAwesomeIcon.SEARCH))
            }
        }

        bottom {
            // May be status bar
        }
    }

    override fun onDock() {
        with(root) {
            center {
                add(DiffView())
            }
        }
    }
}
