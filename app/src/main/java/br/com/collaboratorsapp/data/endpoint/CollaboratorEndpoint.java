package br.com.collaboratorsapp.data.endpoint;

import java.util.List;

import br.com.collaboratorsapp.data.entity.Collaborator;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CollaboratorEndpoint {

    @POST("collaborator")
    Call<Collaborator> create(@Body Collaborator collaborator);

    @GET("collaborator/{collaboratorCode}")
    Call<Collaborator> findOne(@Path("collaboratorCode") Integer collaboratorCode);

    @GET("collaborator")
    Call<List<Collaborator>> findAll();

    @PUT("collaborator")
    Call<Collaborator> update(@Body Collaborator collaborator);

    @DELETE("collaborator/{collaboratorCode}")
    Call<Collaborator> delete(@Path("collaboratorCode") Integer collaboratorCode);

}
