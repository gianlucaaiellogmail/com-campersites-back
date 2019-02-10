package old.info.campersites.controller.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import old.info.campersites.bo.UserBo;
import old.info.campersites.bo.UserPoiBo;
import old.info.campersites.service.OldUserPoiService;
import old.info.campersites.service.OldUserService;

@RestController
public class UserPoisMobileController {
	 
	@Autowired
	private OldUserPoiService userPoiService;
	@Autowired
	private OldUserService userService;
	
	protected static final String USER_POIS = "userPois";
	
	@RequestMapping(value = "/getMobileUserPoisBound", method = RequestMethod.GET)
	public @ResponseBody List<UserPoiBo> getUserPoisBound(Model model, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		List<UserPoiBo> userPois = new ArrayList<UserPoiBo>();
		String userFbId = request.getParameter("userFbId");
		String userId = request.getParameter("userId");
		UserBo currentUser = null;
		if (userFbId != null && !"".equals(userFbId) && !"null".equals(userFbId)) { 
			currentUser = userService.getUserByFbId(userFbId);
		} else {
			currentUser = userService.getUserById(Long.parseLong(userId));
		}
		String bounds = request.getParameter("bounds");
		userPois = userPoiService.getUserPoisBound(bounds, currentUser.getUserId());
		return userPois;
	}
	
}
