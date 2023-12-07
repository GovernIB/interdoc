package es.caib.interdoc.back.model;

import es.caib.interdoc.service.facade.EntitatServiceFacade;
import es.caib.interdoc.service.facade.ReferenciaServiceFacade;
import es.caib.interdoc.service.model.ReferenciaDTO;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ReferenciaModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ReferenciaServiceFacade referenciaService;
	
	@EJB
	private EntitatServiceFacade entitatService;

	private ReferenciaDTO value = new ReferenciaDTO();

	private String entitatDir3;
	
	private String interessats1;
	private String interessats2;
	private String interessats3;
	private String interessats4;
	private String interessats5;
	
	
	private String m1_clau;
	private String m1_valor;
	private String m2_clau;
	private String m2_valor;
	private String m3_clau;
	private String m3_valor;
	private String m4_clau;
	private String m4_valor;
	private String m5_clau;
	private String m5_valor;
	private String m6_clau;
	private String m6_valor;
	private String m7_clau;
	private String m7_valor;
	private String m8_clau;
	private String m8_valor;
	private String m9_clau;
	private String m9_valor;
	private String m10_clau;
	private String m10_valor;
	

	public ReferenciaDTO getValue() {
		return value;
	}

	public void setValue(ReferenciaDTO value) {
		this.value = value;
	}

	public void load() {
		if (value.getId() == null) {
			throw new IllegalArgumentException("id is null");
		}
		value = referenciaService.findById(value.getId()).orElseThrow();
	}

	public String getInteressats1() {
		return interessats1;
	}

	public void setInteressats1(String interessats1) {
		this.interessats1 = interessats1;
	}

	public String getInteressats2() {
		return interessats2;
	}

	public void setInteressats2(String interessats2) {
		this.interessats2 = interessats2;
	}

	public String getInteressats3() {
		return interessats3;
	}

	public void setInteressats3(String interessats3) {
		this.interessats3 = interessats3;
	}

	public String getInteressats4() {
		return interessats4;
	}

	public void setInteressats4(String interessats4) {
		this.interessats4 = interessats4;
	}

	public String getInteressats5() {
		return interessats5;
	}

	public void setInteressats5(String interessats5) {
		this.interessats5 = interessats5;
	}

	public String getM1_clau() {
		return m1_clau;
	}

	public void setM1_clau(String m1_clau) {
		this.m1_clau = m1_clau;
	}

	public String getM1_valor() {
		return m1_valor;
	}

	public void setM1_valor(String m1_valor) {
		this.m1_valor = m1_valor;
	}

	public String getM2_clau() {
		return m2_clau;
	}

	public void setM2_clau(String m2_clau) {
		this.m2_clau = m2_clau;
	}

	public String getM2_valor() {
		return m2_valor;
	}

	public void setM2_valor(String m2_valor) {
		this.m2_valor = m2_valor;
	}

	public String getM3_clau() {
		return m3_clau;
	}

	public void setM3_clau(String m3_clau) {
		this.m3_clau = m3_clau;
	}

	public String getM3_valor() {
		return m3_valor;
	}

	public void setM3_valor(String m3_valor) {
		this.m3_valor = m3_valor;
	}

	public String getM4_clau() {
		return m4_clau;
	}

	public void setM4_clau(String m4_clau) {
		this.m4_clau = m4_clau;
	}

	public String getM4_valor() {
		return m4_valor;
	}

	public void setM4_valor(String m4_valor) {
		this.m4_valor = m4_valor;
	}

	public String getM5_clau() {
		return m5_clau;
	}

	public void setM5_clau(String m5_clau) {
		this.m5_clau = m5_clau;
	}

	public String getM5_valor() {
		return m5_valor;
	}

	public void setM5_valor(String m5_valor) {
		this.m5_valor = m5_valor;
	}

	public String getM6_clau() {
		return m6_clau;
	}

	public void setM6_clau(String m6_clau) {
		this.m6_clau = m6_clau;
	}

	public String getM6_valor() {
		return m6_valor;
	}

	public void setM6_valor(String m6_valor) {
		this.m6_valor = m6_valor;
	}

	public String getM7_clau() {
		return m7_clau;
	}

	public void setM7_clau(String m7_clau) {
		this.m7_clau = m7_clau;
	}

	public String getM7_valor() {
		return m7_valor;
	}

	public void setM7_valor(String m7_valor) {
		this.m7_valor = m7_valor;
	}

	public String getM8_clau() {
		return m8_clau;
	}

	public void setM8_clau(String m8_clau) {
		this.m8_clau = m8_clau;
	}

	public String getM8_valor() {
		return m8_valor;
	}

	public void setM8_valor(String m8_valor) {
		this.m8_valor = m8_valor;
	}

	public String getM9_clau() {
		return m9_clau;
	}

	public void setM9_clau(String m9_clau) {
		this.m9_clau = m9_clau;
	}

	public String getM9_valor() {
		return m9_valor;
	}

	public void setM9_valor(String m9_valor) {
		this.m9_valor = m9_valor;
	}

	public String getM10_clau() {
		return m10_clau;
	}

	public void setM10_clau(String m10_clau) {
		this.m10_clau = m10_clau;
	}

	public String getM10_valor() {
		return m10_valor;
	}

	public void setM10_valor(String m10_valor) {
		this.m10_valor = m10_valor;
	}

	public String getEntitatDir3() {
		return entitatDir3;
	}

	public void setEntitatDir3(String entitatDir3) {
		this.entitatDir3 = entitatDir3;
	}
	
}
