[ ![Download](https://api.bintray.com/packages/psmelser/kotlin-jackson/kotlin-jackson-json/images/download.svg) ](https://bintray.com/psmelser/kotlin-jackson/kotlin-jackson-json/_latestVersion)

# kotlin-jackson
A simple library to kotlinize jackson libbraries for JSON serialization. 

### Serialize using singlton Json object
```kotlin
var serializedObject = Json.toJson(someObject)
```

### Serialize using an instance of JsonConverter
```kotlin
var json = Json.converter()
var serializedObject = json.toJson(someObject)
```

### Deserialize using singleton Json object
```kotlin
var deserializedObject = Json.fromJson<MyObject>(jsonString)
```

### Deserialize using an instance of JsonConverter
```kotlin
var json = Json.converter()
var deserializedObject = json.fromJson<MyObject>(jsonString)
```

### Create a customized JsonConverter
```kotlin
var json = Json.converter(JsonSerializationSettings.Builder()
                .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .with(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .with(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .with(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .with(JavaTimeModule())
                .with(KotlinModule())
                .build())
                
var jsonString = json.toJson(objectToSerialize)
```
