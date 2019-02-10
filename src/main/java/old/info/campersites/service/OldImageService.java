package old.info.campersites.service;

import old.info.campersites.bo.ImageBo;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface OldImageService {
	 
    ImageBo getImageById(Long imageId);
    List<ImageBo> getImageByStopId(Long stopId);
    ImageBo uploadImage(MultipartFile mpf, Long stopId, Long userId) throws IllegalStateException, IOException;

}
