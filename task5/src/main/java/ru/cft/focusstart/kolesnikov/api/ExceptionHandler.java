package ru.cft.focusstart.kolesnikov.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.kolesnikov.api.servlets.DeveloperServlet;
import ru.cft.focusstart.kolesnikov.dto.error.ErrorMessage;
import ru.cft.focusstart.kolesnikov.exception.ObjectNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler {
    private static final ExceptionHandler INSTANCE = new ExceptionHandler();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(DeveloperServlet.class);

    public static ExceptionHandler getInstance() {
        return INSTANCE;
    }

    public void exceptionHandler(Exception e, HttpServletResponse resp) throws IOException {
        ErrorMessage eMsg;
        if (e instanceof IllegalArgumentException) {
            eMsg = new ErrorMessage(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } else if (e instanceof ObjectNotFoundException) {
            eMsg = new ErrorMessage(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } else {
            log.error(e.getMessage(), e);
            eMsg = new ErrorMessage(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error");
        }
        writeError(eMsg, resp);
    }

    private void writeError(ErrorMessage msg, HttpServletResponse resp) throws IOException {
        resp.reset();
        resp.setContentType("application/json");
        resp.setStatus(msg.getCode());
        mapper.writeValue(resp.getOutputStream(), msg);
    }

}
