package com.gui.top.trumps.api.v1

import arrow.core.getOrElse
import com.gui.top.trumps.api.v1.request.RoomAccessRequest
import com.gui.top.trumps.api.v1.request.RoomCreateRequest
import com.gui.top.trumps.api.v1.response.RoomCreateResponse
import com.gui.top.trumps.core.game.application.RoomService
import com.gui.top.trumps.core.game.application.error.ApplicationError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/top-trumps/v1")
class GameController(
    val roomService: RoomService
) {

    @PostMapping("/room")
    fun createRoom(@RequestBody request: RoomCreateRequest): ResponseEntity<RoomCreateResponse>{
        val room = roomService.createRoom(request.playerId, request.slots).getOrElse {
            return responseError(it)
        }
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(room.id).toUri()
        return ResponseEntity.created(uri).body(RoomCreateResponse(room.id, room.pass, room.slots) )
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

    fun <T> responseError(error: ApplicationError): ResponseEntity<T>{
        return when (error){
            is ApplicationError.EntityNotFound -> ResponseEntity.notFound().build()
            is ApplicationError.UnexpectedError -> ResponseEntity.badRequest().build()
        }
    }

}