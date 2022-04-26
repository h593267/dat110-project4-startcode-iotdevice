package no.hvl.dat110.aciotdevice.client;

import okhttp3.MediaType;
import java.io.IOException;



import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;

public class RestClient {

	
	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";
	public static final MediaType JSON = MediaType.parse("application/json; charset=UTF-8");

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message

        AccessMessage accessMsg = new AccessMessage(message);

        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
    

        RequestBody body = RequestBody.create(JSON, gson.toJson(accessMsg));

        Request request = new Request.Builder()
                .url("http://" + Configuration.host + ":" + Configuration.port + logpath)
                .post(body)
                .build();

        System.out.println(request);

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
	
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {
		
		AccessCode code = null;

		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		
		Request request = new Request.Builder().url("http://localhost:8080" + codepath).get().build();
		
		System.out.println(request);
		try(Response response = client.newCall(request).execute()){
			String body = response.body().string();
			System.out.println(body);
			code = gson.fromJson(body, AccessCode.class);
		} catch(JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		
		return code;
	}
}