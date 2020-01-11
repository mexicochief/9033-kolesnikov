package ru.cft.focusstart.kolesnikov.dto.developer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeveloperMessage.Builder.class)
public class DeveloperMessage {
    private final String name;
    private final Long id;
    private final String description;

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String name;
        private Long id;
        private String description;

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

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public DeveloperMessage build() {
            return new DeveloperMessage(this.name, this.id, this.description);
        }
    }

    public DeveloperMessage(String name, Long id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return name;
    } // тут мб везде void поставить

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
