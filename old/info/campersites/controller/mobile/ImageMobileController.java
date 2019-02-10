package old.info.campersites.controller.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import old.info.campersites.bo.ImageBo;
import old.info.campersites.bo.UserBo;
import old.info.campersites.jsonresponse.StringJsonResponse;
import old.info.campersites.service.OldImageService;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Defines;

@RestController
public class ImageMobileController {
	 
	@Autowired
	private OldImageService imageService; 
	@Autowired
	private OldUserService userService;
    
    @RequestMapping(value = "/uploadImageMobile/{stopId}/{userFbId}/{userId}", method = RequestMethod.POST)
    public @ResponseBody StringJsonResponse uploadImageMobile(MultipartHttpServletRequest request, HttpServletResponse response, @PathVariable Long stopId, @PathVariable String userFbId, @PathVariable Long userId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		StringJsonResponse stringJsonResponse = new StringJsonResponse();
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(userId);
		}

        List<ImageBo> images = new ArrayList<ImageBo>();
        final MultiValueMap<String, MultipartFile> filesMap = request.getMultiFileMap();
        Collection<List<MultipartFile>> files = filesMap.values();
        for (Iterator<List<MultipartFile>> iterator = files.iterator(); iterator.hasNext();) {
			List<MultipartFile> filesList = (List<MultipartFile>) iterator.next();
	        for (MultipartFile mpf : filesList) {
        		System.out.println("mpf.getSize(): " + mpf.getSize());
	        	if (mpf.getSize() > 20971520) {
	        		stringJsonResponse.setEsito(Defines.KO);
	    			return stringJsonResponse;
	        	}
	        	String contentType = mpf.getContentType();
        		System.out.println("contentType: " + contentType);
	        	if (!MediaType.IMAGE_GIF_VALUE.equals(contentType) && !MediaType.IMAGE_PNG_VALUE.equals(contentType) && !MediaType.IMAGE_JPEG_VALUE.equals(contentType)) {
	        		stringJsonResponse.setEsito(Defines.KO);
	    			return stringJsonResponse;
	        	}
	        }
		}

        for (Iterator<List<MultipartFile>> iterator = files.iterator(); iterator.hasNext();) {
			List<MultipartFile> filesList = (List<MultipartFile>) iterator.next();
	        for (MultipartFile mpf : filesList) {
	            try {
	                ImageBo image = imageService.uploadImage(mpf, stopId, currentUser.getUserId());
	                images.add(image);
	            } catch(IOException e) {
	            	System.out.println("IOException: " + e.getMessage());
	        		stringJsonResponse.setEsito(Defines.KO);
	    			return stringJsonResponse;
	            }
			}
		}
		stringJsonResponse.setEsito(Defines.OK);
		return stringJsonResponse;
    }
	
}
