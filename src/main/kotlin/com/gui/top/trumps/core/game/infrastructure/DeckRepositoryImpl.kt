package com.gui.top.trumps.core.game.infrastructure

import com.gui.top.trumps.core.game.application.repository.DeckRepository
import com.gui.top.trumps.core.game.infrastructure.entities.CardEntity
import com.gui.top.trumps.core.game.infrastructure.entities.DeckEntity
import com.gui.top.trumps.core.game.domain.Deck
import com.gui.top.trumps.core.game.infrastructure.mapper.DeckMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DeckRepositoryImpl(
    private val repositoryJpa: DeckSpringRepository,
    private val repositoryMongoDb: MongoDbRepository
): DeckRepository{

    override fun findById(id: String): Optional<Deck> {
        val deckEntity = repositoryJpa.findById(id)
        if(deckEntity.isEmpty){
            return Optional.empty()
        }
        val cards = repositoryMongoDb.findByDeckId(id)
        val deck = DeckMapper.fromEntityToDomain(deckEntity.get(), cards)

        return Optional.of(deck);

    }

    override fun save(deck: Deck): Deck {
        val deckEntities = DeckMapper.fromDomainToEntities(deck)
        repositoryJpa.save(deckEntities.deck)
        if(deckEntities.card.isNotEmpty()){
            repositoryMongoDb.saveAll(deckEntities.card)
        }
        return deck
    }

}



interface MongoDbRepository : MongoRepository<CardEntity, String> {
    fun findByDeckId(deckId: String): Set<CardEntity>
}

interface DeckSpringRepository : JpaRepository<DeckEntity, String>, JpaSpecificationExecutor<DeckEntity> {}