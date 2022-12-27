

import api.enums.TestStatus;
import api.utils.ApiConnection;
import api.utils.AuthTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class TestZebrunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    ApiConnection apiConnection = new ApiConnection();

    @Test()
    public void refreshTokenTest() throws IOException {
        LOGGER.info("Authentication");
        AuthTokenService.refreshAuthToken();
    }

    @Test()
    public void runStartTest() throws IOException {
        LOGGER.info("Test run start");
       // AuthTokenService.getAuthToken();
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
    public void executionFinishTest() throws IOException {
        LOGGER.info("Test execution finish");
        apiConnection.testRunStart();
        apiConnection.testExecutionStart();
        apiConnection.testExecutionFinish(TestStatus.PASSED);
        String result = apiConnection.getTestResultExecution();
        Assert.assertTrue(result.equalsIgnoreCase(TestStatus.PASSED
                .getStatus()), "Test statuses are not equals");
    }

    @Test
    public void sampleSuccessTest() throws IOException {
        LOGGER.info("Sample success test started");
        apiConnection.runTest(TestStatus.PASSED);
        String testStatus = apiConnection.getStatus();
        LOGGER.info("Sample success test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.PASSED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleFailTest() throws IOException {
        LOGGER.info("Sample fail test started");
        apiConnection.runTest(TestStatus.FAILED);
        String testStatus = apiConnection.getStatus();
        LOGGER.info("Sample failed test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.FAILED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleSkippedTest() throws IOException {
        LOGGER.info("Sample skipped test started");
        //AuthTokenService.refreshAuthToken();
        apiConnection.runTest(TestStatus.SKIPPED);
        String testStatus = apiConnection.getStatus();
        LOGGER.info("Sample skipped test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.SKIPPED.getStatus()),
                "Test statuses are not equals");
    }
}
