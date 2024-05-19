package com.gui.top.trumps.core.game.infrastructure

import com.gui.top.trumps.core.common.application.UnitOfWork
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UnitOfWorkORM : UnitOfWork {
    @Transactional
    override fun <T> runTransaction(operation: () -> T): T {
        return operation()
    }
}