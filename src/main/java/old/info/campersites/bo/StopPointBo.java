package old.info.campersites.bo;

import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;
import info.campersites.enumerator.EValuta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class StopPointBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long stopId;
	private Float latitude;
	private Float longitude;
	private String latitudeDeg;
	private String longitudeDeg;
	private String description;
	private Date modified;
	private Long modifiedByUser;
	private Date inserted;
	private Long insertedByUser;
	private String typeId;
	private Integer totPreferito = Integer.valueOf(0);
	private Float rating = new Float(0);
	private String fotoPath;
	private String homepage;
	private String valuta;
	private Float prezzoNotturno;
	private Float prezzoOrario;
	private Float prezzoGiornaliero;
	private Float prezzoSettimanale;
	private String prezzoParticolare;
	private Integer acqua;
	private Integer scaricoCassetta;
	private Integer scaricoPozzetto;
	private Float prezzoService;
	private Integer corrente;
	private Float prezzoCorrente;
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
	private List<ReviewBo> reviews;
	private Double distance;
	private String telefono;
	private Integer posti;
	private Integer maxHH;
	private String chiusura;
	private Integer visibile;

	public StopPointBo(Long stopId) {
		this.stopId = stopId;
	}

	public StopPointBo() {
	}

	@Override
	public String toString() {
		return "StopPointsBo [stopId=" + stopId + ", typeId=" + typeId + ", description=" + description + "]";
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StopPointBo)) {
			return false;
		}
		StopPointBo other = (StopPointBo) obj;
		if (stopId == null) {
			if (other.getStopId() != null) {
				return false;
			}
		} else if (!stopId.equals(other.getStopId())) {
			return false;
		}
		return true;
	}

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

	public Float getRating() {
		return (rating > 0) ? (float) (Math.round(rating*100.0)/100.0) : rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getFotoPath() {
		return fotoPath;
	}

	public void setFotoPath(String fotoPath) {
		this.fotoPath = fotoPath;
	}

	public List<ReviewBo> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewBo> reviews) {
		this.reviews = reviews;
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

	public void setValuta(EValuta valuta) {
		this.valuta = valuta.getValue();
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

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(Long modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
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

	public Date getInserted() {
		return inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public Long getInsertedByUser() {
		return insertedByUser;
	}

	public void setInsertedByUser(Long insertedByUser) {
		this.insertedByUser = insertedByUser;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getLatitudeDeg() {
		return latitudeDeg;
	}

	public void setLatitudeDeg(String latitudeDeg) {
		this.latitudeDeg = latitudeDeg;
	}

	public String getLongitudeDeg() {
		return longitudeDeg;
	}

	public void setLongitudeDeg(String longitudeDeg) {
		this.longitudeDeg = longitudeDeg;
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

	public Integer getVisibile() {
		return visibile;
	}

	public void setVisibile(Integer visibile) {
		this.visibile = visibile;
	}

}
