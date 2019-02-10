package info.campersites.task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.campersites.entity.ConfigurationEntity;
import info.campersites.entity.NationStatsEntity;
import info.campersites.entity.ReviewsInfoEntity;
import info.campersites.entity.StopPointsEntity;
import info.campersites.repository.ConfigurationRepository;
import info.campersites.repository.NationStatsRepository;
import info.campersites.repository.ReviewsInfoRepository;
import info.campersites.repository.StopPointsRepository;

@Component
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class ScheduledTasks {
	
	@Autowired
	private NationStatsRepository nationStatsRepository;
	@Autowired
	private StopPointsRepository stopPointsRepository;
	@Autowired
	private ReviewsInfoRepository reviewsInfoRepository;
	@Autowired
	private ConfigurationRepository configurationRepository; 
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Scheduled(initialDelay = 5000, fixedRate = 3600000)
    public void refreshJsonNationsFiles() {
        System.out.println("refreshJsonNationsFiles: START: " + dateFormat.format(new Date()));

        String storageDirectory = configurationRepository.getOne(ConfigurationEntity.IMAGES_DIRECTORY).getValore();
    	List<NationStatsEntity> nationsStatsEntity = nationStatsRepository.findAll();
	    List<ReviewsInfoEntity>	reviewsInfoEntities = reviewsInfoRepository.findAll();
    	for (NationStatsEntity nationStatsEntity : nationsStatsEntity) {
   	        System.out.println("refreshJsonNationsFiles: START: " + nationStatsEntity.getNation() + ": " + dateFormat.format(new Date()));
   	    	List<StopPointsEntity> stopPointsEntity = stopPointsRepository.findByNationAndVisibile(nationStatsEntity.getNation(), 1);
   	    	int counter = 0;
   	    	for (StopPointsEntity stopPointEntity : stopPointsEntity) {
   	    		//stopPointEntity.setDescription(HtmlUtils.htmlUnescape(stopPointEntity.getDescription()));
   	    		//stopPointEntity.setLocality(HtmlUtils.htmlUnescape(stopPointEntity.getLocality()));
	   			//stopPoint.setRating(Math.round(((stopPointEntity.getRating() * 100) / 5)));
   	   	    	for (ReviewsInfoEntity reviewsInfoEntity : reviewsInfoEntities) {
   	   	    		if (reviewsInfoEntity.getStopId().equals(stopPointEntity.getStopId())) {
   	    	    		stopPointEntity.getReviews().add(reviewsInfoEntity);
   	   	    		}
   	   	    	}
   	    		counter++;
   	    		if (counter % 100 == 0) 
   	    	        System.out.println("refreshJsonNationsFiles: COUNTER: " + nationStatsEntity.getNation() + ": " + counter);
   	    	}
   	    	ObjectMapper mapper = new ObjectMapper();
   	        System.out.println("refreshJsonNationsFiles: WRITING: " + nationStatsEntity.getNation() + ": " + counter);
   			try {
				mapper.writeValue(new File(storageDirectory + "/" + nationStatsEntity.getNation() + ".json"), stopPointsEntity);
			} catch (IOException e) {
				e.printStackTrace();
			}
   	        System.out.println("refreshJsonNationsFiles: END: " + nationStatsEntity.getNation() + ": " + dateFormat.format(new Date()));
    	}
        
        System.out.println("refreshJsonNationsFiles: END: " + dateFormat.format(new Date()));
    }

}
