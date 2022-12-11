
import api.enums.HTTPStatusCode;
import api.enums.JsonValues;
import api.enums.TestStatus;
import api.postMethods.TestExecutionLogs;
import api.postMethods.TestExecutionStart;
import api.postMethods.TestRunStart;
import api.putMethods.TestExecutionFinish;
import api.utils.ApiConnection;
import api.utils.ApiExecution;
import api.utils.AuthTokenService;
import api.utils.JsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class TestZebrunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    ApiConnection apiConnection = new ApiConnection();

    @Test()
    public void refreshTokenTest() throws IOException {
        LOGGER.info("Authentication");
        AuthTokenService.refreshAuthToken();
    }

    @Test()
    public void runStartTest() {
        LOGGER.info("Test run start");
        ApiExecution apiExecution = new ApiExecution();
        TestRunStart testRunStart = new TestRunStart();
        apiExecution.expectStatus(testRunStart, HTTPStatusCode.OK);
        String testStatus = JsonService.readTestStatus(apiExecution.callApiMethod(testRunStart));
        testRunStart.validateResponse();
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatus.IN_PROGRESS.getStatus()),
                "Test statuses are not equals");
    }

    @Test()
    public void executionStartTest() {
        LOGGER.info("Test execution start");
        ApiExecution apiExecution = new ApiExecution();
        TestExecutionStart testExecutionStart = new TestExecutionStart();
        apiExecution.expectStatus(testExecutionStart, HTTPStatusCode.OK);
        String result = JsonService.readResult(apiExecution.callApiMethod(testExecutionStart));
        testExecutionStart.validateResponse();
        Assert.assertTrue(result.equalsIgnoreCase(TestStatus.IN_PROGRESS.getStatus()),
                "Test statuses are not equals");
    }

    @Test()
    public void executionFinishTest() throws IOException {
        LOGGER.info("Test execution finish");
        Properties properties = new Properties();
        properties.put(JsonValues.TEST_RESULT.getValue(), TestStatus.PASSED.getStatus());
        String path = "src/test/resources/api/test_execution/put/test_execution_finish.properties";
        FileOutputStream output = new FileOutputStream(path);
        properties.store(output, null);
        ApiExecution apiExecution = new ApiExecution();
        TestExecutionFinish testExecutionFinish = new TestExecutionFinish();
        testExecutionFinish.setProperties(properties);
        apiExecution.expectStatus(testExecutionFinish, HTTPStatusCode.OK);
        String result = JsonService.readResult(apiExecution.callApiMethod(testExecutionFinish));
        testExecutionFinish.validateResponse();
        apiConnection.testExecutionLogs();
        String s = apiConnection.getTestRunId();
        TestExecutionLogs testExecutionLogs = new TestExecutionLogs(s);
        Assert.assertTrue(result.equalsIgnoreCase(TestStatus.PASSED.getStatus()),
                "Test statuses are not equals");
    }

    @Test
    public void sampleSuccessTest() throws IOException {
        LOGGER.info("Sample success test started");
        Assert.assertEquals("1", "1");
        apiConnection.testExecutionLogs();


    }

    @Test
    public void sampleFailTest() throws IOException {
        LOGGER.info("Sample fail test started");
    }

    @Test
    public void sampleSkippedTest() throws IOException {
        LOGGER.info("Sample skipped test started");
    }
}
