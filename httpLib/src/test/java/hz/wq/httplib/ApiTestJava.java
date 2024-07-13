package hz.wq.httplib;

import org.junit.Test;

import hz.wq.httplib.jq.ApiDataFetchTest;

public class ApiTestJava {
    @Test
    public void testJavaApi() throws Exception {
        ApiDataFetchTest test = new ApiDataFetchTest();
        test.fetchData_Test_sendApi_login_java();

        int count = 10;
        while (true) {
            Thread.sleep(1000);
            count--;
            if (count < 0) {
                break;
            }
        }
    }
}
