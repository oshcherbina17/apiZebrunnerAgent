package api.postMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestExecutionLogs extends ApiBase {

    public TestExecutionLogs(String testRunId, String testId) {
        super("api/test_execution/logs/rq.json", "api/test_execution/logs/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        addProperty("testId", testId);
    }
}
