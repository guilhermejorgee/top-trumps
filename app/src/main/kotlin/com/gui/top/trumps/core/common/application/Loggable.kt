package com.gui.top.trumps.core.common.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Loggable {
    val logger: Logger
        get() = logger()
}

private fun <T : Loggable> T.logger() = LoggerFactory.getLogger(javaClass)