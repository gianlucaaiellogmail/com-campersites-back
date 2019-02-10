package old.info.campersites.controller.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.entity.ConfigurationEntity;
import info.campersites.repository.ConfigurationRepository;
import old.info.campersites.bo.NationStatsBo;
import old.info.campersites.bo.NationsFilesBo;
import old.info.campersites.service.OldStopPointsService;

@RestController
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class StaticMobileController {
	 
	@Autowired
	private ConfigurationRepository configurationRepository;
	@Autowired
	private OldStopPointsService stopPointsService;
	
	@RequestMapping(value = "/downloadDbMaps", method = RequestMethod.GET)
	public @ResponseBody List<NationsFilesBo> getDownloadDbMaps(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String fileUploadDirectory = configurationRepository.getOne(ConfigurationEntity.IMAGES_DIRECTORY).getValore() + "/dbMaps/";
		List<NationsFilesBo> nations = new ArrayList<NationsFilesBo>();
		List<NationStatsBo> nationStats = stopPointsService.getNationStats();
		for (NationStatsBo nationStatsBo : nationStats) {
			String filename = nationStatsBo.getNation() + "CamperSitesDbMaps.sqlite";
			File file = new File(fileUploadDirectory + filename);
			if (file != null && file.exists()) {
				NationsFilesBo nation = new NationsFilesBo();
				nation.setNation(nationStatsBo.getNation());
				nation.setFilename(filename);
				nation.setFilesize((double) file.length() / 1024 / 1024);
				Calendar filedate = Calendar.getInstance();
				filedate.setTimeInMillis(file.lastModified());
				nation.setFiledate(filedate.getTime());
				nations.add(nation);
			}
		}
		return nations;
	}
	
}
