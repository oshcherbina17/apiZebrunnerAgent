package api.putMethods;

import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestExecutionLabels extends ApiBase {
    public TestExecutionLabels(String testRunId, String testId) {
        super("api/test_execution/labels/rq.json", null);
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
    }
}
