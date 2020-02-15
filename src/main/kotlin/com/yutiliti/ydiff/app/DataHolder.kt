package com.yutiliti.ydiff.app


import com.github.difflib.DiffUtils
import com.github.difflib.patch.Patch
import java.io.File

class DataHolder(left: File?, right: File?) {

    constructor() : this(null, null)

    var leftValue = String(left?.readBytes() ?: "".toByteArray())
    var rightValue = String(right?.readBytes() ?: "".toByteArray())
    var patch: Patch<String>

    init {
        patch = DiffUtils.diff(leftValue, rightValue)
    }
}