package eu.cqse.qcs.jiratasks;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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
	protected static <T> T createAPI(Class<T> api, String serverUrl, String userName, String password) {
		OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
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

}
