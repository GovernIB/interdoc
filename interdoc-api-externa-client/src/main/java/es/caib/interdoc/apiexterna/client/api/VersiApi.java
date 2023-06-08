package es.caib.interdoc.apiexterna.client.api;

import es.caib.interdoc.apiexterna.client.services.ApiException;
import es.caib.interdoc.apiexterna.client.services.ApiClient;
import es.caib.interdoc.apiexterna.client.services.Configuration;
import es.caib.interdoc.apiexterna.client.services.Pair;

import javax.ws.rs.core.GenericType;

import es.caib.interdoc.apiexterna.client.model.ExamplePojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersiApi {
  private ApiClient apiClient;

  public VersiApi() {
    this(Configuration.getDefaultApiClient());
  }

  public VersiApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Versio de l&#x27;Aplicació
   * 
   * @param idioma Codi de l&#x27;idioma (optional)
   * @return ExamplePojo
   * @throws ApiException if fails to make API call
   */
  public ExamplePojo versio(String idioma) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/public/exemplepublic/versio".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "idioma", idioma));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ExamplePojo> localVarReturnType = new GenericType<ExamplePojo>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
