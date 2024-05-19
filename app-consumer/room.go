package main

import (
	"log"
	"sync"
)

type Room struct {
	roomPass string
	clients  map[chan string]struct{}
	mu       sync.Mutex
}

type RoomManager struct {
	rooms map[string]*Room
	mu    sync.Mutex
}

var roomManager = &RoomManager{
	rooms: make(map[string]*Room),
}

func (rm *RoomManager) getRoom(roomPass string) *Room {
	rm.mu.Lock()
	defer rm.mu.Unlock()
	room, exists := rm.rooms[roomPass]
	if !exists {
		room = &Room{
			roomPass: roomPass,
			clients:  make(map[chan string]struct{}),
		}
		rm.rooms[roomPass] = room
	}
	return room
}

func (r *Room) sendMessage(msg string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	for clientChan := range r.clients {
		select {
		case clientChan <- msg:
		default:
			log.Println("Channel might be full. Skipping.")
		}
	}
	log.Printf("Sent message to all clients in room %s: %s", r.roomPass, msg)
}

func (r *Room) addClient(clientChan chan string, playerID string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	r.clients[clientChan] = struct{}{}
	log.Printf("Player %s added to room %s", playerID, r.roomPass)
}

func (r *Room) removeClient(clientChan chan string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	delete(r.clients, clientChan)
	close(clientChan)
	log.Printf("Client removed from room %s", r.roomPass)
}

func getRoom(roomPass string) *Room {
	return roomManager.getRoom(roomPass)
}
