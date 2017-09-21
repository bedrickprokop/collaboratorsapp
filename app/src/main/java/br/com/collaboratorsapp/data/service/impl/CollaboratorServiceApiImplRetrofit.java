package br.com.collaboratorsapp.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.collaboratorsapp.data.HttpEndpointGenerator;
import br.com.collaboratorsapp.data.endpoint.CollaboratorEndpoint;
import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.data.service.CollaboratorServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollaboratorServiceApiImplRetrofit implements CollaboratorServiceApi {

    //SERVER    - http://www.codingpedia.org/ama/error-handling-in-rest-api-with-jersey/
    //ANDROID   - https://futurestud.io/tutorials/retrofit-2-simple-error-handling

    @Override
    public void findAll(final CollaboratorCallback<List<Collaborator>> callback) {

        Call<List<Collaborator>> call = new HttpEndpointGenerator<CollaboratorEndpoint>()
                .gen(CollaboratorEndpoint.class).findAll();


        call.enqueue(new Callback<List<Collaborator>>() {
            @Override
            public void onResponse(Call<List<Collaborator>> call, Response<List<Collaborator>> response) {
                List<Collaborator> body = response.body();

                //TODO fazer tratamento de erros do servidor - analisar statusCode, body e errorBody
                //TODO criar uma entidade generica para tratamento de responses

                if (null != body) {
                    callback.onLoaded(body);
                } else {
                    callback.onLoaded(new ArrayList<Collaborator>());
                }
            }

            @Override
            public void onFailure(Call<List<Collaborator>> call, Throwable t) {
                //TODO callback de falhas do aparato em chamar o serviço - como erro de conexão
            }
        });
    }

    @Override
    public void findOne(Integer code, final CollaboratorCallback<Collaborator> callback) {
        Call<Collaborator> call = new HttpEndpointGenerator<CollaboratorEndpoint>()
                .gen(CollaboratorEndpoint.class).findOne(code);

        call.enqueue(new Callback<Collaborator>() {
            @Override
            public void onResponse(Call<Collaborator> call, Response<Collaborator> response) {

                //TODO fazer tratamento de erros do servidor - analisar statusCode, body e errorBody
                //TODO criar uma entidade generica para tratamento de responses

                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
                //TODO callback de falhas do aparato em chamar o serviço - como erro de conexão
            }
        });
    }

    @Override
    public void create(Collaborator collaborator, final CollaboratorCallback<Collaborator> callback) {
        Call<Collaborator> call = new HttpEndpointGenerator<CollaboratorEndpoint>()
                .gen(CollaboratorEndpoint.class).create(collaborator);

        call.enqueue(new Callback<Collaborator>() {
            @Override
            public void onResponse(Call<Collaborator> call, Response<Collaborator> response) {

                //TODO fazer tratamento de erros do servidor - analisar statusCode, body e errorBody
                //TODO criar uma entidade generica para tratamento de responses
                //if (response.isSuccessful()) {

                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
                //TODO callback de falhas do aparato em chamar o serviço - como erro de conexão
            }
        });
    }

    @Override
    public void update(Collaborator collaborator, final CollaboratorCallback<Collaborator> callback) {
        Call<Collaborator> call = new HttpEndpointGenerator<CollaboratorEndpoint>()
                .gen(CollaboratorEndpoint.class).update(collaborator);

        call.enqueue(new Callback<Collaborator>() {
            @Override
            public void onResponse(Call<Collaborator> call, Response<Collaborator> response) {
                //TODO fazer tratamento de erros do servidor - analisar statusCode, body e errorBody
                //TODO criar uma entidade generica para tratamento de responses

                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
                //TODO callback de falhas do aparato em chamar o serviço - como erro de conexão
            }
        });
    }

    @Override
    public void delete(Collaborator collaborator, final CollaboratorCallback<Collaborator> callback) {
        Call<Collaborator> call = new HttpEndpointGenerator<CollaboratorEndpoint>()
                .gen(CollaboratorEndpoint.class).delete(collaborator.getCode());

        call.enqueue(new Callback<Collaborator>() {
            @Override
            public void onResponse(Call<Collaborator> call, Response<Collaborator> response) {
                //TODO fazer tratamento de erros do servidor - analisar statusCode, body e errorBody
                //TODO criar uma entidade generica para tratamento de responses

                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
                //TODO callback de falhas do aparato em chamar o serviço - como erro de conexão
            }
        });
    }
}
