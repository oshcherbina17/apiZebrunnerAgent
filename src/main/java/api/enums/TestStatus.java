package api.enums;

public enum TestStatus {

    IN_PROGRESS("IN_PROGRESS"),
    PASSED("PASSED"),
    FAILED("FAILED"),
    SKIPPED("SKIPPED"),
    ABORTED("ABORTED");

    private final String status;

    TestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
