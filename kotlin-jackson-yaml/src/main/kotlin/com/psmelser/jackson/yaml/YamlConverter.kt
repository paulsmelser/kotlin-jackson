package com.psmelser.jackson.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.psmelser.jackson.yaml.YamlSerializationSettings
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
        return wrapException {
            val reader = objectMapper
                    .reader()
                    .forType(targetClass)

            reader.readValue(jsonString)
        }
    }

    fun <T> fromYaml(jsonString: String, targetClass: TypeReference<*>): T {
        return wrapException {
            objectMapper.readValue(jsonString, targetClass)
        }
    }

    fun toObjectNode(json: String): ObjectNode {
        return wrapException { objectMapper.readTree(json) as ObjectNode }
    }

    fun <T> fromYaml(jsonString: String, targetClass: Class<T>, serializationSettings: YamlSerializationSettings): T {
        return wrapException {
            val reader = serializationSettings.createMapper()
                    .reader()
                    .forType(targetClass)

            reader.readValue(jsonString)
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
        return wrapException {
            objectMapper.writer().writeValueAsString(objectToSerialize)
        }
    }

    fun <T> toYaml(objectToSerialize: T, serializationSettings: YamlSerializationSettings): String {
        return wrapException {
            serializationSettings.createMapper().writer().writeValueAsString(objectToSerialize)
        }
    }

    private inline fun <T> wrapException(action: () -> T) : T {
        return try {
            action()
        } catch (e: Exception) {
            throw YamlSerializationException(e)
        }
    }
}
