import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.enums.TestStatus;
import api.utils.ApiConnection;
import api.utils.AuthTokenService;

public class TestZebrunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    ApiConnection apiConnection = new ApiConnection();

    @Test()
    public void refreshTokenTest() {
        LOGGER.info("Authentication");
        AuthTokenService.refreshAuthToken();
    }

    @Test()
    public void runStartTest() {
        LOGGER.info("Test run start");
        apiConnection.testRunStart();
        Assert.assertTrue(apiConnection.getTestResultRun().equalsIgnoreCase(TestStatus.IN_PROGRESS.getStatus()),
                "Test statuses are not equals");
    }

    @Test()
    public void executionStartTest() {
        LOGGER.info("Test execution start");
        apiConnection.testRunStart();
        apiConnection.testExecutionStart();
        String result = apiConnection.getTestResultExecution();
        Assert.assertTrue(result.equalsIgnoreCase(TestStatus.IN_PROGRESS.getStatus()),
                "Test statuses are not equals");
    }

    @Test()
    public void executionFinishTest() {
        LOGGER.info("Test execution finish");
        apiConnection.testRunStart();
        apiConnection.testExecutionStart();
        apiConnection.testExecutionFinish(TestStatus.PASSED);
        String result = apiConnection.getTestResultExecution();
        Assert.assertTrue(result.equalsIgnoreCase(TestStatus.PASSED
                .getStatus()), "Test statuses are not equals");
    }

    @Test
    public void sampleSuccessTest() {
        String startLog = "Sample success test started";
        LOGGER.info(startLog);
        apiConnection.setMessage(startLog);
        apiConnection.runTest(TestStatus.PASSED);
        apiConnection.testExecutionLogs();
        String testStatus = apiConnection.getStatus();
        String finishLog="Sample success test finished";
        LOGGER.info(finishLog);
        apiConnection.setMessage(finishLog);
        apiConnection.testExecutionLogs();
        LOGGER.info(testStatus);
        apiConnection.setMessage(testStatus);
        apiConnection.testExecutionLogs();
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.PASSED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleFailTest() {
        String startLog = "Sample fail test started";
        LOGGER.info(startLog);
        apiConnection.setMessage(startLog);
        apiConnection.runTest(TestStatus.FAILED);
        apiConnection.testExecutionLogs();
        String testStatus = apiConnection.getStatus();
        String finishLog="Sample failed test finished";
        LOGGER.info(finishLog);
        apiConnection.setMessage(finishLog);
        apiConnection.testExecutionLogs();
        LOGGER.info(testStatus);
        apiConnection.setMessage(testStatus);
        apiConnection.testExecutionLogs();
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.FAILED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleSkippedTest() {
        String startLog = "Sample skipped test started";
        LOGGER.info(startLog);
        apiConnection.setMessage(startLog);
        apiConnection.runTest(TestStatus.SKIPPED);
        apiConnection.testExecutionLogs();
        String testStatus = apiConnection.getStatus();
        String finishLog="Sample skipped test finished";
        LOGGER.info(finishLog);
        apiConnection.setMessage(finishLog);
        apiConnection.testExecutionLogs();
        LOGGER.info(testStatus);
        apiConnection.setMessage(testStatus);
        apiConnection.testExecutionLogs();
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.FAILED.getStatus()),
                "Test statuses are not equals");
    }
}
