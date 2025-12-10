package com.studyplatform.server.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(LocalDateTime attribute) {
        return (attribute == null) ? null : attribute.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long dbData) {
        return (dbData == null) ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(dbData), ZoneId.systemDefault());
    }


}
