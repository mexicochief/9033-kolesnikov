package ru.cft.focusstart.kolesnikov.dto.developer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeveloperMessage {
    private final String name;
    private final Long id;
    private final String description;

    @JsonCreator
    public DeveloperMessage(
            @JsonProperty("name") String name,
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
