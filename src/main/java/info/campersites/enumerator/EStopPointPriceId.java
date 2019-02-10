package info.campersites.enumerator;

public enum EStopPointPriceId {
	GR("Gratuito", "GR"),
	PA("Pagamento", "PA");
	
	private String descr;
	private String value;
	
	EStopPointPriceId(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}

	public String getDescr() {
		return this.descr;
	}

	public String getValue() {
		return this.value;
	}
	
	public static EStopPointPriceId fromId(String value) {
		if(value == null) {
			return null;
		}
		for(EStopPointPriceId stato : EStopPointPriceId.values()) {
			if(stato.getValue().equals(value)) {
				return stato;
			}
		}
		return null;
	}
}
