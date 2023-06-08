# VersiApi

All URIs are relative to *http://localhost:8080/interdocapi/interna*

Method | HTTP request | Description
------------- | ------------- | -------------
[**versio**](VersiApi.md#versio) | **GET** /public/exemplepublic/versio | Versio de l&#x27;Aplicació
[**versio1**](VersiApi.md#versio1) | **GET** /secured/documento/versio | Versio de l&#x27;Aplicació
[**versio2**](VersiApi.md#versio2) | **GET** /secured/exempleenum/versio | Versio de l&#x27;Aplicació
[**versio3**](VersiApi.md#versio3) | **GET** /secured/referencia/versio | Versio de l&#x27;Aplicació

<a name="versio"></a>
# **versio**
> ExamplePojo versio(idioma)

Versio de l&#x27;Aplicació

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.VersiApi;


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

<a name="versio1"></a>
# **versio1**
> String versio1(idioma)

Versio de l&#x27;Aplicació

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.VersiApi;


VersiApi apiInstance = new VersiApi();
String idioma = "idioma_example"; // String | Codi de l'idioma
try {
    String result = apiInstance.versio1(idioma);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersiApi#versio1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idioma** | **String**| Codi de l&#x27;idioma | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="versio2"></a>
# **versio2**
> ExamplePojo versio2(idioma)

Versio de l&#x27;Aplicació

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.VersiApi;


VersiApi apiInstance = new VersiApi();
String idioma = "idioma_example"; // String | Codi de l'idioma
try {
    ExamplePojo result = apiInstance.versio2(idioma);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersiApi#versio2");
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

<a name="versio3"></a>
# **versio3**
> String versio3(idioma)

Versio de l&#x27;Aplicació

### Example
```java
// Import classes:
//import es.caib.interdoc.apiinterna.client.services.ApiException;
//import es.caib.interdoc.apiinterna.client.api.VersiApi;


VersiApi apiInstance = new VersiApi();
String idioma = "idioma_example"; // String | Codi de l'idioma
try {
    String result = apiInstance.versio3(idioma);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersiApi#versio3");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idioma** | **String**| Codi de l&#x27;idioma | [optional]

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

