package ru.cft.focusstart.kolesnikov.repository.publisher;

import ru.cft.focusstart.kolesnikov.dto.publisher.PublisherMessage;

import java.util.List;

public interface PublisherDBManager {
    PublisherMessage add(PublisherMessage developerMessage);

    PublisherMessage getById(long id); // мб заменить на optional

    List<PublisherMessage> get(String name); // надо чтоб get получал строку по которой искать в базе

    void update(PublisherMessage developerMessage, Long id);
}
