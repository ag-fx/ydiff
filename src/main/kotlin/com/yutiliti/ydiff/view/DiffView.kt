package com.yutiliti.ydiff.view

import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Priority
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import tornadofx.*
import java.io.File
import java.nio.file.Files


class DiffView(private val file1: File, private val file2: File) : Fragment() {

    private val controller: DiffController by inject()
    private val leftArea = CodeArea()
    private val rightArea = CodeArea()
    private lateinit var content1: List<String>
    private lateinit var content2: List<String>
    private lateinit var style1: StyleSpans<Collection<String>>
    private lateinit var style2: StyleSpans<Collection<String>>

    init {
        runAsync {
            content1 = Files.readAllLines(file1.toPath())
            content2 = Files.readAllLines(file2.toPath())

            val patch = DiffUtils.diff<String>(content1, content2)

            var start1 = 0
            var start2 = 0
            var pos1 = 0
            var pos2 = 0
            var end1 = 0
            var end2 = 0
            val spansBuilder1 = StyleSpansBuilder<Collection<String>>()
            val spansBuilder2 = StyleSpansBuilder<Collection<String>>()
            for (delta in patch.deltas) {

                if (delta.type == DeltaType.CHANGE || delta.type == DeltaType.DELETE) {
                    for (i in pos1 until delta.original.position) {
                        start1 += content1[i].length
                    }
                    spansBuilder1.add(emptyList(), start1 - end1)
                    end1 = start1 + content1[pos1].length
                    spansBuilder1.add(listOf("change"), end1 - start1)
                    pos1 = delta.original.position


                }
                if (delta.type == DeltaType.CHANGE || delta.type == DeltaType.INSERT) {
                    for (i in 0 until delta.revised.position) {
                        start2 += content2[i].length
                    }
                    spansBuilder2.add(emptyList(), start2 - end2)
                    end2 = start2 + content2[pos2].length
                    spansBuilder2.add(listOf("change"), end2 - start2)
                    pos2 = delta.revised.position
                }

            }
            spansBuilder1.add(emptyList(), content1.joinToString("\n").length - end1)
            spansBuilder2.add(emptyList(), content2.joinToString("\n").length - end2)
            style1 = spansBuilder1.create()
            style2 = spansBuilder2.create()
            println(style1)
        } success {
            leftArea.replaceText(content1.joinToString("\n"))
            rightArea.replaceText(content2.joinToString("\n"))
            leftArea.setStyleSpans(0, style1)
            rightArea.setStyleSpans(0, style2)
            val patch = DiffUtils.diff<String>(content1, content2)
            for (delta in patch.deltas) {
                if (delta.type == DeltaType.CHANGE) {
                    leftArea.setStyle(delta.original.position, listOf("change"))
                }
            }
            leftArea.replaceText(content1.joinToString("\n"))
            rightArea.replaceText(content2.joinToString("\n"))
        }
    }

    override val root = vbox {
        hbox(10) {
            label(controller.diffStat)
            separator { }
            label()
        }
        hbox {
            leftArea.apply {
                paragraphGraphicFactory = LineNumberFactory.get(this)
                isEditable = false
                stylesheets.add(javaClass.getResource("/styles.css").toExternalForm())
            }
            rightArea.apply {
                paragraphGraphicFactory = LineNumberFactory.get(this)
                isEditable = false
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

}


class DiffController : Controller() {
    val diffStat = SimpleStringProperty("1 diffs (ignore line ending differences)")

}