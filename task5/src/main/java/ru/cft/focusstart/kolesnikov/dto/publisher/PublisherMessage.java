package ru.cft.focusstart.kolesnikov.dto.publisher;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = PublisherMessage.Builder.class)
public class PublisherMessage {
    private final String name;
    private final Long id;

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String name;
        private Long id;

        private Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public PublisherMessage build() {
            return new PublisherMessage(this.name, this.id);
        }
    }

    public PublisherMessage(String name, Long id) {
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
