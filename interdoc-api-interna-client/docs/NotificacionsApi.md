# NotificacionsApi

All URIs are relative to *http://localhost:8080/interdocapi/interna*

Method | HTTP request | Description
------------- | ------------- | -------------
[**sendNotificationToMobile**](NotificacionsApi.md#sendNotificationToMobile) | **GET** /secured/exempleenum/sendnotificationtomobile | Envia un missatge al mòbil del ciutada a traves de l&#x27;App de Carpeta.

<a name="sendNotificationToMobile"></a>
# **sendNotificationToMobile**
> SendMessageResult sendNotificationToMobile(notificationParameters, langError)

Envia un missatge al mòbil del ciutada a traves de l&#x27;App de Carpeta.

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiClient;
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.services.Configuration;
//import es.caib.interdoc.apiinterna.client.services.auth.*;
//import es.caib.interdoc.apiinterna.client.api.NotificacionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: BasicAuth
HttpBasicAuth BasicAuth = (HttpBasicAuth) defaultClient.getAuthentication("BasicAuth");
BasicAuth.setUsername("YOUR USERNAME");
BasicAuth.setPassword("YOUR PASSWORD");

NotificacionsApi apiInstance = new NotificacionsApi();
List<String> notificationParameters = Arrays.asList("notificationParameters_example"); // List<String> | Exemple de Llistat d'String
String langError = "langError_example"; // String | Idioma en que s'enviaran els missatges d'error
try {
    SendMessageResult result = apiInstance.sendNotificationToMobile(notificationParameters, langError);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NotificacionsApi#sendNotificationToMobile");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **notificationParameters** | [**List&lt;String&gt;**](String.md)| Exemple de Llistat d&#x27;String |
 **langError** | **String**| Idioma en que s&#x27;enviaran els missatges d&#x27;error |

### Return type

[**SendMessageResult**](SendMessageResult.md)

### Authorization

[BasicAuth](../README.md#BasicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

