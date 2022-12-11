package api.enums;

public enum JsonValues {
    AUTH_TOKEN ("authToken"),
    ID("id"),
    TEST_STATUS("status"),
    TEST_RESULT("result"),
    TEST_IDS("testIds"),
    TEST_ID("testId");

    private final String value;

    private JsonValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
