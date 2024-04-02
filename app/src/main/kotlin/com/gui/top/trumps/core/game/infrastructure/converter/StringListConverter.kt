package com.gui.top.trumps.core.game.infrastructure.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.lang.RuntimeException

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {

    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<String>): String {
        return attribute.let {
            try {
                objectMapper.writeValueAsString(it)
            } catch (e: Exception) {
                throw RuntimeException()
            }
        }
    }

    override fun convertToEntityAttribute(dbData: String): List<String> {
        return dbData.let {
            try {
                objectMapper.readValue<List<String>>(it)
            } catch (e: Exception) {
                throw RuntimeException()
            }
        }
    }
}