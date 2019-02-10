package info.campersites.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import info.campersites.entity.ConfigurationEntity;
import info.campersites.entity.ImageEntity;
import info.campersites.repository.ConfigurationRepository;
import info.campersites.repository.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	private ConfigurationRepository configurationRepository; 

	@Override
    public List<String> getByStopPoint(final Long stopId) {
    	List<ImageEntity> imagesEntity = imageRepository.getByStopIdOrderByDateCreatedDesc(stopId);
    	return toUrls(stopId, imagesEntity);
    }
	
	private List<String> toUrls(final Long stopId, List<ImageEntity> imagesEntity) {
		List<String> urls = new ArrayList<String>();
		for (ImageEntity imageEntity : imagesEntity) {
			//if (imageEntity.getUserId() > 0) {
				urls.add(stopId + "/" + imageEntity.getNewName());
			//}
		}
		return urls;
	}

	@Override
	@Transactional(readOnly = false)
	public void upload(MultipartFile file, Long stopId, Long userId) throws IllegalStateException, IOException {
        String newNameBase = UUID.randomUUID().toString();
        String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newName = newNameBase + originalFileExtension;
        String storageDirectory = configurationRepository.getOne(ConfigurationEntity.IMAGES_DIRECTORY).getValore();
        String photoStorageDirectory = configurationRepository.getOne(ConfigurationEntity.IMAGES_DIRECTORY).getValore() + "/" + stopId;
        String contentType = file.getContentType();
        
        File directory = new File(photoStorageDirectory);
        directory.mkdirs();

        // File originale
        File newFile = new File(photoStorageDirectory + "/" + newName);
        file.transferTo(newFile);
        
        // Resize w514 x h374
        Thumbnails.of(newFile)
        	.size(1024, 748)
	        .allowOverwrite(true)
	        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(storageDirectory + "/appicon.png")), 0.5f)
	        .toFile(newFile);
        
        // Image info DB
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setOriginalName(file.getOriginalFilename());
        imageEntity.setStopId(stopId);
        imageEntity.setUserId(userId);
        imageEntity.setThumbnailName(newNameBase + "-thumbnail.png");
        imageEntity.setNewName(newName);
        imageEntity.setContentType(contentType);
        imageEntity.setSize(file.getSize());
        imageEntity.setUrl("/foto/"+stopId+"/"+imageEntity.getNewName());
        imageEntity.setThumbnailUrl("/thumbnail/"+stopId+"/"+imageEntity.getNewName());
        imageEntity = imageRepository.save(imageEntity);

	}

}
