package old.info.campersites.service.impl;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import info.campersites.entity.ConfigurationEntity;
import info.campersites.entity.ImageEntity;
import info.campersites.entity.StopPointsEntity;
import info.campersites.repository.ConfigurationRepository;
import info.campersites.repository.ImageRepository;
import info.campersites.repository.StopPointsRepository;
import old.info.campersites.bo.ImageBo;
import old.info.campersites.service.OldImageService;
import old.info.campersites.tools.Tools;

@Service
@Transactional(readOnly = true)
public class OldImageServiceImpl implements OldImageService {
	 
    @Autowired
	private ImageRepository imageRepository;
    @Autowired
	private StopPointsRepository stopPointsRepository;
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Override
	public ImageBo getImageById(Long imageId) {
		ImageBo imageBo = null;
    	ImageEntity imageEntity = imageRepository.getOne(imageId);
    	if (imageEntity != null) {
    		imageBo = mappingEntityToBo(imageEntity);
    	}
        return imageBo;
    }

	@Override
	public List<ImageBo> getImageByStopId(Long stopId) {
		List<ImageBo> imagesBo = new ArrayList<ImageBo>();
		List<ImageEntity> imagesEntity = imageRepository.getByStopIdOrderByDateCreatedDesc(stopId);
    	for (ImageEntity imageEntity : imagesEntity) {
    		imagesBo.add(mappingEntityToBo(imageEntity));
		}
        return imagesBo;
    }

	@Override
	@Transactional(readOnly = false)
    public ImageBo uploadImage(MultipartFile mpf, Long stopId, Long userId) throws IllegalStateException, IOException {
        String newNameBase = UUID.randomUUID().toString();
        String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
        String newName = newNameBase + originalFileExtension;
        String storageDirectory = configurationRepository.getOne(ConfigurationEntity.IMAGES_DIRECTORY).getValore() + "/" + stopId;
        String contentType = mpf.getContentType();
        
        File directory = new File(storageDirectory);
        directory.mkdirs();

        // File originale
        File newFile = new File(storageDirectory + "/" + newName);
        mpf.transferTo(newFile);
        
        // Resize
        BufferedImage originalImage = ImageIO.read(newFile);
        if (originalImage.getWidth() > 2048 || originalImage.getHeight() > 2048) {
	        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	        BufferedImage resized = Tools.resizeImageWithHint(originalImage, Tools.getScaledDimension(new Dimension(originalImage.getWidth(), originalImage.getHeight()), new Dimension(2048, (2048 * originalImage.getHeight()) / originalImage.getWidth())), type);
	        ImageIO.write(resized, "png", newFile);
        }
        
        // Thumbnail
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage thumbnail = Tools.resizeImageWithHint(originalImage, Tools.getScaledDimension(new Dimension(originalImage.getWidth(), originalImage.getHeight()), new Dimension(150, 100)), type);
        String thumbnailName = newNameBase + "-thumbnail.png";
        File thumbnailFile = new File(storageDirectory + "/" + thumbnailName);
        ImageIO.write(thumbnail, "png", thumbnailFile);
        
        // Image info DB
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setOriginalName(mpf.getOriginalFilename());
        imageEntity.setStopId(stopId);
        imageEntity.setUserId(userId);
        imageEntity.setThumbnailName(thumbnailName);
        imageEntity.setNewName(newName);
        imageEntity.setContentType(contentType);
        imageEntity.setSize(mpf.getSize());
        imageEntity.setUrl("/foto/"+stopId+"/"+imageEntity.getNewName());
        imageEntity.setThumbnailUrl("/thumbnail/"+stopId+"/"+imageEntity.getNewName());
        imageEntity = imageRepository.save(imageEntity);
        
        // Aggiorna copertina widget StopPoint
        StopPointsEntity stopPointsEntity = stopPointsRepository.getOne(stopId);
        stopPointsEntity.setFotoPath("/thumbnail/"+imageEntity.getImageId());
        stopPointsRepository.save(stopPointsEntity);
        
        return mappingEntityToBo(imageEntity);
	}

	private ImageBo mappingEntityToBo(ImageEntity imageEntity) {
        ImageBo imageBo = new ImageBo(imageEntity.getImageId());
        imageBo.setOriginalName(imageEntity.getOriginalName());
        imageBo.setStopId(imageEntity.getStopId());
        imageBo.setUserId(imageEntity.getUserId());
        imageBo.setThumbnailName(imageEntity.getThumbnailName());
        imageBo.setNewName(imageEntity.getNewName());
        imageBo.setContentType(imageEntity.getContentType());
        imageBo.setSize(imageEntity.getSize());
        imageBo.setUrl(imageEntity.getUrl());
        imageBo.setThumbnailUrl(imageEntity.getThumbnailUrl());
 		return imageBo;
	}
	
}
