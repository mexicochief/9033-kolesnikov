package ru.cft.focusstart.kolesnikov.api.urlparsers;

import ru.cft.focusstart.kolesnikov.exception.ObjectNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class URLParsers {
    Matcher getMatcher(String url, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(url);
        if(!matcher.find()){
            throw new ObjectNotFoundException("Object not found");
        }
        return matcher;
    }
}
