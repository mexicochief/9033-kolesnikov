package ru.cft.focusstart.kolesnikov.api.urlparsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class URLParsers {
    Matcher getMatcher(String url, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(url);
        if(!matcher.find()){
            throw new RuntimeException("String for Matcher not found");
        }
        return matcher;
    }
}
