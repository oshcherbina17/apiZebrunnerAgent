package api.deleteMethods;

import api.utils.AuthTokenService;
import api.ApiBase;
import com.qaprosoft.carina.core.foundation.utils.R;

public class TestRegistrationDelete extends ApiBase {
    public TestRegistrationDelete() {
        super(null, null);
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        String testRunId= AuthTokenService.getTestRunId();
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", AuthTokenService.getTestId(testRunId));
    }
}
