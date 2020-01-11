package ru.cft.focusstart.kolesnikov.repository.developer;

import ru.cft.focusstart.kolesnikov.dto.developer.DeveloperMessage;

import java.util.List;

public interface DeveloperDBManager {

    DeveloperMessage add(DeveloperMessage developerMessage);

    DeveloperMessage getById(long id);

    List<DeveloperMessage> get(String name);

    DeveloperMessage update(DeveloperMessage developerMessage, Long id);
}
