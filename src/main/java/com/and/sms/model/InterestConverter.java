package com.and.sms.model;

import com.google.common.collect.Sets;

import javax.persistence.AttributeConverter;
import java.util.Set;

public class InterestConverter implements AttributeConverter<Set<String>, String>{
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> interests) {
        StringBuilder sb = new StringBuilder();
        interests.forEach(interest -> sb.append(interest).append(SEPARATOR));
        return sb.deleteCharAt(sb.indexOf(SEPARATOR)).toString();
    }

    @Override
    public Set<String> convertToEntityAttribute(String interestsString) {
        return Sets.newHashSet(interestsString.split(SEPARATOR));
    }
}
