package com.jcoremanager.util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária (Adapter) para o Gson.
 * O Gson padrão não sabe lidar nativamente com o 'LocalDateTime' do Java 8+,
 * então precisamos ensinar a ele como converter Texto -> Data e Data -> Texto.
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    // Define o formato padrão ISO (ex: 2023-10-05T15:30:00)
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Serialização: Converte o objeto LocalDateTime (Java) para uma String (JSON).
     */
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(src));
    }

    /**
     * Deserialização: Converte a String (JSON) de volta para um objeto LocalDateTime (Java).
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}