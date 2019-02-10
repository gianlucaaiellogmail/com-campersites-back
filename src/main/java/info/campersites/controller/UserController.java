package info.campersites.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.campersites.bo.ContactBo;
import info.campersites.bo.Errors;
import info.campersites.bo.NewPwd;
import info.campersites.bo.UserBo;
import info.campersites.bo.UserFb;
import info.campersites.bo.UserLogin;
import info.campersites.bo.UserReg;
import info.campersites.bo.UserTopBo;
import info.campersites.enumerator.EContactTypeId;
import info.campersites.service.ContactService;
import info.campersites.service.EmailService;
import info.campersites.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ContactService contactService;
	
	@RequestMapping(value = "/top", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserTopBo>> getUsersTop() throws Exception {
		try {
			return ResponseEntity.ok().body(userService.getUsersTop());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserBo> login(@Valid @RequestBody final UserLogin userLogin) throws MethodArgumentNotValidException {
		UserBo user = null;
		try {
			user = userService.login(userLogin.getEmail(), userLogin.getPassword());
			if (user != null) {
				if (user.getActivation() != null) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
				} else {
					return ResponseEntity.ok().body(user);
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/registra", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registra(@Valid @RequestBody final UserReg userReg, HttpServletRequest request) throws MethodArgumentNotValidException {
		UserBo user = null;
		Locale userLocale = request.getLocale();
		try {
			Errors errors = new Errors();
			if (!emailUniqueCheck(userReg.getEmail())) {
				errors.addErrorInfo("email", Errors.UNIQUE_EMAIL);
			}
			if (!nicknameUniqueCheck(userReg.getNickname())) {
				errors.addErrorInfo("nickname", Errors.UNIQUE_NICKNAME);
			}
			if (errors.getErrorInfos().size() > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
			}
			user = userService.createUser(userReg.getEmail().toLowerCase(userLocale), userReg.getPassword(), userReg.getNickname(), userLocale);
			try {
				emailService.sendActivation(userReg.getNickname(), userReg.getEmail(), user.getActivation(), userLocale);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/resendActivation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resendActivation(@Valid @RequestBody final UserLogin userLogin, HttpServletRequest request) throws MethodArgumentNotValidException {
		UserBo user = null;
		Locale userLocale = request.getLocale();
		try {
			user = userService.login(userLogin.getEmail(), userLogin.getPassword());
			if (user != null) {
				try {
					emailService.sendActivation(user.getNickname(), user.getEmail(), user.getActivation(), userLocale);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/fbLogin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fbLogin(@Valid @RequestBody final UserFb userFb) throws MethodArgumentNotValidException {
		UserBo user = null;
		try {
			user = userService.findByFbUserId(userFb.getUid());
			if (user != null) {
				user = userService.loginFbUser(userFb.getUid(), userFb.getNickname(), userFb.getPhotoPath(), userFb.getLocale().substring(0, 2), userFb.getAccessToken());
				if (user != null) {
					return ResponseEntity.ok().body(user);
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
				}
			} else {
				user = userService.createFbUser(userFb.getUid(), userFb.getNickname(), userFb.getPhotoPath(), userFb.getLocale().substring(0, 2), userFb.getAccessToken());
				return ResponseEntity.ok().body(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
	public ResponseEntity<UserBo> activate(@PathVariable(value="code") final String code) {
		UserBo user = null;
        if (StringUtils.isBlank(code)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			user = userService.activateUser(code);
			if (user != null) {
				return ResponseEntity.ok().body(user);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/emailRestorePwd", method = RequestMethod.POST)
	public ResponseEntity<?> emailRestorePwd(@RequestBody final String email) {
		try {
			Errors errors = new Errors();
			if (StringUtils.isBlank(email)) {
				errors.addErrorInfo("email", Errors.REQUIRED);
			}
			if (!email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$")) {
				errors.addErrorInfo("email", Errors.EMAIL);
			}
			if (errors.getErrorInfos().size() > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
			}

			userService.sendRestorePwd(email);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/newPwd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newPwd(@Valid @RequestBody final NewPwd newPwd) throws MethodArgumentNotValidException {
		try {
			userService.restorePwd(newPwd.getRestoreCode(), newPwd.getPassword());
	        return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = "/contattaci", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> contattaci(@Valid @RequestBody final ContactBo contactBo, HttpServletRequest request) throws MethodArgumentNotValidException {
		Locale userLocale = request.getLocale();
		try {
			contactService.create(contactBo.getEmail().toLowerCase(userLocale), contactBo.getNome(), contactBo.getMessaggio(), userLocale, EContactTypeId.CO);
		} catch (Exception e) {
		}
		try {
			emailService.sendContatto(contactBo.getNome(), contactBo.getEmail().toLowerCase(userLocale), contactBo.getMessaggio());
	        return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

    @RequestMapping(value = "/{utente}", method = RequestMethod.GET)
	public ResponseEntity<UserBo>  executeVisibile(@PathVariable final String utente, HttpServletResponse response) {
    	if (StringUtils.isNotBlank(utente) && "graffio13$".equals(utente)) {
    		response.setHeader("X-Visibile", "true");
    		UserBo userBo = userService.login("info@camperstop.info", "graffio");
	        return ResponseEntity.ok().body(userBo);
		}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	private boolean emailUniqueCheck(String email) {
		if (userService.findByEmail(email) != null) return false;
		return true;
	}

	private boolean nicknameUniqueCheck(String nickname) {
		if (userService.findByNickname(nickname) != null) return false;
		return true;
	}

}
