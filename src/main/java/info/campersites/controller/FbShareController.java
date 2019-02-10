package info.campersites.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import info.campersites.bo.StopPointBo;
import info.campersites.service.StopPointsService;

@RestController
@RequestMapping("/fbshare")
public class FbShareController {
	
	@Autowired
	private StopPointsService stopPointsService;
    @Autowired 
    private SpringTemplateEngine templateEngine;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody String getHomePageToShareOnFb(@RequestParam(value="fb_locale", required = false) final String fb_locale, HttpServletRequest request) {
		String locale = "it";
		if (StringUtils.isNotBlank(request.getHeader("X-Facebook-Locale"))) {
			locale = request.getHeader("X-Facebook-Locale");
			//System.out.println("X-Facebook-Locale: "+locale);
			locale = locale.substring(0, 2);
		} else {
			if (StringUtils.isNotBlank(fb_locale)) {
				locale = fb_locale;
				//System.out.println("fb_locale: "+locale);
				locale = locale.substring(0, 2);
			}
		}
		//System.out.println("locale: "+locale);
		if (locale == null) locale = "it";
		Locale localeObj = new Locale(locale);
    	final Context ctx = new Context(localeObj);
    	ctx.setVariable("title", "CamperSites.info");
        ctx.setVariable("image", "http://www.campersites.info/img/banner_1024_500.png");
    	ctx.setVariable("url", "http://www.campersites.info");
        final String htmlContent = this.templateEngine.process(locale + "/fb-share.html", ctx);
        return htmlContent;
    }

	@RequestMapping(value = "/detail/{stopId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody String getDetailPageToShareOnFb(@PathVariable(value="stopId") final Long stopId, @RequestParam(value="fb_locale", required = false) final String fb_locale, HttpServletRequest request) {
		try {
			if (stopId == null) return null;
			String locale = "it";
			if (StringUtils.isNotBlank(request.getHeader("X-Facebook-Locale"))) {
				locale = request.getHeader("X-Facebook-Locale");
				//System.out.println("X-Facebook-Locale: "+locale);
				locale = locale.substring(0, 2);
			} else {
				if (StringUtils.isNotBlank(fb_locale)) {
					locale = fb_locale;
					//System.out.println("fb_locale: "+locale);
					locale = locale.substring(0, 2);
				}
			}
			//System.out.println("locale: "+locale);
			if (locale == null) locale = "it";
			Locale localeObj = new Locale(locale);
			StopPointBo stopPointBo = stopPointsService.getStopPoint(stopId, null);
			if (stopPointBo.getStopId() != null) {
		    	final Context ctx = new Context(localeObj);
		    	ctx.setVariable("title", stopPointBo.getDescription() + " | " + stopPointBo.getLocality() + " | CamperSites.info");
		        ctx.setVariable("image", "http://www.campersites.info/img/banner_1024_500.png");
		        if (stopPointBo.getPhotos().size() > 0) {
		        	ctx.setVariable("image", "http://www.campersites.info:8000/"+stopPointBo.getPhotos().get(0));
				}
		    	ctx.setVariable("url", "http://www.campersites.info/detail/"+stopId);
		        final String htmlContent = this.templateEngine.process(locale + "/fb-share.html", ctx);
		        return htmlContent;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

}
