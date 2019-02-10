package info.campersites.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import info.campersites.enumerator.EStopPointPlaceTypeId;
import info.campersites.enumerator.EStopPointTypeId;
import info.campersites.enumerator.EValuta;

@Entity
@Table(name = "stop_points")
public class StopPointsEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "stop_id")
	private Long stopId;
	
	@Column(name = "latitude")
	private Float latitude;

	@Column(name = "longitude")
	private Float longitude;

	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "type_id", length = 2)
	private String typeId = EStopPointTypeId.PS.getValue();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted")
	private Date inserted = Calendar.getInstance().getTime();

	@Column(name = "inserted_by_user")
	private Long insertedUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	private Date modified = Calendar.getInstance().getTime();

	@Column(name = "modified_by_user")
	private Long modifiedUserId;

	@Column(name = "tot_preferito")
	private Integer totPreferito = Integer.valueOf(0);

	@Column(name = "rating")
	private Float rating = new Float(0);

	@Column(name = "foto_path", length = 100)
	private String fotoPath;
	
	@Column(name = "homepage", length = 100)
	private String homepage;
	
	@Column(name = "valuta", length = 3)
	private String valuta = EValuta.EUR.getValue();

	@Column(name = "prezzo_notturno")
	private Float prezzoNotturno;

	@Column(name = "prezzo_orario")
	private Float prezzoOrario;

	@Column(name = "prezzo_giornaliero")
	private Float prezzoGiornaliero;

	@Column(name = "prezzo_settimanale")
	private Float prezzoSettimanale;

	@Column(name = "prezzo_particolare", length = 45)
	private String prezzoParticolare;

	@Column(name = "acqua")
	private Integer acqua;

	@Column(name = "scarico_cassetta")
	private Integer scaricoCassetta;

	@Column(name = "scarico_pozzetto")
	private Integer scaricoPozzetto;

	@Column(name = "prezzo_service")
	private Float prezzoService;

	@Column(name = "tipo_piazzola", length = 2)
	private String tipoPiazzola = EStopPointPlaceTypeId.ST.getValue();

	@Column(name = "corrente")
	private Integer corrente;

	@Column(name = "prezzo_corrente")
	private Float prezzoCorrente;

	@Column(name = "custodito")
	private Integer accessoCustodito;

	@Column(name = "videosorveglianza")
	private Integer videosorveglianza;

	@Column(name = "notte")
	private Integer notte;

	@Column(name = "illuminazione")
	private Integer illuminazione;

	@Column(name = "ombra")
	private Integer ombra;

	@Column(name = "docce")
	private Integer docce;

	@Column(name = "bagni")
	private Integer bagni;

	@Column(name = "bambini")
	private Integer bambini;

	@Column(name = "picnic")
	private Integer picnic;

	@Column(name = "animali")
	private Integer animali;

	@Column(name = "fermata")
	private Integer fermata;

	@Column(name = "wifi")
	private Integer wifi;

	@Column(name = "locality", length = 100)
	private String locality;

	@Column(name = "nation", length = 3)
	private String nation;

	@Column(name = "telefono", length = 45)
	private String telefono;
	
	@Column(name = "posti")
	private Integer posti;

	@Column(name = "max_hh")
	private Integer maxHH;

	@Column(name = "chiusura", length = 45)
	private String chiusura;

	@Column(name = "visibile")
	private Integer visibile;
	
	@Transient
	private List<ReviewsInfoEntity> reviews;

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

	public EStopPointTypeId getTypeId() {
		return EStopPointTypeId.fromId(typeId);
	}

	public void setTypeId(EStopPointTypeId typeId) {
		this.typeId = typeId.getValue();
	}

	public Date getInserted() {
		return inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public Long getInsertedUserId() {
		return insertedUserId;
	}

	public void setInsertedUserId(Long insertedUserId) {
		this.insertedUserId = insertedUserId;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(Long modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Integer getTotPreferito() {
		return totPreferito;
	}

	public void setTotPreferito(Integer totPreferito) {
		this.totPreferito = totPreferito;
	}

	public Float getRating() {
		return rating;
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

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public EValuta getValuta() {
		return EValuta.fromId(valuta);
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

	public EStopPointPlaceTypeId getTipoPiazzola() {
		return EStopPointPlaceTypeId.fromId(tipoPiazzola);
	}

	public void setTipoPiazzola(EStopPointPlaceTypeId tipoPiazzola) {
		this.tipoPiazzola = tipoPiazzola.getValue();
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

	public Integer getVisibile() {
		return visibile;
	}

	public void setVisibile(Integer visibile) {
		this.visibile = visibile;
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
		StopPointsEntity other = (StopPointsEntity) obj;
		if (stopId == null) {
			if (other.stopId != null)
				return false;
		} else if (!stopId.equals(other.stopId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StopPointsEntity [stopId=" + stopId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", description=" + description + ", typeId=" + typeId + ", inserted=" + inserted + ", insertedUserId="
				+ insertedUserId + ", modified=" + modified + ", modifiedUserId=" + modifiedUserId + ", totPreferito="
				+ totPreferito + ", rating=" + rating + ", fotoPath=" + fotoPath + ", homepage=" + homepage
				+ ", valuta=" + valuta + ", prezzoNotturno=" + prezzoNotturno + ", prezzoOrario=" + prezzoOrario
				+ ", prezzoGiornaliero=" + prezzoGiornaliero + ", prezzoSettimanale=" + prezzoSettimanale
				+ ", prezzoParticolare=" + prezzoParticolare + ", acqua=" + acqua + ", scaricoCassetta="
				+ scaricoCassetta + ", scaricoPozzetto=" + scaricoPozzetto + ", prezzoService=" + prezzoService
				+ ", tipoPiazzola=" + tipoPiazzola + ", corrente=" + corrente + ", prezzoCorrente=" + prezzoCorrente
				+ ", accessoCustodito=" + accessoCustodito + ", videosorveglianza=" + videosorveglianza + ", notte="
				+ notte + ", illuminazione=" + illuminazione + ", ombra=" + ombra + ", docce=" + docce + ", bagni="
				+ bagni + ", bambini=" + bambini + ", picnic=" + picnic + ", animali=" + animali + ", fermata="
				+ fermata + ", wifi=" + wifi + ", locality=" + locality + ", nation=" + nation + ", telefono="
				+ telefono + ", posti=" + posti + ", maxHH=" + maxHH + ", chiusura=" + chiusura + ", visibile="
				+ visibile + "]";
	}

	public List<ReviewsInfoEntity> getReviews() {
		if (reviews == null) reviews = new ArrayList<ReviewsInfoEntity>();
		return reviews;
	}

	public void setReviews(List<ReviewsInfoEntity> reviews) {
		this.reviews = reviews;
	}
	
}
