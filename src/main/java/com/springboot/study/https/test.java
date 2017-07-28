package com.springboot.study.https;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;

/**
 * Created by Pan on 16-12-7.
 * Description:
 * Version:
 */
public class test {

    @Test
    public void post() {
        String params = "{\n" +
                "    \"company_account\": \"cevi\",\n" +
                "    \"name\": \"中国奥运健儿加油\",\n" +
                "    \"identity_card_number\": \"362321197508040219\",\n" +
                "    \"cell_phone_number\": \"13122224110\",\n" +
                "    \"first_contact_name\":\"测试\",\n" +
                "    \"first_contact_type\":\"SIBBING\",\n" +
                "    \"first_contact_cell_phone_number\":\"13122234110\",\n" +
                "    \"second_contact_name\":\"测试\",\n" +
                "    \"second_contact_type\":\"SIBBING\",\n" +
                "    \"second_contact_cell_phone_number\":\"13122434110\"\n" +
                "}";
        Request request = new Request();
        request.setMethod(HttpConst.Method.POST)
                .putHeaderField(HttpConst.Header.ACCEPT, "application/json")
                .putHeaderField(HttpConst.Header.CONTENT_TYPE, "application/json")
                .setJsonContent(params)
                .setTimeOut(5000 * 100)
                .setUrl("https://collect.hulushuju.com/api/applications/mobile");

        try (CloseableHttpResponse response = HttpClientUtil.createHttpClient(HttpClientUtil.HTTP_TYPE.HTTPS)
                .execute(HttpClientUtil.getHttpUriRequest(request))) {

            String result=HttpClientUtil.getResponseResult(response);
            System.out.println(result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
