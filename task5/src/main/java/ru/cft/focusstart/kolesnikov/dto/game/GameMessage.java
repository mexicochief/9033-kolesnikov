package ru.cft.focusstart.kolesnikov.dto.game;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;

@JsonDeserialize(builder = GameMessage.Builder.class)
public class GameMessage {
    private final String name;
    private final Long id;
    private final String description;
    @JsonSerialize(using = JsonDateSerializer.class)
    private final Date releaseDate; // тут мб хуйню написал
    private final Long developerId;
    private final Long publisherId;

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String name;
        private Long id;
        private String description;
        private Date releaseDate;
        private Long developerId;
        private Long publisherId;

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

        public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setDeveloperId(Long developerId) {
            this.developerId = developerId;
        }

        public void setPublisherId(Long publisherId) {
            this.publisherId = publisherId;
        }

        public GameMessage build() {
            return new GameMessage(
                    this.name,
                    this.id,
                    this.description,
                    this.releaseDate,
                    this.developerId,
                    this.publisherId
            );
        }
    }

    public GameMessage(
            String name,
            Long id,
            String description,
            Date releaseDate,
            Long developerId,
            Long publisherId) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.releaseDate = releaseDate;
        this.developerId = developerId;
        this.publisherId = publisherId;
    }

//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public Builder toBuilder() {
//        return new Builder(this);
//    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public Long getPublisherId() {
        return publisherId;
    }
}
