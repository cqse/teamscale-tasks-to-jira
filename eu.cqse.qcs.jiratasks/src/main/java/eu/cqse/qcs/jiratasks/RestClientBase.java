package eu.cqse.qcs.jiratasks;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Base class for REST clients using {@link Retrofit}
 */
public abstract class RestClientBase {

	/** Settings */
	protected final TasksToJiraSettings settings;

	/** Name for the destination, used for logging */
	private final String destinationName;

	public RestClientBase(String destinantionName, TasksToJiraSettings settings) {
		this.settings = settings;
		this.destinationName = destinantionName;
	}

	/**
	 * Checks if the given response is successful and throws a {@link IOException}
	 * otherwise.
	 */
	protected void throwErrorOnUnsuccessfulResponse(Response<?> response) throws IOException {
		if (!response.isSuccessful()) {
			throw new IOException("An error occurred while trying to access " + destinationName + ". HTTP Status Code "
					+ response.code() + response.errorBody().string());
		}
	}

	/**
	 * Creates the API implementation for the given interface class.
	 */
	protected static <T> T createAPI(Class<T> api, String serverUrl, String userName, String password,
			boolean allowAllSslCertificates) {
		OkHttpClient okHttpClient = getHttpClientBuilder(allowAllSslCertificates).addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {
				Request originalRequest = chain.request();

				Request.Builder builder = originalRequest.newBuilder().header("Authorization",
						Credentials.basic(userName, password));

				Request newRequest = builder.build();
				return chain.proceed(newRequest);
			}
		}).build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(serverUrl).client(okHttpClient)
				.addConverterFactory(JacksonConverterFactory.create()).build();
		return retrofit.create(api);
	}

	/**
	 * Creates a builder for the HTTP client.
	 * 
	 * @param trustAllSslCertificates if <code>true</code>, any SSL certificates are
	 *                                trusted
	 */
	private static Builder getHttpClientBuilder(boolean trustAllSslCertificates) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (!trustAllSslCertificates) {
			return builder;
		}

		// Builder to trust all SSL certificates, as suggested in
		// https://stackoverflow.com/questions/25509296/trusting-all-certificates-with-okhttp

		// Create a trust manager that does not validate certificate chains
		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}
		} };

		try {
			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
			builder.hostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			return builder;

		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new RuntimeException(e);
		}
	}

}
