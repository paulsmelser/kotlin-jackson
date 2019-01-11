package com.psmelser.jackson.json

class JsonSerializationException(cause: Throwable) : RuntimeException(cause.localizedMessage, cause)
