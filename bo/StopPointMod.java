package info.campersites.bo;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;



public class StopPointMod implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Min(value=0, message = Errors.PATTERN)
	private Long stopId;

	@NotNull(message = Errors.REQUIRED)
	@Digits(integer = 2, fraction = 6, message = Errors.PATTERN)
	private Float latitude;

	@NotNull(message = Errors.REQUIRED)
	@Digits(integer = 3, fraction = 6, message = Errors.PATTERN)
	private Float longitude;

	@NotEmpty(message = Errors.REQUIRED)
	@Length(max=100, message = Errors.MAXLENGTH)
	private String description;
	
	@NotEmpty(message = Errors.REQUIRED)
	@Length(max=2, message = Errors.MAXLENGTH)
	private String typeId;
	
	@URL(protocol="http", message = Errors.URL)
	private String homepage;
	
	private String valuta = "EUR";
	
	private Float prezzoNotturno;
	
	private Float prezzoOrario;
	
	private Float prezzoGiornaliero;
	
	private Float prezzoSettimanale;
	
	private String prezzoParticolare;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer acqua;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer scaricoCassetta;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer scaricoPozzetto;
	
	private Float prezzoService;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer corrente;
	
	private Float prezzoCorrente;
	
	@NotEmpty(message = Errors.REQUIRED)
	@Length(min=2, max=2, message = Errors.PATTERN)
	private String tipoPiazzola = "ST";
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer accessoCustodito;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer videosorveglianza;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer notte;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer illuminazione;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer ombra;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer docce;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer bagni;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer bambini;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer picnic;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer animali;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer fermata;
	
	@Digits(integer = 1, fraction = 0, message = Errors.PATTERN)
	@Range(min=0, max=1, message = Errors.PATTERN)
	private Integer wifi;
	
	@NotEmpty(message = Errors.REQUIRED)
	@Length(max=100, message = Errors.MAXLENGTH)
	private String locality;
	
	@NotEmpty(message = Errors.REQUIRED)
	@Length(min=2, max=2, message = Errors.PATTERN)
	private String nation;
	
	@Length(max=45, message = Errors.MAXLENGTH)
	@Pattern(regexp="^[\\+]+\\d+$", message = Errors.PATTERN)
	private String telefono;
	
	private Integer posti;
	
	private Integer maxHH;
	
	@Length(max=11, message = Errors.MAXLENGTH)
	@Pattern(regexp="^[0-3][0-9]/[0-1][0-9]-[0-3][0-9]/[0-1][0-9]$", message = Errors.PATTERN)
	private String chiusura;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StopPointMod)) {
			return false;
		}
		StopPointMod other = (StopPointMod) obj;
		if (stopId == null) {
			if (other.getStopId() != null) {
				return false;
			}
		} else if (!stopId.equals(other.getStopId())) {
			return false;
		}
		return true;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getValuta() {
		return valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
	}

	public Float getPrezzoNotturno() {
		return prezzoNotturno;
	}

	public void setPrezzoNotturno(Float prezzoNotturno) {
		this.prezzoNotturno = prezzoNotturno;
	}

	public Float getPrezzoOrario() {
		return prezzoOrario;
	}

	public void setPrezzoOrario(Float prezzoOrario) {
		this.prezzoOrario = prezzoOrario;
	}

	public Float getPrezzoGiornaliero() {
		return prezzoGiornaliero;
	}

	public void setPrezzoGiornaliero(Float prezzoGiornaliero) {
		this.prezzoGiornaliero = prezzoGiornaliero;
	}

	public Float getPrezzoSettimanale() {
		return prezzoSettimanale;
	}

	public void setPrezzoSettimanale(Float prezzoSettimanale) {
		this.prezzoSettimanale = prezzoSettimanale;
	}

	public String getPrezzoParticolare() {
		return prezzoParticolare;
	}

	public void setPrezzoParticolare(String prezzoParticolare) {
		this.prezzoParticolare = prezzoParticolare;
	}

	public Integer getAcqua() {
		return acqua;
	}

	public void setAcqua(Integer acqua) {
		this.acqua = acqua;
	}

	public Integer getScaricoCassetta() {
		return scaricoCassetta;
	}

	public void setScaricoCassetta(Integer scaricoCassetta) {
		this.scaricoCassetta = scaricoCassetta;
	}

	public Integer getScaricoPozzetto() {
		return scaricoPozzetto;
	}

	public void setScaricoPozzetto(Integer scaricoPozzetto) {
		this.scaricoPozzetto = scaricoPozzetto;
	}

	public Float getPrezzoService() {
		return prezzoService;
	}

	public void setPrezzoService(Float prezzoService) {
		this.prezzoService = prezzoService;
	}

	public Integer getCorrente() {
		return corrente;
	}

	public void setCorrente(Integer corrente) {
		this.corrente = corrente;
	}

	public Float getPrezzoCorrente() {
		return prezzoCorrente;
	}

	public void setPrezzoCorrente(Float prezzoCorrente) {
		this.prezzoCorrente = prezzoCorrente;
	}

	public String getTipoPiazzola() {
		return tipoPiazzola;
	}

	public void setTipoPiazzola(String tipoPiazzola) {
		this.tipoPiazzola = tipoPiazzola;
	}

	public Integer getAccessoCustodito() {
		return accessoCustodito;
	}

	public void setAccessoCustodito(Integer accessoCustodito) {
		this.accessoCustodito = accessoCustodito;
	}

	public Integer getVideosorveglianza() {
		return videosorveglianza;
	}

	public void setVideosorveglianza(Integer videosorveglianza) {
		this.videosorveglianza = videosorveglianza;
	}

	public Integer getNotte() {
		return notte;
	}

	public void setNotte(Integer notte) {
		this.notte = notte;
	}

	public Integer getIlluminazione() {
		return illuminazione;
	}

	public void setIlluminazione(Integer illuminazione) {
		this.illuminazione = illuminazione;
	}

	public Integer getOmbra() {
		return ombra;
	}

	public void setOmbra(Integer ombra) {
		this.ombra = ombra;
	}

	public Integer getDocce() {
		return docce;
	}

	public void setDocce(Integer docce) {
		this.docce = docce;
	}

	public Integer getBagni() {
		return bagni;
	}

	public void setBagni(Integer bagni) {
		this.bagni = bagni;
	}

	public Integer getBambini() {
		return bambini;
	}

	public void setBambini(Integer bambini) {
		this.bambini = bambini;
	}

	public Integer getPicnic() {
		return picnic;
	}

	public void setPicnic(Integer picnic) {
		this.picnic = picnic;
	}

	public Integer getAnimali() {
		return animali;
	}

	public void setAnimali(Integer animali) {
		this.animali = animali;
	}

	public Integer getFermata() {
		return fermata;
	}

	public void setFermata(Integer fermata) {
		this.fermata = fermata;
	}

	public Integer getWifi() {
		return wifi;
	}

	public void setWifi(Integer wifi) {
		this.wifi = wifi;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getPosti() {
		return posti;
	}

	public void setPosti(Integer posti) {
		this.posti = posti;
	}

	public Integer getMaxHH() {
		return maxHH;
	}

	public void setMaxHH(Integer maxHH) {
		this.maxHH = maxHH;
	}

	public String getChiusura() {
		return chiusura;
	}

	public void setChiusura(String chiusura) {
		this.chiusura = chiusura;
	}

	@Override
	public String toString() {
		return "StopPointMod [stopId=" + stopId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", description=" + description + ", typeId=" + typeId + ", homepage=" + homepage + ", valuta="
				+ valuta + ", prezzoNotturno=" + prezzoNotturno + ", prezzoOrario=" + prezzoOrario
				+ ", prezzoGiornaliero=" + prezzoGiornaliero + ", prezzoSettimanale=" + prezzoSettimanale
				+ ", prezzoParticolare=" + prezzoParticolare + ", acqua=" + acqua + ", scaricoCassetta="
				+ scaricoCassetta + ", scaricoPozzetto=" + scaricoPozzetto + ", prezzoService=" + prezzoService
				+ ", corrente=" + corrente + ", prezzoCorrente=" + prezzoCorrente + ", tipoPiazzola=" + tipoPiazzola
				+ ", accessoCustodito=" + accessoCustodito + ", videosorveglianza=" + videosorveglianza + ", notte="
				+ notte + ", illuminazione=" + illuminazione + ", ombra=" + ombra + ", docce=" + docce + ", bagni="
				+ bagni + ", bambini=" + bambini + ", picnic=" + picnic + ", animali=" + animali + ", fermata="
				+ fermata + ", wifi=" + wifi + ", locality=" + locality + ", nation=" + nation + ", telefono="
				+ telefono + ", posti=" + posti + ", maxHH=" + maxHH + ", chiusura=" + chiusura + "]";
	}

}
