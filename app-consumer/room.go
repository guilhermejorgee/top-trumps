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

var (
	rooms      = make(map[string]*Room)
	roomsMutex = sync.Mutex{}
)

func getRoom(roomPass string) *Room {
	roomsMutex.Lock()
	defer roomsMutex.Unlock()
	room, exists := rooms[roomPass]
	if !exists {
		room = &Room{
			roomPass: roomPass,
			clients:  make(map[chan string]struct{}),
		}
		rooms[roomPass] = room
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
}

func (r *Room) addClient(clientChan chan string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	r.clients[clientChan] = struct{}{}
}

func (r *Room) removeClient(clientChan chan string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	delete(r.clients, clientChan)
}