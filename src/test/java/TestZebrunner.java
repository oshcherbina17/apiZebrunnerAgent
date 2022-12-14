

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
                .getStatus()), "Actual test result differs from the expected one");
    }

    @Test
    public void sampleSuccessTest() throws IOException {
        LOGGER.info("Sample success test started");
        Assert.assertEquals("1", "1");
        apiConnection.testExecutionLogs();
    }

    @Test
    public void sampleFailTest() {
        LOGGER.info("Sample fail test started");
    }

    @Test
    public void sampleSkippedTest() {
        LOGGER.info("Sample skipped test started");
    }
}
