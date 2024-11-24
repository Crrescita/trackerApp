package retrofitAPI;

import android.content.Context;

import com.crrescita.tel.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
public class WebRequest {
    public static APIInterface m_ApiInterface;

    public WebRequest(Context context) {
        m_ApiInterface = RestClient.getClient();
    }

    private static class RestClient {
        public static APIInterface getClient() {
            String url = BuildConfig.BASE_URL;
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            System.setProperty("http.keepAlive", "false");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit.create(APIInterface.class);
        }

        public static OkHttpClient.Builder getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                builder.connectTimeout(60, TimeUnit.MINUTES);
                builder.readTimeout(60, TimeUnit.MINUTES);
                builder.writeTimeout(60, TimeUnit.MINUTES);
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                return builder;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public interface APIInterface {

        @POST("login")
        Call<ResponseBody> getLogin(@Header("Content-Type") String con_type,
                                    @Header("versionnumber") String versionnumber,
                                    @Body String jsonObject);

        @POST("setCoordinates")
        Call<ResponseBody> setCoordinate(@Header("Content-Type") String con_type,
                                         @Header("authorization") String authorization,
                                         @Body String jsonObject);

        @POST("sendLiveLocation")
        Call<ResponseBody> sendLiveLocation(@Header("Content-Type") String con_type,
                                         @Body String jsonObject);

        @POST("checkIn")
        Call<ResponseBody> checkIn(@Header("Content-Type") String con_type,
                                   @Header("authorization") String authorization,
                                   @Body String jsonObject);

        @POST("checkOut")
        Call<ResponseBody> checkOut(@Header("Content-Type") String con_type,
                                    @Header("authorization") String authorization,
                                    @Body String jsonObject);

        @POST("forgotPassword")
        Call<ResponseBody> forgotPassword(@Header("Content-Type") String con_type,
                                          @Header("versionnumber") String versionnumber,
                                          @Body String jsonObject);

        @POST("validateResetCode")
        Call<ResponseBody> validateResetCode(@Header("Content-Type") String con_type,
                                             @Header("versionnumber") String versionnumber,
                                             @Body String jsonObject);

        @POST("resetPassword")
        Call<ResponseBody> resetPassword(@Header("Content-Type") String con_type,
                                         @Header("versionnumber") String versionnumber,
                                         @Body String jsonObject);


        @GET("getEmployeeCompany")
        Call<ResponseBody> getEmployeeCompanay(@Header("Content-Type") String con_type,
                                               @Header("authorization") String authorization);

        @POST("changePassword")
        Call<ResponseBody> changePassword(@Header("Content-Type") String con_type,
                                          @Header("authorization") String authorization,
                                          @Body String jsonObject);

        @GET("getEmployeeAttendance")
        Call<ResponseBody> getEmployeeAttendance(@Header("Content-Type") String con_type,
                                                 @Header("authorization") String authorization,
                                                 @Query("date") String date);

        @GET("getEmployee")
        Call<ResponseBody> getUserProfile(@Header("Content-Type") String con_type,
                                          @Header("authorization") String authorization);

        @Multipart
        @PUT("updateEmployee")
        Call<ResponseBody> updateUserProfile(
                @Header("authorization") String authorization,
                @Part("name") RequestBody name,
                @Part("mobile") RequestBody mobile,
                @Part("email") RequestBody email,
                @Part MultipartBody.Part image // Optional
        );

        @GET("myRecord")
        Call<ResponseBody> getMyRecord(@Header("Content-Type") String con_type,
                                          @Header("authorization") String authorization);

        @GET("checkInDetail")
        Call<ResponseBody> getCheckInDetails(@Header("Content-Type") String con_type,
                                          @Header("authorization") String authorization);


        @Multipart
        @POST("support")
        Call<ResponseBody> supportUs(
                @Header("authorization") String authorization,
                @Part("message") RequestBody message,
                @Part MultipartBody.Part image // Optional
        );


        @POST("setFcmToken")
        Call<ResponseBody> setFcmToken(@Header("Content-Type") String con_type,
                                          @Header("authorization") String authorization,
                                          @Body String jsonObject);


        @POST("app-version")
        Call<ResponseBody> checkAppVersion(@Header("Content-Type") String con_type,
                                          @Header("authorization") String authorization,
                                          @Body String jsonObject);

        @POST("logout")
        Call<ResponseBody> logoutAPI(@Header("Content-Type") String con_type,
                                       @Header("authorization") String authorization,
                                       @Body String jsonObject);

        @POST("account/status")
        Call<ResponseBody> deleteAccount(@Header("Content-Type") String con_type,
                                             @Header("authorization") String authorization,
                                         @Body String jsonObject);


    }

}
