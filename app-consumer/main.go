package main

import (
	"context"
	"fmt"
	"time"
	"github.com/segmentio/kafka-go"
)

func main() {
	// Corrigido: Usar chaves para inicializar um slice
	topics := []string{"top-trumps"}

	for _, v := range topics {
		c := fmt.Sprintf("consumer-%s", v)
		go consumer(v, c)
	}

	// Alterado para usar um canal para aguardar o sinal de término
	// Isso é mais idiomático do que dormir por um tempo fixo
	quit := make(chan struct{})
	go func() {
		time.Sleep(300 * time.Second)
		close(quit)
		fmt.Println("Encerrando threads")
	}()

	<-quit // Aguarda aqui até o sinal de término
}

func consumer(topic, consumer string) {
	fmt.Println("Consumindo topic:", topic)

	r := kafka.NewReader(kafka.ReaderConfig{
		Brokers: []string{"localhost:9092"},
		GroupID: consumer,
		Topic:   topic,
	})

	for {
		m, err := r.ReadMessage(context.Background())
		if err != nil {
			fmt.Printf("Erro ao ler mensagem do tópico %s: %v\n", topic, err)
			break
		}
		fmt.Printf("Mensagem de [%s]: %s\n", topic, string(m.Value))
	}
	if err := r.Close(); err != nil {
		fmt.Printf("Erro ao fechar o leitor do tópico %s: %v\n", topic, err)
	}
}