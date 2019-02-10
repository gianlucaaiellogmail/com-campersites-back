package old.info.campersites.bo;

import java.io.Serializable;
import java.util.Date;


public class FiltriBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean PS = false;
	private boolean CS = false;
	private boolean AA = false;
	private boolean AC = false;
	private boolean CA = false;
	private boolean sostaGratis = false; 
	private boolean acqua = false;
	private boolean scaricoCassetta = false;
	private boolean scaricoPozzetto = false;
	private boolean corrente = false;
	private boolean serviziGratis = false;
	private boolean sterrato = false;
	private boolean asfaltato = false;
	private boolean erba = false;
	private boolean mattoni = false;
	private boolean accessoCustodito = false;
	private boolean videosorveglianza = false;
	private boolean notte = false;
	private boolean illuminazione = false;
	private boolean ombra = false;
	private boolean docce = false;
	private boolean bagni = false;
	private boolean bambini = false;
	private boolean picnic = false;
	private boolean animali = false;
	private boolean fermata = false;
	private boolean wifi = false;
	private Date chiusura = null;
	private int posti = 0;
	private int maxHH = 0;

	public FiltriBo() {
	}

	@Override
	public String toString() {
		return "FiltriBo [filtriAttivi=" + this.isFiltriAttivi() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.isFiltriAttivi() == false) ? 0 : 1);
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
		if (!(obj instanceof FiltriBo)) {
			return false;
		}
		FiltriBo other = (FiltriBo) obj;
		if (this.isFiltriAttivi() == false) {
			if (other.isFiltriAttivi() != false) {
				return false;
			}
		} else if (!this.isFiltriAttivi() == other.isFiltriAttivi()) {
			return false;
		}
		return true;
	}

	public boolean isFiltriAttivi() {
		return PS ||
		CS ||
		AA ||
		AC ||
		CA ||
		sostaGratis || 
		acqua ||
		scaricoCassetta ||
		scaricoPozzetto ||
		corrente ||
		serviziGratis ||
		sterrato ||
		asfaltato ||
		erba ||
		mattoni ||
		accessoCustodito ||
		videosorveglianza ||
		notte ||
		illuminazione ||
		ombra ||
		docce ||
		bagni ||
		bambini ||
		picnic ||
		animali ||
		fermata ||
		wifi ||
		chiusura != null ||
		posti > 0 ||
		maxHH > 0;
	}

	public boolean isPS() {
		return PS;
	}

	public void setPS(boolean pS) {
		PS = pS;
	}

	public boolean isCS() {
		return CS;
	}

	public void setCS(boolean cS) {
		CS = cS;
	}

	public boolean isAA() {
		return AA;
	}

	public void setAA(boolean aA) {
		AA = aA;
	}

	public boolean isAC() {
		return AC;
	}

	public void setAC(boolean aC) {
		AC = aC;
	}

	public boolean isCA() {
		return CA;
	}

	public void setCA(boolean cA) {
		CA = cA;
	}

	public boolean isSostaGratis() {
		return sostaGratis;
	}

	public void setSostaGratis(boolean sostaGratis) {
		this.sostaGratis = sostaGratis;
	}

	public boolean isAcqua() {
		return acqua;
	}

	public void setAcqua(boolean acqua) {
		this.acqua = acqua;
	}

	public boolean isScaricoCassetta() {
		return scaricoCassetta;
	}

	public void setScaricoCassetta(boolean scaricoCassetta) {
		this.scaricoCassetta = scaricoCassetta;
	}

	public boolean isScaricoPozzetto() {
		return scaricoPozzetto;
	}

	public void setScaricoPozzetto(boolean scaricoPozzetto) {
		this.scaricoPozzetto = scaricoPozzetto;
	}

	public boolean isCorrente() {
		return corrente;
	}

	public void setCorrente(boolean corrente) {
		this.corrente = corrente;
	}

	public boolean isServiziGratis() {
		return serviziGratis;
	}

	public void setServiziGratis(boolean serviziGratis) {
		this.serviziGratis = serviziGratis;
	}

	public boolean isSterrato() {
		return sterrato;
	}

	public void setSterrato(boolean sterrato) {
		this.sterrato = sterrato;
	}

	public boolean isAsfaltato() {
		return asfaltato;
	}

	public void setAsfaltato(boolean asfaltato) {
		this.asfaltato = asfaltato;
	}

	public boolean isErba() {
		return erba;
	}

	public void setErba(boolean erba) {
		this.erba = erba;
	}

	public boolean isMattoni() {
		return mattoni;
	}

	public void setMattoni(boolean mattoni) {
		this.mattoni = mattoni;
	}

	public boolean isAccessoCustodito() {
		return accessoCustodito;
	}

	public void setAccessoCustodito(boolean accessoCustodito) {
		this.accessoCustodito = accessoCustodito;
	}

	public boolean isVideosorveglianza() {
		return videosorveglianza;
	}

	public void setVideosorveglianza(boolean videosorveglianza) {
		this.videosorveglianza = videosorveglianza;
	}

	public boolean isNotte() {
		return notte;
	}

	public void setNotte(boolean notte) {
		this.notte = notte;
	}

	public boolean isIlluminazione() {
		return illuminazione;
	}

	public void setIlluminazione(boolean illuminazione) {
		this.illuminazione = illuminazione;
	}

	public boolean isOmbra() {
		return ombra;
	}

	public void setOmbra(boolean ombra) {
		this.ombra = ombra;
	}

	public boolean isDocce() {
		return docce;
	}

	public void setDocce(boolean docce) {
		this.docce = docce;
	}

	public boolean isBagni() {
		return bagni;
	}

	public void setBagni(boolean bagni) {
		this.bagni = bagni;
	}

	public boolean isBambini() {
		return bambini;
	}

	public void setBambini(boolean bambini) {
		this.bambini = bambini;
	}

	public boolean isPicnic() {
		return picnic;
	}

	public void setPicnic(boolean picnic) {
		this.picnic = picnic;
	}

	public boolean isAnimali() {
		return animali;
	}

	public void setAnimali(boolean animali) {
		this.animali = animali;
	}

	public boolean isFermata() {
		return fermata;
	}

	public void setFermata(boolean fermata) {
		this.fermata = fermata;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public Date getChiusura() {
		return chiusura;
	}

	public void setChiusura(Date chiusura) {
		this.chiusura = chiusura;
	}

	public int getPosti() {
		return posti;
	}

	public void setPosti(int posti) {
		this.posti = posti;
	}

	public int getMaxHH() {
		return maxHH;
	}

	public void setMaxHH(int maxHH) {
		this.maxHH = maxHH;
	}

}
