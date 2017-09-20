package br.com.collaboratorsapp.data.service.impl;

import android.os.AsyncTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import br.com.collaboratorsapp.Constants;
import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.data.service.CollaboratorServiceApi;

public class CollaboratorServiceApiImplSpring implements CollaboratorServiceApi {

    @Override
    public void findAll(CollaboratorCallback<List<Collaborator>> callback) {
        new Execution(HttpMethod.GET, callback).execute();
    }

    @Override
    public void findOne(Integer code, CollaboratorCallback<Collaborator> callback) {
        new Execution(HttpMethod.GET, callback).execute(code);
    }

    @Override
    public void create(Collaborator collaborator, CollaboratorCallback<Collaborator> callback) {
        new Execution(HttpMethod.POST, callback).execute(collaborator);
    }

    @Override
    public void update(Collaborator collaborator, CollaboratorCallback<Collaborator> callback) {
        new Execution(HttpMethod.POST, callback).execute(collaborator);
    }

    @Override
    public void delete(Collaborator collaborator, CollaboratorCallback<Collaborator> callback) {
    }

    class Execution extends AsyncTask<Object, Void, Object> {

        private RestTemplate mTemplate;
        private HttpMethod mMethod;
        private CollaboratorCallback callback;

        public Execution(HttpMethod methodType, CollaboratorCallback callback) {
            this.mMethod = methodType;
            this.callback = callback;
            mTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
            mTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        }

        @Override
        protected Object doInBackground(Object... params) {

            if (params.length == 0) {

                List<Collaborator> collaboratorList = null;
                if (mMethod.equals(HttpMethod.GET)) {
                    ResponseEntity<Collaborator[]> responseEntity = mTemplate.getForEntity(
                            Constants.BASE_SERVICE_URL.concat("collaborator"), Collaborator[].class);
                    Collaborator[] objects = responseEntity.getBody();
                    collaboratorList = Arrays.asList(objects);
                }
                return collaboratorList;

            } else {
                Collaborator collaborator = null;

                if (mMethod.equals(HttpMethod.GET)) {
                    Integer collaboratorCode = (Integer) params[0];

                    ResponseEntity<Collaborator> responseEntity = mTemplate.getForEntity(
                            Constants.BASE_SERVICE_URL.concat("collaborator/")
                                    .concat(collaboratorCode.toString()), Collaborator.class);

                    collaborator = responseEntity.getBody();

                } else if (mMethod.equals(HttpMethod.POST)) {
                    Collaborator body = (Collaborator) params[0];

                    HttpEntity<Collaborator> httpEntity = new HttpEntity<>(body);
                    ResponseEntity<Collaborator> responseEntity = mTemplate.postForEntity(Constants
                            .BASE_SERVICE_URL.concat("collaborator"), httpEntity, Collaborator.class);

                    collaborator = responseEntity.getBody();

                } else if (mMethod.equals(HttpMethod.PUT)) {
                } else if (mMethod.equals(HttpMethod.DELETE)) {
                }
                return collaborator;
            }
        }

        @Override
        protected void onPostExecute(Object object) {
            if (null != object) {
                if (object instanceof List) {
                    List<Collaborator> collaboratorList = (List<Collaborator>) object;
                    callback.onLoaded(collaboratorList);
                } else if (object instanceof Collaborator) {
                    Collaborator collaborator = (Collaborator) object;
                    callback.onLoaded(collaborator);
                }
            }
        }
    }
}
