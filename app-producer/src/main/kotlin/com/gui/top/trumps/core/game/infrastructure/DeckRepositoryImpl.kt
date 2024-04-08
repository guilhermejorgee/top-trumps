package com.gui.top.trumps.core.game.infrastructure

import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.infrastructure.entities.CardEntity
import com.gui.top.trumps.core.game.infrastructure.entities.DeckEntity
import com.gui.top.trumps.core.game.domain.Deck
import com.gui.top.trumps.core.game.infrastructure.mapper.DeckMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.interceptor.TransactionAspectSupport
import java.lang.RuntimeException
import java.util.*

@Repository
class DeckRepositoryImpl(
    private val repositoryJpa: DeckSpringRepository,
    private val repositoryMongoDb: DeckMongoDbRepository
): DeckRepository{

    override fun findById(id: String): Optional<Deck> {
        val deckEntity = repositoryJpa.findById(id)
        if(deckEntity.isEmpty){
            return Optional.empty()
        }
        val cards = repositoryMongoDb.findByDeckId(id)
        if(cards.isEmpty()){
            throw RuntimeException()
        }
        val deck = DeckMapper.fromEntityToDomain(deckEntity.get(), cards)

        return Optional.of(deck);

    }

    override fun save(deck: Deck): Deck {
        try {
            val deckEntities = DeckMapper.fromDomainToEntities(deck)
            repositoryJpa.save(deckEntities.deck)
            if(deckEntities.card.isNotEmpty()){
                repositoryMongoDb.saveAll(deckEntities.card)
            }
            return deck
        }catch (ex: Exception){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw ex
        }
    }

}



interface DeckMongoDbRepository : MongoRepository<CardEntity, String> {
    fun findByDeckId(deckId: String): Set<CardEntity>
}

interface DeckSpringRepository : JpaRepository<DeckEntity, String> {}