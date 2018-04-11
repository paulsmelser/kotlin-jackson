package com.psmelser.jackson.yaml

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import java.util.*

class YamlSerializationSettings private constructor(private val deserializationFeatures: Map<DeserializationFeature, Boolean>,
                                                    private val serializationFeatures: Map<SerializationFeature, Boolean>,
                                                    private val mapperFeatures: Map<MapperFeature, Boolean>,
                                                    private val modules: Collection<Module>) {

	fun createMapper(): YAMLMapper {
		val mapper = YAMLMapper()
		deserializationFeatures.forEach { mapper.configure(it.key, it.value) }
		deserializationFeatures.forEach { mapper.configure(it.key, it.value) }
		serializationFeatures.forEach { mapper.configure(it.key, it.value) }
		mapperFeatures.forEach { mapper.configure(it.key, it.value) }
		modules.forEach { mapper.registerModule(it) }
		return mapper
	}

	class Builder {
		private val deserializationFeatures: MutableMap<DeserializationFeature, Boolean>
		private val serializationFeatures: MutableMap<SerializationFeature, Boolean>
		private val mapperFeatures: MutableMap<MapperFeature, Boolean>
		private val modules: MutableCollection<Module>

		init {
			deserializationFeatures = HashMap()
			serializationFeatures = HashMap()
			mapperFeatures = HashMap()
			modules = ArrayList()
		}

		fun with(deserializationFeature: DeserializationFeature, activated: Boolean): Builder {
			deserializationFeatures[deserializationFeature] = activated
			return this
		}

		fun with(serializationFeature: SerializationFeature, activated: Boolean): Builder {
			serializationFeatures[serializationFeature] = activated
			return this
		}

		fun with(mapperFeature: MapperFeature, activated: Boolean): Builder {
			mapperFeatures[mapperFeature] = activated
			return this
		}

		fun with(module: Module): Builder {
			this.modules.add(module)
			return this
		}

		fun build(): YamlSerializationSettings {
			return YamlSerializationSettings(deserializationFeatures, serializationFeatures, mapperFeatures, modules)
		}
	}

	companion object {
		fun builder(): Builder {
			return Builder()
		}
	}
}
