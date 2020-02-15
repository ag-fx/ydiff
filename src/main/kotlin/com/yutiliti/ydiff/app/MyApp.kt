package com.yutiliti.ydiff.app

import com.yutiliti.ydiff.utils.log
import com.yutiliti.ydiff.view.MainView
import tornadofx.*
import java.io.File

class MyApp : App(MainView::class, Styles::class) {

    companion object {
        lateinit var data: DataHolder
    }

    override fun init() {
        super.init()
        val args = parameters.raw
        data = if (args.size == 2) {
            val f1 = File(args[0])
            if (!f1.canRead()) {
                log("File ${args[0]} does not exist or cannot read.")
            }
            val f2 = File(args[1])
            if (!f2.canRead()) {
                log("File ${args[1]} does not exist or cannot read.")
            }
            log("Diffing between $f1 and $f2")
            DataHolder(f1, f2)
        } else DataHolder()
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}