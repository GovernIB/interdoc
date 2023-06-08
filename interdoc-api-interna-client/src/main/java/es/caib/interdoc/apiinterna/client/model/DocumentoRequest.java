/*
 * API REST INTERNA de interdoc
 * Conjunt de Serveis REST de interdoc per ser accedits des de l'interior
 *
 * OpenAPI spec version: 1.0.0
 * Contact: otae@fundaciobit.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package es.caib.interdoc.apiinterna.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * DocumentoRequest
 */


public class DocumentoRequest {
  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("documento")
  private String documento = null;

  @JsonProperty("mime")
  private String mime = null;

  public DocumentoRequest nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

   /**
   * Get nombre
   * @return nombre
  **/
  @Schema(description = "")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public DocumentoRequest documento(String documento) {
    this.documento = documento;
    return this;
  }

   /**
   * Get documento
   * @return documento
  **/
  @Schema(description = "")
  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public DocumentoRequest mime(String mime) {
    this.mime = mime;
    return this;
  }

   /**
   * Get mime
   * @return mime
  **/
  @Schema(description = "")
  public String getMime() {
    return mime;
  }

  public void setMime(String mime) {
    this.mime = mime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentoRequest documentoRequest = (DocumentoRequest) o;
    return Objects.equals(this.nombre, documentoRequest.nombre) &&
        Objects.equals(this.documento, documentoRequest.documento) &&
        Objects.equals(this.mime, documentoRequest.mime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombre, documento, mime);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentoRequest {\n");
    
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    documento: ").append(toIndentedString(documento)).append("\n");
    sb.append("    mime: ").append(toIndentedString(mime)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
