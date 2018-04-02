package com.appdirect.jackson.json;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class JsonJavaTest {

	@Test
	public void testJavaTimeSerialization() {
		TestDateClass expectedValue = new TestDateClass();
		TestDateClass actualValue = Json.fromJson(Json.toJson(expectedValue), TestDateClass.class);

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(actualValue.getInstant()).isEqualByComparingTo(expectedValue.getInstant());
			softly.assertThat(actualValue.getZonedDateTime()).describedAs("ZonedDateTime").isEqualTo(expectedValue.getZonedDateTime());
			softly.assertThat(actualValue.getLocalDateTime()).describedAs("LocalDateTime").isEqualTo(expectedValue.getLocalDateTime());
			softly.assertThat(actualValue.getOffsetDateTime().toInstant()).describedAs("OffsetDateTime").isEqualTo(expectedValue.getOffsetDateTime().toInstant());
		});

	}

	@Test
	public void testFromJson() {
		TestClass expectedValue = new TestClass();
		TestClass actualValue = Json.fromJson(Json.toJson(expectedValue), TestClass.class);

		SoftAssertions.assertSoftly ( it -> {
			it.assertThat(actualValue.getName()).isEqualTo(expectedValue.getName());
			it.assertThat(actualValue.getNumber()).isEqualTo(expectedValue.getNumber());
			it.assertThat(actualValue.getDouble()).isEqualTo(expectedValue.getDouble());
		});
	}
}
