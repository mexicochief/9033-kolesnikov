package ru.cft.focusstart.kolesnikov.api.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.kolesnikov.api.ExceptionHandler;
import ru.cft.focusstart.kolesnikov.api.urlparsers.URLParserLong;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.dto.publisher.PublisherMessage;
import ru.cft.focusstart.kolesnikov.service.publisher.DefaultPublisherService;
import ru.cft.focusstart.kolesnikov.service.publisher.PublisherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/publishers/*", name = "publishers")
public class PublisherServlet extends HttpServlet {
    private static final String PUBLISHERS_PATTERN = "/publishers";
    private static final String PUBLISHER_PATTERN = "^/publishers/(?<id>[0-9]+)$";
    private static final String PUBLISHER_GAME_PATTERN = "^/publishers/(?<id>[0-9]+)/games";
    private final ObjectMapper mapper = new ObjectMapper();
    private final PublisherService publisherService = DefaultPublisherService.getInstance();
    private final ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.equals(PUBLISHERS_PATTERN)) { // наверное лучше сделать единорбразно
                List<PublisherMessage> respMessages = getByName(req.getParameter("name"));
                writeResp(resp, respMessages);
            } else if (url.matches(PUBLISHER_PATTERN)) {
                PublisherMessage respMsg = getById(url);
                writeResp(resp, respMsg);
            } else if (url.matches(PUBLISHER_GAME_PATTERN)) {
                writeResp(resp, getGames(url));
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            String url = getUrl(req);
            if (url.matches(PUBLISHERS_PATTERN)) {
                PublisherMessage requestMsg = mapper.readValue(req.getInputStream(), PublisherMessage.class);
                System.out.println(requestMsg.getId());
                writeResp(resp, publisherService.add(requestMsg));
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.matches(PUBLISHER_PATTERN)) {
                PublisherMessage respMsg = putById(req, url);
                writeResp(resp, respMsg);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    private String getUrl(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length());
    }

    private List<PublisherMessage> getByName(String name) {
        return publisherService.get(name);
    }

    private PublisherMessage getById(String url) {
        URLParserLong urlParsers = new URLParserLong();
        long id = urlParsers.getLongFromUrl(url, PUBLISHER_PATTERN, "id");
        return publisherService.getById(id);
    }

    private PublisherMessage putById(HttpServletRequest req, String url) throws IOException {
        URLParserLong parserLong = new URLParserLong();
        long id = parserLong.getLongFromUrl(url, PUBLISHER_PATTERN, "id");
        PublisherMessage reqMsg = mapper.readValue(req.getInputStream(), PublisherMessage.class);
        System.out.println(reqMsg.getName());
        return publisherService.update(reqMsg, id);
    }

    private List<GameMessage> getGames(String url) {
        URLParserLong parserLong = new URLParserLong();
        System.out.println(url);
        long id = parserLong.getLongFromUrl(url, PUBLISHER_GAME_PATTERN, "id");
        System.out.println(id);
        return publisherService.getGames(id);
    }

    private void writeResp(HttpServletResponse resp, Object message) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), message);
    }
}
