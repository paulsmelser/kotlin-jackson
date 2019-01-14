package com.psmelser.jackson.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.SoftAssertions
import org.junit.Test

class JsonTest {
    @Test
    fun `Test Java time serialization`() {
        val expectedValue = TestDateClass()
        val jsonValue = Json.toJson(expectedValue)
        val actualValue = Json.fromJson<TestDateClass>(jsonValue)

        assertThat(actualValue.instant).isEqualByComparingTo(expectedValue.instant)
        assertThat(actualValue.zonedDateTime).describedAs("ZonedDateTime").isEqualTo(expectedValue.zonedDateTime)
        assertThat(actualValue.localDateTime).describedAs("LocalDateTime").isEqualTo(expectedValue.localDateTime)
        assertThat(actualValue.offsetDateTime.toInstant()).describedAs("OffsetDateTime").isEqualTo(expectedValue.offsetDateTime.toInstant())
    }
    @Test
    fun `Test Java time serializatssion`() {
        val expectedValue = TestDateClass()
        val list = listOf(expectedValue)
        val toJson = Json.toJson(list)
        val fromJson = Json.fromJsonArray<List<TestDateClass>>(toJson)
        val jsonValue = Json.toJson(expectedValue)
        val actualValue = Json.fromJson<TestDateClass>(jsonValue)

        assertThat(fromJson[0].instant).isEqualByComparingTo(expectedValue.instant)
        assertThat(actualValue.instant).isEqualByComparingTo(expectedValue.instant)
        assertThat(actualValue.zonedDateTime).describedAs("ZonedDateTime").isEqualTo(expectedValue.zonedDateTime)
        assertThat(actualValue.localDateTime).describedAs("LocalDateTime").isEqualTo(expectedValue.localDateTime)
        assertThat(actualValue.offsetDateTime.toInstant()).describedAs("OffsetDateTime").isEqualTo(expectedValue.offsetDateTime.toInstant())
    }

    @Test
    fun `Test fromJson`() {
        val expectedValue = TestClass(double = 12.3)
        val actualValue = Json.fromJson<TestClass>(Json.toJson(expectedValue))

        SoftAssertions.assertSoftly {
            it.assertThat(actualValue.name).isEqualTo(expectedValue.name)
            it.assertThat(actualValue.number).isEqualTo(expectedValue.number)
            it.assertThat(actualValue.double).isEqualTo(expectedValue.double)
        }
    }

    @Test
    fun `Test fromJsonArray`() {
        val expectedValue = TestClass(double = 12.3)
        val actualList = Json.fromJsonArray<List<TestClass>>(Json.toJson(listOf(expectedValue)))

        SoftAssertions.assertSoftly {
            val actualValue = actualList[0]
            it.assertThat(actualValue.name).isEqualTo(expectedValue.name)
            it.assertThat(actualValue.number).isEqualTo(expectedValue.number)
            it.assertThat(actualValue.double).isEqualTo(expectedValue.double)
        }
    }

    @Test
    fun `Test fromJson blah`() {
        assertThatThrownBy { Json.fromJson<TestClass>("{\"name\":\"world\",\"number:\"1\",\"double\":\"ahhh\"}") }
                .hasMessageContaining("was expecting a colon to separate field name and value")
                .isInstanceOf(JsonSerializationException::class.java)
    }

    @Test
    fun `Test fromJson blah settings`() {
        Json.converter(JsonSerializationSettings.Builder()
                .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .with(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .with(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .with(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .with(JavaTimeModule())
                .with(KotlinModule())
                .build())
        assertThatThrownBy { Json.fromJson<TestClass>("{\"name\":\"world\",\"number:\"1\",\"double\":\"ahhh\"}") }
                .hasMessageContaining("was expecting a colon to separate field name and value")
                .isInstanceOf(JsonSerializationException::class.java)
    }
}