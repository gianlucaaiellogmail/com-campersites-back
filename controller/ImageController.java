package info.campersites.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import info.campersites.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value = "/upload/{stopId}", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@PathVariable(value="stopId") final Long stopId, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(request.getHeader("X-User"))) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
	
			System.out.println("file.getSize(): " + file.getSize());
			if (file.getSize() > 20971520) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			String contentType = file.getContentType();
			if (!MediaType.IMAGE_GIF_VALUE.equals(contentType) && !MediaType.IMAGE_PNG_VALUE.equals(contentType) && !MediaType.IMAGE_JPEG_VALUE.equals(contentType)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
	
			imageService.upload(file, stopId, Long.parseLong(request.getHeader("X-User")));

			return ResponseEntity.ok().body(null);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
