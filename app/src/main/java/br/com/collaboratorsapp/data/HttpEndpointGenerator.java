package br.com.collaboratorsapp.data;

import br.com.collaboratorsapp.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpEndpointGenerator<T> {

    public T gen(Class<T> clazz) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(provideOkHttpClient())
                .build();

        return retrofit.create(clazz);
    }
}
