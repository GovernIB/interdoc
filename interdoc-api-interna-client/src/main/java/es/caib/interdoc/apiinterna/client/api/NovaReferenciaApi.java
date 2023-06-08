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

public class NovaReferenciaApi {
  private ApiClient apiClient;

  public NovaReferenciaApi() {
    this(Configuration.getDefaultApiClient());
  }

  public NovaReferenciaApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Crear una nova referencia
   * 
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String nova() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/secured/referencia/new".replaceAll("\\{format\\}","json");

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
   * Crear una nova referencia
   * 
   * @param csv CSV (optional)
   * @param uuid UUID (optional)
   * @param aplicacioId aplicacioId (optional)
   * @param emisor emisor (optional)
   * @param receptor receptor (optional)
   * @param interessats interessats (optional)
   * @param metadades metadades (optional)
   * @param fitxer fitxer (optional)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String nova1(String csv, String uuid, String aplicacioId, String emisor, String receptor, String interessats, String metadades, String fitxer) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/secured/referencia/nova".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "csv", csv));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "uuid", uuid));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "aplicacioId", aplicacioId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "emisor", emisor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "receptor", receptor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "interessats", interessats));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "metadades", metadades));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "fitxer", fitxer));

    
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
}
