package com.appdirect.jackson.json

import com.appdirect.jackson.yaml.YamlSerializationSettings
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.IOException

class YamlConverter {
    val objectMapper: YAMLMapper

    constructor() {
        objectMapper = YamlSerializationSettings.builder()
                .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .with(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .with(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .with(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .with(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .with(JavaTimeModule())
                .build().createMapper()
    }

    constructor(serializationSettings: YamlSerializationSettings) {
        this.objectMapper = serializationSettings.createMapper()
    }

    fun <T> fromYaml(jsonString: String, targetClass: Class<T>): T {
        try {
            val reader = objectMapper
                    .reader()
                    .forType(targetClass)

            return reader.readValue(jsonString)

        } catch (e: IOException) {
            throw YamlSerializationException(e)
        }

    }

    fun <T> fromYaml(jsonString: String, targetClass: TypeReference<*>): T {
        try {
            return objectMapper.readValue(jsonString, targetClass)

        } catch (e: IOException) {
            throw YamlSerializationException(e)
        }

    }

    fun <T> fromYaml(jsonString: String, targetClass: Class<T>, serializationSettings: YamlSerializationSettings): T {
        try {
            val reader = serializationSettings.createMapper()
                    .reader()
                    .forType(targetClass)

            return reader.readValue(jsonString)

        } catch (e: IOException) {
            throw YamlSerializationException(e)
        }

    }

    inline fun <reified T> fromYaml(jsonString: String): T {
        try {
            val reader = objectMapper.reader()
                    .forType(T::class.java)

            return reader.readValue(jsonString)

        } catch (e: IOException) {
            throw YamlSerializationException(e)
        }

    }

    inline fun <reified T> fromYaml(jsonString: String, serializationSettings: YamlSerializationSettings): T {
        try {
            val reader = serializationSettings.createMapper()
                    .reader()
                    .forType(T::class.java)

            return reader.readValue(jsonString)

        } catch (e: IOException) {
            throw YamlSerializationException(e)
        }

    }

    fun <T> toYaml(objectToSerialize: T): String {
        return try {
            objectMapper.writer().writeValueAsString(objectToSerialize)
        } catch (e: JsonProcessingException) {
            throw YamlSerializationException(e)
        }

    }

    fun <T> toYaml(objectToSerialize: T, serializationSettings: YamlSerializationSettings): String {
        return try {
            serializationSettings.createMapper().writer().writeValueAsString(objectToSerialize)
        } catch (e: JsonProcessingException) {
            throw YamlSerializationException(e)
        }

    }
}
