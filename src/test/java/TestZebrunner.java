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
        apiConnection.runTest(TestStatus.PASSED);
        String startLog = "Sample success test started";
        apiConnection.logInfoAndSetMsg(startLog);
        String testStatus = apiConnection.getStatus();
        String finishLog = "Sample success test finished";
        apiConnection.logInfoAndSetMsg(finishLog);
        apiConnection.logInfoAndSetMsg(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.PASSED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleFailTest() {
        apiConnection.runTest(TestStatus.FAILED);
        String startLog = "Sample fail test started";
        apiConnection.logInfoAndSetMsg(startLog);
        String testStatus = apiConnection.getStatus();
        String finishLog = "Sample failed test finished";
        apiConnection.logInfoAndSetMsg(finishLog);
        apiConnection.logInfoAndSetMsg(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.FAILED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleSkippedTest() {
        apiConnection.runTest(TestStatus.SKIPPED);
        apiConnection.setStatus(TestStatus.SKIPPED.getStatus());
        String startLog = "Sample skipped test started";
        apiConnection.logInfoAndSetMsg(startLog);
        String testStatus = apiConnection.getStatus();
        String finishLog = "Sample skipped test finished";
        apiConnection.logInfoAndSetMsg(finishLog);
        apiConnection.logInfoAndSetMsg(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.SKIPPED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void multiplyWithLogsTest() {
        apiConnection.testRunStart();

        apiConnection.setStatus(TestStatus.PASSED.getStatus());
        apiConnection.runTests(TestStatus.PASSED);
        apiConnection.logInfoAndSetMsg("Sample success test started");
        String testStatus = apiConnection.getStatus();
        apiConnection.logInfoAndSetMsg("Sample success test finished");
        apiConnection.logInfoAndSetMsg(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.PASSED.getStatus()),
                "Test statuses are not equals");

        apiConnection.setStatus(TestStatus.FAILED.getStatus());
        apiConnection.runTests(TestStatus.FAILED);
        apiConnection.logInfoAndSetMsg("Sample fail test started");
        String testStatusFailed = apiConnection.getStatus();
        apiConnection.logInfoAndSetMsg("Sample failed test finished");
        apiConnection.logInfoAndSetMsg(testStatusFailed);
        Assert.assertTrue(testStatusFailed.equalsIgnoreCase(TestStatus.FAILED.getStatus()),
                "Test statuses are not equals");

        apiConnection.setStatus(TestStatus.SKIPPED.getStatus());
        apiConnection.runTests(TestStatus.SKIPPED);
        apiConnection.logInfoAndSetMsg("Sample skipped test started");
        String testStatusSkip = apiConnection.getStatus();
        apiConnection.logInfoAndSetMsg("Sample skipped test finished");
        apiConnection.logInfoAndSetMsg(testStatusSkip);
        Assert.assertTrue(testStatusSkip.equalsIgnoreCase(TestStatus.SKIPPED.getStatus()),
                "Test statuses are not equals");

        apiConnection.testRunExecutionFinish();
        apiConnection.testSessionFinish();
    }
}
