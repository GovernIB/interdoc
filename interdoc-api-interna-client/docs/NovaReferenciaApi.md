# NovaReferenciaApi

All URIs are relative to *http://localhost:8080/interdocapi/interna*

Method | HTTP request | Description
------------- | ------------- | -------------
[**nova**](NovaReferenciaApi.md#nova) | **POST** /secured/referencia/new | Crear una nova referencia
[**nova1**](NovaReferenciaApi.md#nova1) | **POST** /secured/referencia/nova | Crear una nova referencia

<a name="nova"></a>
# **nova**
> String nova()

Crear una nova referencia

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.NovaReferenciaApi;


NovaReferenciaApi apiInstance = new NovaReferenciaApi();
try {
    String result = apiInstance.nova();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NovaReferenciaApi#nova");
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

<a name="nova1"></a>
# **nova1**
> String nova1(csv, uuid, aplicacioId, emisor, receptor, interessats, metadades, fitxer)

Crear una nova referencia

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.NovaReferenciaApi;


NovaReferenciaApi apiInstance = new NovaReferenciaApi();
String csv = "csv_example"; // String | CSV
String uuid = "uuid_example"; // String | UUID
String aplicacioId = "aplicacioId_example"; // String | aplicacioId
String emisor = "emisor_example"; // String | emisor
String receptor = "receptor_example"; // String | receptor
String interessats = "interessats_example"; // String | interessats
String metadades = "metadades_example"; // String | metadades
String fitxer = "fitxer_example"; // String | fitxer
try {
    String result = apiInstance.nova1(csv, uuid, aplicacioId, emisor, receptor, interessats, metadades, fitxer);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NovaReferenciaApi#nova1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **csv** | **String**| CSV | [optional]
 **uuid** | **String**| UUID | [optional]
 **aplicacioId** | **String**| aplicacioId | [optional]
 **emisor** | **String**| emisor | [optional]
 **receptor** | **String**| receptor | [optional]
 **interessats** | **String**| interessats | [optional]
 **metadades** | **String**| metadades | [optional]
 **fitxer** | **String**| fitxer | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

