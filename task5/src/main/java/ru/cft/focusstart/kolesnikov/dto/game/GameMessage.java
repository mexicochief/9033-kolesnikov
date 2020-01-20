package ru.cft.focusstart.kolesnikov.dto.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

public class GameMessage {
    private final String name;
    private final Long id;
    private final String description;
    @JsonDeserialize(using = JsonLocalDateDeserializer.class)
    @JsonSerialize(using = JsonLocalDateSerializer.class) // с LocalDate по моему @JsonFormat не подходит, или я что-то не так делал
    private final LocalDate releaseDate;
    private final Long developerId;
    private final Long publisherId;

    @JsonCreator
    public GameMessage(
            @JsonProperty("name") String name,
            @JsonProperty("id") Long id,
            @JsonProperty("description") String description,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("developerid") Long developerId,
            @JsonProperty("publisherid") Long publisherId) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.releaseDate = releaseDate;
        this.developerId = developerId;
        this.publisherId = publisherId;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public Long getPublisherId() {
        return publisherId;
    }
}
