package info.campersites.bo;

import java.io.Serializable;

import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;



public class StopPointMarkerBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long stopId;
	private Float latitude;
	private Float longitude;
	private String description;
	private String typeId;
	private Integer totPreferito = Integer.valueOf(0);
	private Integer rating;
	private String prezzo;
	private Integer acqua;
	private Integer scaricoCassetta;
	private Integer scaricoPozzetto;
	private Integer corrente;
	private String tipoPiazzola;
	private Integer accessoCustodito;
	private Integer videosorveglianza;
	private Integer notte;
	private Integer illuminazione;
	private Integer ombra;
	private Integer docce;
	private Integer bagni;
	private Integer bambini;
	private Integer picnic;
	private Integer animali;
	private Integer fermata;
	private Integer wifi;
	private String locality;
	private String nation;
	private Integer posti;
	private String chiusura;
	private Integer visibile;
	private Double distance;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(EStopPointTypeId typeId) {
		this.typeId = typeId.getValue();
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

	public Integer getTotPreferito() {
		return totPreferito;
	}

	public void setTotPreferito(Integer totPreferito) {
		this.totPreferito = totPreferito;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
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

	public Integer getCorrente() {
		return corrente;
	}

	public void setCorrente(Integer corrente) {
		this.corrente = corrente;
	}

	public String getTipoPiazzola() {
		return tipoPiazzola;
	}

	public void setTipoPiazzola(EStopPointPlaceTypeId tipoPiazzola) {
		this.tipoPiazzola = tipoPiazzola.getValue();
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

	public Integer getPosti() {
		return posti;
	}

	public void setPosti(Integer posti) {
		this.posti = posti;
	}

	public String getChiusura() {
		return chiusura;
	}

	public void setChiusura(String chiusura) {
		this.chiusura = chiusura;
	}

	public Integer getVisibile() {
		return visibile;
	}

	public void setVisibile(Integer visibile) {
		this.visibile = visibile;
	}

	public String getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StopPointMarkerBo other = (StopPointMarkerBo) obj;
		if (stopId == null) {
			if (other.stopId != null)
				return false;
		} else if (!stopId.equals(other.stopId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StopPointMarkerBo [stopId=" + stopId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", description=" + description + ", typeId=" + typeId + ", totPreferito=" + totPreferito + ", rating="
				+ rating + ", prezzo=" + prezzo + ", acqua=" + acqua + ", scaricoCassetta=" + scaricoCassetta
				+ ", scaricoPozzetto=" + scaricoPozzetto + ", corrente=" + corrente + ", tipoPiazzola=" + tipoPiazzola
				+ ", accessoCustodito=" + accessoCustodito + ", videosorveglianza=" + videosorveglianza + ", notte="
				+ notte + ", illuminazione=" + illuminazione + ", ombra=" + ombra + ", docce=" + docce + ", bagni="
				+ bagni + ", bambini=" + bambini + ", picnic=" + picnic + ", animali=" + animali + ", fermata="
				+ fermata + ", wifi=" + wifi + ", locality=" + locality + ", nation=" + nation + ", posti=" + posti
				+ ", chiusura=" + chiusura + ", visibile=" + visibile + "]";
	}

}
