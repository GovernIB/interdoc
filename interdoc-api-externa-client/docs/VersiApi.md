# VersiApi

All URIs are relative to *http://localhost:8080/interdocapi/externa*

Method | HTTP request | Description
------------- | ------------- | -------------
[**versio**](VersiApi.md#versio) | **GET** /public/exemplepublic/versio | Versio de l&#x27;Aplicació

<a name="versio"></a>
# **versio**
> ExamplePojo versio(idioma)

Versio de l&#x27;Aplicació

### Example
```java
// Import classes:
//import es.caib.interdoc.apiexterna.client.services.ApiException;
//import es.caib.interdoc.apiexterna.client.api.VersiApi;


VersiApi apiInstance = new VersiApi();
String idioma = "idioma_example"; // String | Codi de l'idioma
try {
    ExamplePojo result = apiInstance.versio(idioma);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersiApi#versio");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idioma** | **String**| Codi de l&#x27;idioma | [optional]

### Return type

[**ExamplePojo**](ExamplePojo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

