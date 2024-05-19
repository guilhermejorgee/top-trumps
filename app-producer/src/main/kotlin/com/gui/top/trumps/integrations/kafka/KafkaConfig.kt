package com.gui.top.trumps.integrations.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder


@Configuration
class KafkaConfig {

    @Value("\${topic.name}")
    private lateinit var TOPIC_NAME: String

    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name(TOPIC_NAME)
            .partitions(1)
            .replicas(1)
            .build()
    }

}