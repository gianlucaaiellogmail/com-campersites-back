package info.campersites.enumerator;

public enum EStopPointPlaceTypeId {
	ST("label.sterrato", "ST"),
	AS("label.asfaltato", "AS"),
	ER("label.erba", "ER"),
	MA("label.mattoni", "MA");
	
	private String descr;
	private String value;
	
	EStopPointPlaceTypeId(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}

	public String getDescr() {
		return this.descr;
	}

	public String getValue() {
		return this.value;
	}
	
	public static EStopPointPlaceTypeId fromId(String value) {
		if(value == null) {
			return null;
		}
		for(EStopPointPlaceTypeId stato : EStopPointPlaceTypeId.values()) {
			if(stato.getValue().equals(value)) {
				return stato;
			}
		}
		return null;
	}
}
