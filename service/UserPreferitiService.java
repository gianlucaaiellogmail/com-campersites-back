package info.campersites.service;

public interface UserPreferitiService {
	
	Boolean isPreferito(Long stopId, Long userId);

	void togglePreferito(Long stopId, Long userId);
	
}
