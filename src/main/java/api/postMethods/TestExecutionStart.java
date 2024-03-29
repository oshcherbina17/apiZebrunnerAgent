package api.postMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestExecutionStart extends ApiBase {
    public TestExecutionStart(String testRunId) {
        super("api/test_execution/post/rq.json", "api/test_execution/post/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
    }
}
