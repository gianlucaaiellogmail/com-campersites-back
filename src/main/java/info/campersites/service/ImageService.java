package info.campersites.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	
	List<String> getByStopPoint(Long stopId);
	void upload(MultipartFile file, Long stopId, Long userId) throws IllegalStateException, IOException;
}
