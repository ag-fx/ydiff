package com.yutiliti.ydiff.view

import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import tornadofx.*
import java.io.File
import java.nio.file.Files


class DiffView(private val file1: File, private val file2: File) : Fragment() {

    private val controller: DiffController by inject()
    private val leftArea = CodeArea()
    private val rightArea = CodeArea()
    private lateinit var content1: List<String>
    private lateinit var content2: List<String>

    override val root = vbox {
        hbox(10) {
            label(controller.diffStat)
            separator(Orientation.VERTICAL)
            label("UTF-8")
        }
        hbox {
            leftArea.apply {
                paragraphGraphicFactory = LineNumberFactory.get(this)
            }
            rightArea.apply {
                paragraphGraphicFactory = LineNumberFactory.get(this)
            }
            val leftPane = VirtualizedScrollPane(leftArea).apply {
                vgrow = Priority.ALWAYS
                hgrow = Priority.ALWAYS
            }
            val rightPane = VirtualizedScrollPane(rightArea).apply {
                vgrow = Priority.ALWAYS
                hgrow = Priority.ALWAYS
            }
            leftPane.estimatedScrollXProperty().bindBidirectional(rightPane.estimatedScrollXProperty())
            leftPane.estimatedScrollYProperty().bindBidirectional(rightPane.estimatedScrollYProperty())

            add(leftPane)
            add(rightPane)
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }
    }

    init {
        runAsync {
            content1 = Files.readAllLines(file1.toPath())
            content2 = Files.readAllLines(file2.toPath())
        } success {
            leftArea.replaceText(content1.joinToString("\n"))
            rightArea.replaceText(content2.joinToString("\n"))
            calculateStyles()
        }
    }

    private fun calculateStyles() {
        val patch = DiffUtils.diff<String>(content1, content2)
        for (delta in patch.deltas) {
            if (delta.type == DeltaType.CHANGE || delta.type == DeltaType.DELETE) {
                for (i in 0 until delta.original.lines.size)
                    leftArea.setParagraphStyle(delta.original.position + i, listOf("change"))
            }
            if (delta.type == DeltaType.CHANGE || delta.type == DeltaType.INSERT) {
                for (i in 0 until delta.revised.lines.size)
                    rightArea.setParagraphStyle(delta.revised.position + i, listOf("change"))
            }
        }
    }
}


class DiffController : Controller() {
    val diffStat = SimpleStringProperty("1 diffs (ignore line ending differences)")

}