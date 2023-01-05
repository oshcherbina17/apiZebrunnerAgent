package api.utils;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.enums.HTTPStatusCode;
import api.enums.TestStatus;
import api.postMethods.TestExecutionLogs;
import api.postMethods.TestExecutionStart;
import api.postMethods.TestRunStart;
import api.postMethods.TestSessionStart;
import api.putMethods.TestExecutionFinish;
import api.putMethods.TestExecutionLabels;
import api.putMethods.TestRunExecutionFinish;
import api.putMethods.TestSessionFinish;

public class ApiConnection {
    ApiExecution apiExecution = new ApiExecution();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String testRunId;

    private String testId;

    private String status;

    private String testSessionId;

    private String testResultRun;

    private String testResultExecution;

    private String message;

    public ApiConnection() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void testRunStart() {
        TestRunStart testRunStart = new TestRunStart();
        apiExecution.expectStatus(testRunStart, HTTPStatusCode.OK);
        String result = apiExecution.callApiMethod(testRunStart);
        setTestRunId(JsonService.readId(result));
        setTestResultRun(JsonService.readTestStatus(result));
        testRunStart.validateResponse();
    }

    public void testExecutionStart() {
        TestExecutionStart testExecutionStart = new TestExecutionStart(getTestRunId());
        apiExecution.expectStatus(testExecutionStart, HTTPStatusCode.OK);
        String result = apiExecution.callApiMethod(testExecutionStart);
        setTestId(JsonService.readId(result));
        setTestResultExecution(JsonService.readResult(result));
        testExecutionStart.validateResponse();
    }

    public void testExecutionFinish(TestStatus status) {
        TestExecutionFinish testExecutionFinish = new TestExecutionFinish(getTestRunId(), getTestId(), status.getStatus());
        apiExecution.expectStatus(testExecutionFinish, HTTPStatusCode.OK);
        setTestResultExecution(JsonService.readResult(apiExecution.callApiMethod(testExecutionFinish)));
        testExecutionFinish.validateResponse();
    }

    public void testRunExecutionFinish() {
        TestRunExecutionFinish testRunExecutionFinish = new TestRunExecutionFinish(testRunId);
        apiExecution.expectStatus(testRunExecutionFinish, HTTPStatusCode.OK);
        status = JsonService.readTestStatus(apiExecution.callApiMethod(testRunExecutionFinish));
    }

    public void testExecutionLogs() {
        TestExecutionLogs testExecutionLogs = new TestExecutionLogs(getTestRunId(), getTestId(), getMessage());
        apiExecution.expectStatus(testExecutionLogs, HTTPStatusCode.ACCEPTED);
        apiExecution.callApiMethod(testExecutionLogs);
    }

    public void testSessionStart() {
        TestSessionStart testSessionStart = new TestSessionStart(getTestRunId(), getTestId());
        apiExecution.expectStatus(testSessionStart, HTTPStatusCode.OK);
        testSessionId = JsonService.readId(apiExecution.callApiMethod(testSessionStart));
        setTestSessionId(testSessionId);
        testSessionStart.validateResponse();
    }

    public void testSessionFinish() {
        TestSessionFinish testSessionFinish = new TestSessionFinish(getTestRunId(), getTestSessionId(), getTestId());
        apiExecution.expectStatus(testSessionFinish, HTTPStatusCode.OK);
        apiExecution.callApiMethod(testSessionFinish);
        testSessionFinish.validateResponse();
    }

    public void testExecutionLabels() {
        TestExecutionLabels testExecutionLabels = new TestExecutionLabels(getTestRunId(), getTestId());
        apiExecution.expectStatus(testExecutionLabels, HTTPStatusCode.OK_NO_CONTENT);
        apiExecution.callApiMethod(testExecutionLabels);
    }

    public void runTest(TestStatus status) {
        testRunStart();
        testExecutionStart();
        testExecutionLabels();
        testExecutionFinish(status);
        testSessionStart();
        testRunExecutionFinish();
        testSessionFinish();
    }
}
