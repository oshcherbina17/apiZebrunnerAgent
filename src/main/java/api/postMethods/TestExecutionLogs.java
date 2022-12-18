package api.postMethods;

import api.ApiBase;
import api.utils.Helper;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestExecutionLogs extends ApiBase {
    public TestExecutionLogs(String testRunId) {
        super("api/test_execution/logs/rq.json", "api/test_execution/logs/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        addProperty("testId", Helper.getHelper().getTestId());
    }
}
