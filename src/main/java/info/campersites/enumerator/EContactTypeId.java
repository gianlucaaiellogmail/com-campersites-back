package info.campersites.enumerator;

public enum EContactTypeId {
	CO("label.contatto", "CO"),
	SE("label.segnalazione", "SE");
	
	private String descr;
	private String value;
	
	EContactTypeId(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}

	public String getDescr() {
		return this.descr;
	}

	public String getValue() {
		return this.value;
	}
	
	public static EContactTypeId fromId(String value) {
		if(value == null) {
			return null;
		}
		for(EContactTypeId stato : EContactTypeId.values()) {
			if(stato.getValue().equals(value)) {
				return stato;
			}
		}
		return null;
	}
}
