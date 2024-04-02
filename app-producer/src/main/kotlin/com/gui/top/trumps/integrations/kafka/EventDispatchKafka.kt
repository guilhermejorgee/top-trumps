package com.gui.top.trumps.integrations.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.gui.top.trumps.core.common.domain.Event
import com.gui.top.trumps.core.common.domain.EventDispatch
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class EventDispatchKafka(
    val template: KafkaTemplate<String, String>
) : EventDispatch {

    @Value("\${topic.name}")
    private lateinit var TOPIC_NAME: String

    override fun send(event: Event) {
        template.send(TOPIC_NAME, event.toJson())
    }
}