package main

import (
	"fmt"
	"net/http"
)

func sseHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "text/event-stream")
	w.Header().Set("Cache-Control", "no-cache")
	w.Header().Set("Connection", "keep-alive")

	roomPass := r.URL.Query().Get("roomPass")
	playerID := r.URL.Query().Get("playerID")
	if roomPass == "" || playerID == "" {
		http.Error(w, "RoomPass and PlayerID are required", http.StatusBadRequest)
		return
	}

	clientChan := make(chan string)
	room := getRoom(roomPass)
	room.addClient(clientChan, playerID)
	defer room.removeClient(clientChan)

	flusher, ok := w.(http.Flusher)
	if !ok {
		http.Error(w, "Streaming not supported", http.StatusInternalServerError)
		return
	}

	for {
		select {
		case msg := <-clientChan:
			fmt.Fprintf(w, "data: %s\n\n", msg)
			flusher.Flush()
		case <-r.Context().Done():
			return
		}
	}
}
