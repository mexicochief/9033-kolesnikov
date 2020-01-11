package ru.cft.focusstart.kolesnikov.api.urlparsers;

import java.util.regex.Matcher;

public class URLParserLong extends URLParsers { // вот эту хуйню наверное не так сделать
    public long getLongFromUrl(String url, String pattern, String groupName) {
        Matcher matcher = getMatcher(url, pattern);
        try {
            return Long.parseLong(matcher.group(groupName));
        } catch (Exception e) {
            throw new RuntimeException("id not found");
        }
    }
}
