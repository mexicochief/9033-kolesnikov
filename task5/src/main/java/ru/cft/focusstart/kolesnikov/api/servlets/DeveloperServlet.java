package ru.cft.focusstart.kolesnikov.api.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.kolesnikov.api.ExceptionHandler;
import ru.cft.focusstart.kolesnikov.api.urlparsers.URLParserLong;
import ru.cft.focusstart.kolesnikov.dto.developer.DeveloperMessage;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.service.developer.DefaultDeveloperService;
import ru.cft.focusstart.kolesnikov.service.developer.DeveloperService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/developers/*", name = "developers")
public class DeveloperServlet extends HttpServlet {
    private static final String DEVELOPERS_PATTERN = "/developers";
    private static final String DEVELOPER_PATTERN = "^/developers/(?<id>[0-9]+)$";
    private static final String DEVELOPER_GAME_PATTERN = "^/developers/(?<id>[0-9]+)/games";
    private final ObjectMapper mapper = new ObjectMapper();
    private final DeveloperService developerService = DefaultDeveloperService.getInstance();
    private final ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.equals(DEVELOPERS_PATTERN)) { // наверное лучше сделать единорбразно
                List<DeveloperMessage> respMessages = getByName(req.getParameter("name"));
                writeResp(resp, respMessages);
            } else if (url.matches(DEVELOPER_PATTERN)) {
                DeveloperMessage respMsg = getById(url);
                writeResp(resp, respMsg);
            } else if (url.matches(DEVELOPER_GAME_PATTERN)) {
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
            if (url.matches(DEVELOPERS_PATTERN)) {
                DeveloperMessage requestMsg = mapper.readValue(req.getInputStream(), DeveloperMessage.class);
                DeveloperMessage responseMsg = developerService.add(requestMsg);
                writeResp(resp, responseMsg);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.matches(DEVELOPER_PATTERN)) {
                DeveloperMessage respMsg = putById(req, url);
                writeResp(resp, respMsg);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    private String getUrl(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length());
    }

    private List<DeveloperMessage> getByName(String name) {
        return developerService.get(name);
    }

    private DeveloperMessage getById(String url) {
        URLParserLong urlParsers = new URLParserLong();

        long id = urlParsers.getLongFromUrl(url, DEVELOPER_PATTERN, "id");
        return developerService.getById(id);
    }

    private DeveloperMessage putById(HttpServletRequest req, String url) throws IOException {
        URLParserLong parserLong = new URLParserLong();
        long id = parserLong.getLongFromUrl(url, DEVELOPER_PATTERN, "id");
        DeveloperMessage reqMsg = mapper.readValue(req.getInputStream(), DeveloperMessage.class);
        System.out.println(reqMsg.getName());
        return developerService.update(reqMsg, id);
    }

    private List<GameMessage> getGames(String url) {
        URLParserLong urlParsers = new URLParserLong();
        long id = urlParsers.getLongFromUrl(url, DEVELOPER_GAME_PATTERN, "id");
        return developerService.getGames(id);
    }

    private void writeResp(HttpServletResponse resp, Object message) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), message);
    }

}
