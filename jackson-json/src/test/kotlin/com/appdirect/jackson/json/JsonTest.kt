package com.appdirect.jackson.json

import com.appdirect.jackson.json.Json.toJson
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.Test

class JsonTest {
    @Test
    fun `Test Java time serialization`() {
        val something = Something()
        val jsonString = Json.toJson(something)
        val deserializedSomething = Json.fromJson(jsonString, Something::class.java)

        assertThat(deserializedSomething.instant).isEqualByComparingTo(something.instant)
        assertThat(deserializedSomething.zonedDateTime).describedAs("ZonedDateTime").isEqualTo(something.zonedDateTime)
        assertThat(deserializedSomething.localDateTime).describedAs("LocalDateTime").isEqualTo(something.localDateTime)
        assertThat(deserializedSomething.offsetDateTime.toInstant()).describedAs("OffsetDateTime").isEqualTo(something.offsetDateTime.toInstant())
    }

    @Test
    fun `Test fromJson`() {
        val simpleClass = SimpleClass()
        val jsonNode = Json.fromJson<SimpleClass>(toJson(simpleClass))

        SoftAssertions.assertSoftly {
            it.assertThat(jsonNode.name).isEqualTo(simpleClass.name)
            it.assertThat(jsonNode.number).isEqualTo(simpleClass.number)
            it.assertThat(jsonNode.double).isEqualTo(simpleClass.double)
        }
    }

}