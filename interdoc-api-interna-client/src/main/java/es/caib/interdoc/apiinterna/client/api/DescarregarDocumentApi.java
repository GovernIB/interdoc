package es.caib.interdoc.apiinterna.client.api;

import es.caib.interdoc.apiinterna.client.services.ApiException;
import es.caib.interdoc.apiinterna.client.services.ApiClient;
import es.caib.interdoc.apiinterna.client.services.Configuration;
import es.caib.interdoc.apiinterna.client.services.Pair;

import javax.ws.rs.core.GenericType;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DescarregarDocumentApi {
  private ApiClient apiClient;

  public DescarregarDocumentApi() {
    this(Configuration.getDefaultApiClient());
  }

  public DescarregarDocumentApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Descarrega un document a partir de la seva referencia
   * 
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String descarregar() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/secured/documento/descargar".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    final String[] localVarAccepts = {
      "application/json", "application/xml"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<String> localVarReturnType = new GenericType<String>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Descarrega un document
   * 
   * @param fileName  (optional)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String descarregar1(String fileName) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/secured/documento/download".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "fileName", fileName));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<String> localVarReturnType = new GenericType<String>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
