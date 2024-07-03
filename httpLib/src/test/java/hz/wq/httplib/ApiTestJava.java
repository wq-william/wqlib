package hz.wq.httplib;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import hz.wq.httplib.bean.ApiResponse;
import hz.wq.httplib.interfaces.IDataProcessing;
import hz.wq.httplib.utils.HttpUtil;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.GlobalScope;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
