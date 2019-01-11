package com.psmelser.jackson.yaml

import com.psmelser.jackson.json.YamlConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

object Yaml {
    var yamlConverter: YamlConverter = YamlConverter()
    val mapper = YAMLMapper()

    fun converter(): YamlConverter {
        return YamlConverter()
    }

    fun converter(serializationSettings: YamlSerializationSettings): YamlConverter {
        return YamlConverter(serializationSettings)
    }

    inline fun <reified T> fromYaml(yamlString: String): T {
        return yamlConverter.fromYaml(yamlString)
    }

    fun <T> fromYaml(jsonString: String, targetClass: Class<T>): T {
        return yamlConverter.fromYaml(jsonString, targetClass)
    }

    fun <T> fromYaml(jsonString: String, targetClass: TypeReference<*>): T {
        return yamlConverter.fromYaml(jsonString, targetClass)
    }

    fun <T> fromYaml(jsonString: String, targetClass: Class<T>, serializationSettings: YamlSerializationSettings): T {
        return yamlConverter.fromYaml(jsonString, targetClass, serializationSettings)
    }

    inline fun <reified T> fromYaml(jsonString: String, serializationSettings: YamlSerializationSettings): T {
        return yamlConverter.fromYaml(jsonString, serializationSettings)
    }

    fun <T> toYaml(objectToSerialize: T): String {
        return yamlConverter.toYaml(objectToSerialize)
    }

    fun <T> toYaml(objectToSerialize: T, serializationSettings: YamlSerializationSettings): String {
        return yamlConverter.toYaml(objectToSerialize, serializationSettings)
    }
}