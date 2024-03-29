package api.enums;

public enum JsonValues {
    AUTH_TOKEN("authToken"),
    ID("id"),
    TEST_STATUS("status"),
    TEST_RESULT("result");

    private final String value;

    JsonValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
