package com.yutiliti.ydiff.view

import com.github.difflib.patch.DeltaType
import com.yutiliti.ydiff.app.MyApp
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.StyleClassedTextArea
import tornadofx.*


class DiffView : Fragment() {

    private val controller: DiffController by inject()
    private val leftArea = StyleClassedTextArea()
    private val rightArea = StyleClassedTextArea()

    override val root = vbox {

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

        hbox(10) {
            vboxConstraints {
                margin = tornadofx.insets(5)
                alignment = Pos.CENTER_RIGHT
            }
            label(controller.diffStat)
            separator(Orientation.VERTICAL)
            label("UTF-8")
        }
    }

    init {
        leftArea.replaceText(MyApp.data.leftValue)
        rightArea.replaceText(MyApp.data.rightValue)
        calculateStyles()
    }

    private fun calculateStyles() {
        for (delta in MyApp.data.patch.deltas) {
            if (delta.type == DeltaType.CHANGE || delta.type == DeltaType.DELETE) {
                if (delta.original.lines.size == 1) {
                    leftArea.setParagraphStyle(delta.original.position, listOf("change", "change_sole"))
                } else {
                    leftArea.setParagraphStyle(delta.original.position, listOf("change", "change_top"))
                    for (i in 1 until delta.original.lines.size - 1) {
                        leftArea.setParagraphStyle(delta.original.position + i, listOf("change"))
                    }
                    leftArea.setParagraphStyle(delta.original.position + delta.original.lines.size - 1,
                            listOf("change", "change_bottom"))
                }
            }
            if (delta.type == DeltaType.CHANGE || delta.type == DeltaType.INSERT) {
                if (delta.revised.lines.size == 1) {
                    rightArea.setParagraphStyle(delta.revised.position, listOf("change", "change_sole"))
                } else {
                    rightArea.setParagraphStyle(delta.revised.position, listOf("change", "change_top"))
                    for (i in 1 until delta.revised.lines.size - 1) {
                        rightArea.setParagraphStyle(delta.revised.position + i, listOf("change"))
                    }
                    rightArea.setParagraphStyle(delta.revised.position + delta.revised.lines.size - 1,
                            listOf("change", "change_bottom"))
                }
            }
        }
        controller.diffStat.value = "${MyApp.data.patch.deltas.size} diffs"
    }
}


class DiffController : Controller() {
    val diffStat = SimpleStringProperty("1 diffs (ignore line ending differences)")
}