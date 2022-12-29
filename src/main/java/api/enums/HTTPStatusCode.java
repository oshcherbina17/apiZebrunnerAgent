package api.enums;

public enum HTTPStatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    OK_NO_CONTENT(204),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER_ERROR(500);

    private int status;

    HTTPStatusCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
