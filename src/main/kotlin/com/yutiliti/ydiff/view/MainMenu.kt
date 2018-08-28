package com.yutiliti.ydiff.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.scene.control.ButtonBar
import tornadofx.*

class MainMenu : View() {
    override val root = vbox {
        menubar {
            menu("File") {
                item("New")
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
}