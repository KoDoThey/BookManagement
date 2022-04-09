package vcc.mlbigdata.intern.bookmanagement.util.exception;

public class ClientException extends Exception {
    private static final long serialVersionUID                   = 1L;

    private static final Integer SERVER_ERROR_CODE               = 1000;

    private static final Integer ACCESS_DENIED_CODE              = 2000;

    private static final Integer ERROR_STATUS                    = 0;
    private static final Integer SUCCESS_STATUS                  = 1;

    public ClientException() {
    }

    public ClientException(String s) {
        super(s);
    }

    public ClientException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public enum Message {
        SERVER_ERROR(SERVER_ERROR_CODE, "Lỗi hệ thống, thử lại sau", ERROR_STATUS),

        ACCESS_DENIED(ACCESS_DENIED_CODE, "Không được phép truy cập", ERROR_STATUS),
        ;

        public int code;
        public int status;
        public String message;

        Message(int code, String message, int status) {
            this.code = code;
            this.status = status;
            this.message = message;
        }
    }
}