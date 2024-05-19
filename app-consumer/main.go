package main

import (
	"log"
	"net/http"
)

func main() {
	go consumeKafkaMessages([]string{"localhost:9092"}, "top-trumps")

	http.HandleFunc("/events", sseHandler)
	log.Println("Server started on :3000")
	if err := http.ListenAndServe(":3000", nil); err != nil {
		log.Fatalf("Failed to start server: %v", err)
	}
}
