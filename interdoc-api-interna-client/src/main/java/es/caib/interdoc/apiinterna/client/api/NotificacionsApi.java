package es.caib.interdoc.apiinterna.client.api;

import es.caib.interdoc.apiinterna.client.services.ApiException;
import es.caib.interdoc.apiinterna.client.services.ApiClient;
import es.caib.interdoc.apiinterna.client.services.Configuration;
import es.caib.interdoc.apiinterna.client.services.Pair;

import javax.ws.rs.core.GenericType;

import es.caib.interdoc.apiinterna.client.model.SendMessageResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificacionsApi {
  private ApiClient apiClient;

  public NotificacionsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public NotificacionsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Envia un missatge al mòbil del ciutada a traves de l&#x27;App de Carpeta.
   * 
   * @param notificationParameters Exemple de Llistat d&#x27;String (required)
   * @param langError Idioma en que s&#x27;enviaran els missatges d&#x27;error (required)
   * @return SendMessageResult
   * @throws ApiException if fails to make API call
   */
  public SendMessageResult sendNotificationToMobile(List<String> notificationParameters, String langError) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'notificationParameters' is set
    if (notificationParameters == null) {
      throw new ApiException(400, "Missing the required parameter 'notificationParameters' when calling sendNotificationToMobile");
    }
    // verify the required parameter 'langError' is set
    if (langError == null) {
      throw new ApiException(400, "Missing the required parameter 'langError' when calling sendNotificationToMobile");
    }
    // create path and map variables
    String localVarPath = "/secured/exempleenum/sendnotificationtomobile".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("multi", "notificationParameters", notificationParameters));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "langError", langError));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "BasicAuth" };

    GenericType<SendMessageResult> localVarReturnType = new GenericType<SendMessageResult>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
