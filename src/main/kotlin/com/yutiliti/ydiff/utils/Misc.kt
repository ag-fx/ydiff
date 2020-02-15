package com.yutiliti.ydiff.utils

import tornadofx.*
import java.text.SimpleDateFormat
import java.util.*

val sdfLog = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
fun log(msg: Any) {
    val caller = Thread.currentThread().stackTrace[2]
    runAsync {
        println("${sdfLog.format(Date())} - ${caller.methodName}() ${caller.fileName}:${caller.lineNumber} - $msg")
    }
}