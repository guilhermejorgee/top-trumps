package com.gui.top.trumps.core.common.application

interface UnitOfWork {
    fun <T> runTransaction(operation: () -> T): T
}