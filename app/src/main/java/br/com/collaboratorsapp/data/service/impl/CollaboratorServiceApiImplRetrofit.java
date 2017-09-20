package br.com.collaboratorsapp.data.service.impl;

import java.io.Console;
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

    @Override
    public void findAll(final CollaboratorCallback<List<Collaborator>> callback) {

        Call<List<Collaborator>> call = new HttpEndpointGenerator<CollaboratorEndpoint>()
                .gen(CollaboratorEndpoint.class).findAll();


        call.enqueue(new Callback<List<Collaborator>>() {
            @Override
            public void onResponse(Call<List<Collaborator>> call, Response<List<Collaborator>> response) {
                List<Collaborator> body = response.body();
                if (null != body) {
                    callback.onLoaded(body);
                } else {
                    callback.onLoaded(new ArrayList<Collaborator>());
                }
            }

            @Override
            public void onFailure(Call<List<Collaborator>> call, Throwable t) {
                //TODO
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
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
                //TODO
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
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
                String teste = "teste";
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
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {

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
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<Collaborator> call, Throwable t) {
            }
        });
    }
}
