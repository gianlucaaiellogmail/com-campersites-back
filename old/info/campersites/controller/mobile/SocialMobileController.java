package old.info.campersites.controller.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import old.info.campersites.bo.UserBo;
import old.info.campersites.jsonresponse.UserJsonResponse;
import old.info.campersites.service.OldUserService;
import old.info.campersites.tools.Defines;

@RestController
public class SocialMobileController {
	
	@Autowired
	private OldUserService userService;
	 
	@RequestMapping(value = "/fbLoginMobile", method = RequestMethod.POST)
	public @ResponseBody UserJsonResponse fbLoginMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String fbUserId = request.getParameter("uid");
		String nickname = request.getParameter("nickname");
		String photoPath = request.getParameter("photoPath");
		String locale = request.getParameter("locale");
		String fbToken = request.getParameter("accessToken");
		UserJsonResponse userJsonResponse = new UserJsonResponse();
		UserBo user = userService.getUserByFbId(fbUserId);
		if (user != null) {
			try {
				user = userService.loginFbUser(user.getUserId(), nickname, photoPath, locale.substring(0, 2), fbToken);
				if (user != null) {
					userJsonResponse.setUser(user);
					userJsonResponse.setEsito(Defines.OK);
					request.getSession().setAttribute(Defines.CURRENT_USER, user);
				} else {
					userJsonResponse.setEsito(Defines.KO);
				}
			} catch (Exception e) {
				userJsonResponse.setEsito(Defines.SYSTEM);
			}
		} else {
			user = userService.createFbUser(fbUserId, nickname, photoPath, locale.substring(0, 2), fbToken);
			userJsonResponse.setUser(user);
			userJsonResponse.setEsito(Defines.OK);
			request.getSession().setAttribute(Defines.CURRENT_USER, user);
		}
		
		return userJsonResponse;
	}

}
