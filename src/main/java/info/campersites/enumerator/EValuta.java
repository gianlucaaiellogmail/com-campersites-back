package info.campersites.enumerator;

public enum EValuta {
	EUR("Euro", "EUR"),
	ALL("Lek albanese", "ALL"),
	BAM("Marco bosniaco", "BAM"),
	BGN("Nuovo lev bulgaro", "BGN"),
	BYR("Rublo bielorusso", "BYR"),
	CHF("Franco svizzero", "CHF"),
	CZK("Corona ceca", "CZK"),
	DKK("Corona danese", "DKK"),
	GBP("Sterlina britannica", "GBP"),
	GIP("Sterlina di Gibilterra", "GIP"),
	HRK("Kuna croata", "HRK"),
	HUF("Fiorino ungherese", "HUF"),
	ISK("Corona islandese", "ISK"),
	LTL("Lita lituano", "LTL"),
	LVL("Lats lettone", "LVL"),
	MDL("Leu moldavo", "MDL"),
	MKD("Dinaro macedone", "MKD"),
	NOK("Corona norvegese", "NOK"),
	PLN("Złoty polacco", "PLN"),
	RON("Nuovo leu rumeno", "ROL"),
	RSD("Dinaro serbo", "RSD"),
	RUB("Rublo russo", "RUB"),
	SEK("Corona svedese", "SEK"),
	UAH("Grivnia ucraina", "UAH");
	
	private String descr;
	private String value;
	
	EValuta(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}

	public String getDescr() {
		return this.descr;
	}

	public String getValue() {
		return this.value;
	}
	
	public static EValuta fromId(String value) {
		if(value == null) {
			return null;
		}
		for(EValuta stato : EValuta.values()) {
			if(stato.getValue().equals(value)) {
				return stato;
			}
		}
		return null;
	}
}
