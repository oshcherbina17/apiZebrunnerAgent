package api.putMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestExecutionFinish extends ApiBase {
    public TestExecutionFinish(String testRunId, String testId, String result) {
        super("api/test_execution/put/rq.json", "api/test_execution/put/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
        addProperty("result", result);
    }
}
