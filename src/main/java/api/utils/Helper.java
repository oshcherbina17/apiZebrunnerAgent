package api.utils;

public class Helper { ////////

    private static Helper helper;

    private String testRunId;

    private String testId;

    private String status;

    private String testSessionId;

    private String testResultRun;

    private String testResultExecution;

    public Helper() {
    }

    public static Helper getHelper() {
        return helper;
    }

    public String getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(String testRunId) {
        this.testRunId = testRunId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestSessionId() {
        return testSessionId;
    }

    public void setTestSessionId(String testSessionId) {
        this.testSessionId = testSessionId;
    }

    public String getTestResultRun() {
        return testResultRun;
    }

    public void setTestResultRun(String testResultRun) {
        this.testResultRun = testResultRun;
    }

    public String getTestResultExecution() {
        return testResultExecution;
    }

    public void setTestResultExecution(String testResultExecution) {
        this.testResultExecution = testResultExecution;
    }
}

