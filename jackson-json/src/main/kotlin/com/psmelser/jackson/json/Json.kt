package com.psmelser.jackson.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode

object Json {
	var jsonConverter: JsonConverter = JsonConverter()

    @JvmStatic fun converter(): JsonConverter {
        return JsonConverter()
    }

    @JvmStatic fun converter(serializationSettings: JsonSerializationSettings): JsonConverter {
        return JsonConverter(serializationSettings)
    }

    @JvmStatic fun <T> fromJson(jsonString: String, targetClass: Class<T>): T {
        return jsonConverter.fromJson(jsonString, targetClass)
    }

    @JvmStatic fun <T> fromJson(jsonString: String, targetClass: TypeReference<*>): T {
        return jsonConverter.fromJson(jsonString, targetClass)
    }

    @JvmStatic fun <T> fromJson(jsonString: String, targetClass: Class<T>, serializationSettings: JsonSerializationSettings): T {
        return jsonConverter.fromJson(jsonString, targetClass, serializationSettings)
    }

    @JvmStatic inline fun <reified T> fromJson(jsonString: String): T {
		return jsonConverter.fromJson(jsonString)
	}

    @JvmStatic inline fun <reified T> fromJson(jsonString: String, serializationSettings: JsonSerializationSettings): T {
		return jsonConverter.fromJson(jsonString, serializationSettings)
	}

    @JvmStatic fun <T> toJson(objectToSerialize: T): String {
        return jsonConverter.toJson(objectToSerialize)
    }

    @JvmStatic fun <T> toJson(objectToSerialize: T, serializationSettings: JsonSerializationSettings): String {
        return jsonConverter.toJson(objectToSerialize, serializationSettings)
    }

    @JvmStatic fun toJsonNode(json: String): JsonNode {
        return jsonConverter.toJsonNode(json)
    }

    @JvmStatic fun toJsonNode(json: Any): JsonNode {
        return jsonConverter.toJsonNode(json)
    }

}
