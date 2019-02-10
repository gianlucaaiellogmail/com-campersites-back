package info.campersites.controller.sitemap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import info.campersites.entity.StopPointsEntity;
import info.campersites.repository.StopPointsRepository;
import info.campersites.utils.Tools;

@RestController
public class SitemapController {
	
	@Autowired
	StopPointsRepository stopPointsRepository;

	@RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody XmlUrlSet dynamicSitemap() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        addUrl(xmlUrlSet, "", XmlUrl.Priority.HIGH);
        addUrl(xmlUrlSet, "/terms", XmlUrl.Priority.MEDIUM);
        addUrl(xmlUrlSet, "/privacy", XmlUrl.Priority.MEDIUM);
		List<StopPointsEntity> stopPoints = stopPointsRepository.findAll();
		for (StopPointsEntity stopPointsEntity : stopPoints) {
	        addUrl(xmlUrlSet, "/detail/" + stopPointsEntity.getStopId() + "?stopInfo=" + Tools.encodeUrl(HtmlUtils.htmlUnescape(stopPointsEntity.getLocality())) + "_" + Tools.encodeUrl(HtmlUtils.htmlUnescape(stopPointsEntity.getDescription())), XmlUrl.Priority.HIGH);
		}
        return xmlUrlSet;
    }

    private void addUrl(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl("http://www.campersites.info" + link, priority));
    }
	
}
