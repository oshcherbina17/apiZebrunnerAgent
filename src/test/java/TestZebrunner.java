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

    @Test
    public void multiplyTest() {
        apiConnection.testRunStart();

        apiConnection.testExecutionStart();
        apiConnection.testExecutionFinish(TestStatus.PASSED);
        apiConnection.testSessionStart();

        apiConnection.testExecutionStart();
        apiConnection.testExecutionFinish(TestStatus.FAILED);
        apiConnection.testSessionStart();

        apiConnection.testExecutionStart();
        apiConnection.testExecutionFinish(TestStatus.SKIPPED);
        apiConnection.testSessionStart();

        apiConnection.testRunExecutionFinish();
        apiConnection.testSessionFinish();
    }

    @Test
    public void multiplyWithLogsTest() {
        apiConnection.testRunStart();

        String startLog = "Sample success test started";
        LOGGER.info(startLog);
        apiConnection.setMessage(startLog);
        apiConnection.setStatus(TestStatus.PASSED.getStatus());
        apiConnection.runTests(TestStatus.PASSED);
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

        String startLogFail = "Sample fail test started";
        LOGGER.info(startLogFail);
        apiConnection.setMessage(startLogFail);
        apiConnection.setStatus(TestStatus.FAILED.getStatus());
        apiConnection.runTests(TestStatus.FAILED);
        apiConnection.testExecutionLogs();
        String testStatusFailed = apiConnection.getStatus();
        String finishLogFail="Sample failed test finished";
        LOGGER.info(finishLogFail);
        apiConnection.setMessage(finishLogFail);
        apiConnection.testExecutionLogs();
        LOGGER.info(testStatusFailed);
        apiConnection.setMessage(testStatusFailed);
        apiConnection.testExecutionLogs();
        Assert.assertTrue(testStatusFailed.equalsIgnoreCase(TestStatus.FAILED.getStatus()),
                "Test statuses are not equals");

        String startLogSkip = "Sample skipped test started";
        LOGGER.info(startLogSkip);
        apiConnection.setMessage(startLogSkip);
        apiConnection.setStatus(TestStatus.SKIPPED.getStatus());
        apiConnection.runTests(TestStatus.SKIPPED);
        apiConnection.testExecutionLogs();
        String testStatusSkip = apiConnection.getStatus();
        String finishLogSkip="Sample skipped test finished";
        LOGGER.info(finishLogSkip);
        apiConnection.setMessage(finishLogSkip);
        apiConnection.testExecutionLogs();
        LOGGER.info(testStatusSkip);
        apiConnection.setMessage(testStatusSkip);
        apiConnection.testExecutionLogs();
        Assert.assertTrue(testStatusSkip.equalsIgnoreCase(TestStatus.SKIPPED.getStatus()),
                "Test statuses are not equals");

        apiConnection.testRunExecutionFinish();
        apiConnection.testSessionFinish();
    }
}
