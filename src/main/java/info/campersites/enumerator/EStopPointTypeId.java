package info.campersites.enumerator;

public enum EStopPointTypeId {
	AA("label.areaattrezzata", "AA"),
	PS("label.puntososta", "PS"),
	CS("label.camperservice", "CS"),
	AC("label.agricampeggio", "AC"),
	CA("label.campeggio", "CA");
	
	private String descr;
	private String value;
	
	EStopPointTypeId(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}

	public String getDescr() {
		return this.descr;
	}

	public String getValue() {
		return this.value;
	}
	
	public static EStopPointTypeId fromId(String value) {
		if(value == null) {
			return null;
		}
		for(EStopPointTypeId stato : EStopPointTypeId.values()) {
			if(stato.getValue().equals(value)) {
				return stato;
			}
		}
		return null;
	}
}
