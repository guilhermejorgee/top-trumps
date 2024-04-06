package com.gui.top.trumps.api.v1

import arrow.core.getOrElse
import com.gui.top.trumps.api.v1.request.*
import com.gui.top.trumps.api.v1.response.CategoryCreateResponse
import com.gui.top.trumps.api.v1.response.DeckCreateResponse
import com.gui.top.trumps.api.v1.response.RoomCreateResponse
import com.gui.top.trumps.api.v1.response.UserCreateResponse
import com.gui.top.trumps.core.game.application.CategoryService
import com.gui.top.trumps.core.game.application.DeckService
import com.gui.top.trumps.core.game.application.RoomService
import com.gui.top.trumps.core.game.application.UserService
import com.gui.top.trumps.core.game.application.error.ApplicationError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/top-trumps/v1")
class GameController(
    private val roomService: RoomService,
    private val userService: UserService,
    private val deckService: DeckService,
    private val categoryService: CategoryService
) {

    @PostMapping("/users")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserCreateResponse>{
        val user = userService.createUser(request.name)
            .getOrElse {
                return responseError(it)
            }

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id).toUri()

        return ResponseEntity.created(uri).body(UserCreateResponse(user.id, user.name))
    }

    @PostMapping("/rooms")
    fun createRoom(@RequestBody request: RoomCreateRequest): ResponseEntity<RoomCreateResponse>{
        val room = roomService.createRoom(request.playerId, request.slots).getOrElse {
            return responseError(it)
        }
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(room.id).toUri()
        return ResponseEntity.created(uri).body(RoomCreateResponse(room.id, room.pass, room.slots))
    }

    @PatchMapping("/rooms/{roomPass}/access")
    fun accessRoom(
        @PathVariable roomPass: String,
        @RequestBody request: RoomAccessRequest
    ): ResponseEntity<Any>{
        roomService.accessRoom(request.playerId, roomPass).getOrElse {
            return responseError(it)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/decks")
    fun createDeck(@RequestBody request: CreateDeckRequest): ResponseEntity<DeckCreateResponse>{
        val deck = deckService.createDeck(request.name, request.categoryId, request.cards).getOrElse {
            return responseError(it)
        }

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(deck.id).toUri()
        return ResponseEntity.created(uri).body(DeckCreateResponse(deck.id, deck.name, deck.category.name))
    }

    @PostMapping("/categories")
    fun createCategory(@RequestBody request: CreateCategoryRequest): ResponseEntity<CategoryCreateResponse> {
        val category = categoryService.createCategory(request.name, request.description)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.id).toUri()
        return ResponseEntity.created(uri).body(CategoryCreateResponse(category.id, category.name, category.description))
    }


    fun <T> responseError(error: ApplicationError): ResponseEntity<T>{
        return when (error){
            is ApplicationError.EntityNotFound -> ResponseEntity.notFound().build()
            is ApplicationError.UnexpectedError -> ResponseEntity.badRequest().build()
            is ApplicationError.UnprocessableEntity -> ResponseEntity.unprocessableEntity().build()
        }
    }

}