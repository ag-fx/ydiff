package com.yutiliti.ydiff.view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import tornadofx.*
import java.io.File
import java.nio.file.Files

class DiffView(val file1: File, val file2: File) : Fragment() {

    val controller: DiffController by inject()
    val leftArea = CodeArea().apply {
        paragraphGraphicFactory = LineNumberFactory.get(this)
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS
    }
    val rightArea = CodeArea().apply {
        paragraphGraphicFactory = LineNumberFactory.get(this)
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS
    }

    init {
        task {
            Files.readAllLines(file1.toPath()).joinToString("\n")
        } success {
            leftArea.replaceText(it)
        }
        task {
            Files.readAllLines(file2.toPath()).joinToString("\n")
        } success {
            rightArea.replaceText(it)
        }
    }

    override val root = vbox {
        hbox(10) {
            label(controller.diffStat)
            separator { }
            label()
        }
        hbox {
            add(leftArea)
            add(rightArea)
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS

        }
    }

}


class DiffController : Controller() {
    val diffStat = SimpleStringProperty("1 diffs (ignore line ending differences)")

}