package ru.cft.focusstart.kolesnikov.api.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.kolesnikov.api.ExceptionHandler;
import ru.cft.focusstart.kolesnikov.api.urlparsers.URLParserDate;
import ru.cft.focusstart.kolesnikov.api.urlparsers.URLParserLong;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.service.game.DefaultGameService;
import ru.cft.focusstart.kolesnikov.service.game.GameService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(urlPatterns = "/games/*", name = "games")
public class GameServlet extends HttpServlet {
    private static final String GAMES_PATTERN = "/games";
    private static final String GAME_PATTERN = "^/games/(?<id>[0-9]+)$";
    private static final String GAME_RELEASE_DATE_PATTERN = "^/games/(?<date>"
            + "((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)" // года где в Феврале 29 д.
            + "|(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))" // Общие 28 дней февраля
            + "|(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))" //  31 день
            + "|(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30)))$"; // 30 дней
    private final ObjectMapper mapper = new ObjectMapper();
    private final GameService gameService = DefaultGameService.getInstance();
    private final ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            String url = getUrl(req);
            if (url.equals(GAMES_PATTERN)) { // наверное лучше сделать единорбразно
                List<GameMessage> respMessages = getByName(req);
                writeResp(resp, respMessages);
            } else if (url.matches(GAME_PATTERN)) {
                GameMessage respMsg = getById(url);
                writeResp(resp, respMsg);
            } else if (url.matches(GAME_RELEASE_DATE_PATTERN)) {
                writeResp(resp, getByReleaseDate(url));
            }
        } catch (Exception e) {
            exceptionHandler.exceptionHandler(e, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.matches(GAMES_PATTERN)) {
                GameMessage requestMsg = mapper.readValue(req.getInputStream(), GameMessage.class);
                GameMessage responseMsg = gameService.add(requestMsg);
                System.out.println(requestMsg.getName() + requestMsg.getReleaseDate());
                writeResp(resp, responseMsg);
            }
        } catch (Exception e) {
            exceptionHandler.exceptionHandler(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.matches(GAME_PATTERN)) {
                GameMessage respMsg = putById(req, url);
                writeResp(resp, respMsg);
            }
        } catch (Exception e) {
            exceptionHandler.exceptionHandler(e, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String url = getUrl(req);
            if (url.matches(GAME_PATTERN)) {
                URLParserLong urlParserLong = new URLParserLong();
                long id = urlParserLong.getLongFromUrl(url, GAME_PATTERN, "id");
                gameService.delete(id);
            }
        } catch (Exception e) {
            exceptionHandler.exceptionHandler(e, resp);
        }
    }

    private String getUrl(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length());
    }

    private List<GameMessage> getByName(HttpServletRequest req) {
        return gameService.get(req.getParameter("name"), req.getParameter("developerName"));
    }

    private GameMessage getById(String url) {
        URLParserLong urlParsers = new URLParserLong();

        long id = urlParsers.getLongFromUrl(url, GAME_PATTERN, "id");
        return gameService.getById(id);
    }

    private List<GameMessage> getByReleaseDate(String url) {
        URLParserDate parserDate = new URLParserDate();
        Date releaseDate = parserDate.getDateFromUrl(url, GAME_RELEASE_DATE_PATTERN, "date");
        System.out.println(releaseDate);
        return gameService.getByReleaseDate(releaseDate);
    }

    private GameMessage putById(HttpServletRequest req, String url) throws IOException {
        URLParserLong parserLong = new URLParserLong();
        long id = parserLong.getLongFromUrl(url, GAME_PATTERN, "id");
        GameMessage reqMsg = mapper.readValue(req.getInputStream(), GameMessage.class);
        System.out.println(reqMsg.getName());
        return gameService.update(reqMsg, id);
    }

    private void writeResp(HttpServletResponse resp, Object message) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), message);
    }
}
