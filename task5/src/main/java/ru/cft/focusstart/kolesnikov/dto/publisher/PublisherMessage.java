package ru.cft.focusstart.kolesnikov.dto.publisher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PublisherMessage {
    private final String name;
    private final Long id;

    @JsonCreator
    public PublisherMessage(
            @JsonProperty("name") String name,
            @JsonProperty("id") Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
