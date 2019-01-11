package com.psmelser.jackson.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

class JsonConverter {
    val objectMapper: ObjectMapper

    constructor() {
        objectMapper = JsonSerializationSettings.Builder()
                .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .with(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .with(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .with(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .with(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .with(JavaTimeModule())
                .with(KotlinModule())
                .build()
                .createMapper()
    }

    constructor(serializationSettings: JsonSerializationSettings) {
        this.objectMapper = serializationSettings.createMapper()
    }

    fun <T> fromJson(jsonString: String, targetClass: Class<T>): T {
        return wrapException { objectMapper.reader().forType(targetClass).readValue(jsonString) }
    }

    fun <T> fromJson(jsonString: String, targetClass: TypeReference<*>): T {
        return wrapException { objectMapper.readValue(jsonString, targetClass) }
    }

    fun <T> fromJson(jsonString: String, targetClass: Class<T>, serializationSettings: JsonSerializationSettings): T {
        return wrapException { serializationSettings.createMapper().reader().forType(targetClass).readValue(jsonString) }
    }

    fun <T> toJson(objectToSerialize: T): String {
        return wrapException { objectMapper.writer().writeValueAsString(objectToSerialize) }

    }

    fun <T> toJson(objectToSerialize: T, serializationSettings: JsonSerializationSettings): String {
        return wrapException { serializationSettings.createMapper().writer().writeValueAsString(objectToSerialize) }
    }

    fun toJsonNode(json: String): JsonNode {
        return wrapException { objectMapper.reader().readTree(json) }
    }

    fun toJsonNode(json: Any): JsonNode {
        return objectMapper.valueToTree(json)
    }

    inline fun <reified T> fromJson(jsonString: String, serializationSettings: JsonSerializationSettings): T {
        return try {
            serializationSettings.createMapper().reader().forType(T::class.java).readValue(jsonString)
        } catch (e: Exception) {
            throw JsonSerializationException(e)
        }
    }

    inline fun <reified T> fromJson(jsonString: String): T {
        return try {
            objectMapper.reader().forType(T::class.java).readValue(jsonString)
        } catch (e: Exception) {
            throw JsonSerializationException(e)
        }
    }

    private inline fun <T> wrapException(action: () -> T) : T {
        return try {
            action()
        } catch (e: Exception) {
            throw JsonSerializationException(e)
        }
    }
}
