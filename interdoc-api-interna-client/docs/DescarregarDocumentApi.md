# DescarregarDocumentApi

All URIs are relative to *http://localhost:8080/interdocapi/interna*

Method | HTTP request | Description
------------- | ------------- | -------------
[**descarregar**](DescarregarDocumentApi.md#descarregar) | **POST** /secured/documento/descargar | Descarrega un document a partir de la seva referencia
[**descarregar1**](DescarregarDocumentApi.md#descarregar1) | **GET** /secured/documento/download | Descarrega un document

<a name="descarregar"></a>
# **descarregar**
> String descarregar()

Descarrega un document a partir de la seva referencia

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.DescarregarDocumentApi;


DescarregarDocumentApi apiInstance = new DescarregarDocumentApi();
try {
    String result = apiInstance.descarregar();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DescarregarDocumentApi#descarregar");
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
 - **Accept**: application/json, application/xml

<a name="descarregar1"></a>
# **descarregar1**
> String descarregar1(fileName)

Descarrega un document

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.DescarregarDocumentApi;


DescarregarDocumentApi apiInstance = new DescarregarDocumentApi();
String fileName = "fileName_example"; // String | 
try {
    String result = apiInstance.descarregar1(fileName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DescarregarDocumentApi#descarregar1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **fileName** | **String**|  | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

