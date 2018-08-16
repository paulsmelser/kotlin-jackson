package com.psmelser.jackson.json

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.Test

class JsonTest {
    @Test
    fun `Test Java time serialization`() {
        val expectedValue = TestDateClass()
        val actualValue = Json.fromJson<TestDateClass>(Json.toJson(expectedValue))

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

}