package ru.cft.focusstart.kolesnikov.dto.error;

public class ErrorMessage {
    private final int code;
    private final String msg;

    public ErrorMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
