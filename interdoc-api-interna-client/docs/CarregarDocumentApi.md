# CarregarDocumentApi

All URIs are relative to *http://localhost:8080/interdocapi/interna*

Method | HTTP request | Description
------------- | ------------- | -------------
[**carregar**](CarregarDocumentApi.md#carregar) | **POST** /secured/documento/upload | Carrega un document

<a name="carregar"></a>
# **carregar**
> String carregar()

Carrega un document

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.CarregarDocumentApi;


CarregarDocumentApi apiInstance = new CarregarDocumentApi();
try {
    String result = apiInstance.carregar();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CarregarDocumentApi#carregar");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

