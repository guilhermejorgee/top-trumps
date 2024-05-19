package main

import (
	"context"
	"encoding/json"
	"log"

	"github.com/segmentio/kafka-go"
)

func consumeKafkaMessages(brokers []string, topic string) {
	r := kafka.NewReader(kafka.ReaderConfig{
		Brokers: brokers,
		Topic:   topic,
		GroupID: "top-trumps-consumer",
	})
	defer r.Close()

	for {
		m, err := r.ReadMessage(context.Background())
		if err != nil {
			log.Printf("Error reading message: %v", err)
			continue
		}

		log.Printf("Received Kafka message: %s", string(m.Value))

		var messageData map[string]interface{}
		if err := json.Unmarshal(m.Value, &messageData); err != nil {
			log.Printf("Error unmarshaling message: %v", err)
			continue
		}

		roomPass, ok := messageData["roomPass"].(string)
		if !ok {
			log.Println("roomPass is missing or not a string")
			continue
		}

		log.Printf("Routing message to room: %s", roomPass)

		room := getRoom(roomPass)
		if room != nil {
			room.sendMessage(string(m.Value))
		} else {
			log.Printf("No room found for roomPass: %s", roomPass)
		}
	}
}
