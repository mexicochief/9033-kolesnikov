package ru.cft.focusstart.kolesnikov.api.urlparsers;

import java.sql.Date;
import java.util.regex.Matcher;

public class URLParserDate extends URLParsers {
    public Date getDateFromUrl(String url, String pattern, String groupName){
        Matcher matcher = getMatcher(url,pattern);
        try {
            String dateStr = matcher.group(groupName);
            return Date.valueOf(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("id not found");
        }
    }
}
