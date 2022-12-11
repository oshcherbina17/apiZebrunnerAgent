package api.utils;

import api.enums.HTTPStatusCode;
import api.enums.JsonValues;
import api.enums.TestStatus;
import api.postMethods.*;
import api.putMethods.TestExecutionFinish;
import api.putMethods.TestRunExecutionFinish;
import api.putMethods.TestSessionFinish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class ApiConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private String testRunId;
    private String testId;
    private String status;
    private String testSessionId;

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

    public void refreshToken() {
        ApiExecution apiExecution = new ApiExecution();
        TestRunStart testRunStart = new TestRunStart();
        apiExecution.expectStatus(testRunStart, HTTPStatusCode.OK);
        testRunId = JsonService.readId(apiExecution.callApiMethod(testRunStart));
        LOGGER.info(testRunId);
    }

    public void testRunStart() {
        ApiExecution apiExecution = new ApiExecution();
        TestRunStart testRunStart = new TestRunStart();
        apiExecution.expectStatus(testRunStart, HTTPStatusCode.OK);
        testRunId = JsonService.readId(apiExecution.callApiMethod(testRunStart));
        LOGGER.info(testRunId);
    }

    public void testExecutionStart() {
        ApiExecution apiExecution = new ApiExecution();
        TestExecutionStart testExecutionStart = new TestExecutionStart(testRunId);
        apiExecution.expectStatus(testExecutionStart, HTTPStatusCode.OK);
        testId = JsonService.readId(apiExecution.callApiMethod(testExecutionStart));
        LOGGER.info(testId);
    }

    public void testExecutionFinish(TestStatus status) throws IOException {
        Properties properties = new Properties();
        properties.put(JsonValues.TEST_RESULT.getValue(), status.getStatus());
        String path = "src/test/resources/api/test_execution/put/test_execution_finish.properties";
        FileOutputStream output = new FileOutputStream(path);
        properties.store(output, null);
        ApiExecution apiExecution = new ApiExecution();
        TestExecutionFinish testExecutionFinish = new TestExecutionFinish(testRunId, testId);
        testExecutionFinish.setProperties(properties);
        apiExecution.expectStatus(testExecutionFinish, HTTPStatusCode.OK);
        apiExecution.callApiMethod(testExecutionFinish);
    }

    public void testRunExecutionFinish() {
        ApiExecution apiExecution = new ApiExecution();
        TestRunExecutionFinish testRunExecutionFinish = new TestRunExecutionFinish(testRunId);
        apiExecution.expectStatus(testRunExecutionFinish, HTTPStatusCode.OK);
        status = JsonService.readTestStatus(apiExecution.callApiMethod(testRunExecutionFinish));
    }

    public void testExecutionLogs() throws IOException {
        ApiExecution apiExecution = new ApiExecution();
        TestRunStart testRunStart = new TestRunStart();
        testRunId = JsonService.readId(apiExecution.callApiMethod(testRunStart));

        TestExecutionStart testExecutionStart = new TestExecutionStart(testRunId);
        testId = JsonService.readId(apiExecution.callApiMethod(testExecutionStart));

        ////////////////////////////////////////////////////////////////////
        Properties properties = new Properties();
        properties.put(JsonValues.TEST_ID.getValue(), testId);
        String path = "src/test/resources/api/test_execution/logs/logs.properties";
        FileOutputStream output = new FileOutputStream(path);
        properties.store(output, null);

        TestExecutionLogs testExecutionLogs = new TestExecutionLogs(testRunId);
        testExecutionLogs.setProperties(properties);
        apiExecution.expectStatus(testExecutionLogs, HTTPStatusCode.ACCEPTED);
        apiExecution.callApiMethod(testExecutionLogs);
    }

    public void testSessionStart() throws IOException {
        Properties properties = new Properties();
        properties.put(JsonValues.TEST_IDS.getValue(), testId);
        String path = "src/test/resources/api/test_session/test_session.properties";
        FileOutputStream output = new FileOutputStream(path);
        properties.store(output, null);
        ApiExecution apiExecution = new ApiExecution();
        TestSessionStart testSessionStart = new TestSessionStart(testRunId);
        testSessionStart.setProperties(properties);
        apiExecution.expectStatus(testSessionStart, HTTPStatusCode.OK);
        testSessionId = JsonService.readId(apiExecution.callApiMethod(testSessionStart));
    }

    public void testSessionFinish() throws IOException {
        Properties properties = new Properties();
        properties.put(JsonValues.TEST_IDS.getValue(), testId);
        String path = "src/test/resources/api/test_session/test_session.properties";
        FileOutputStream output = new FileOutputStream(path);
        properties.store(output, null);
        ApiExecution apiExecution = new ApiExecution();
        TestSessionFinish testSessionFinish = new TestSessionFinish(testRunId, testSessionId);
        testSessionFinish.setProperties(properties);
        apiExecution.expectStatus(testSessionFinish, HTTPStatusCode.OK);
        apiExecution.callApiMethod(testSessionFinish);
    }

    public void startTest(TestStatus status) throws IOException {
        testRunStart();
        testExecutionStart();
        testExecutionLogs();
        testExecutionFinish(status);
        testSessionStart();
        testRunExecutionFinish();
        testSessionFinish();
    }
}
