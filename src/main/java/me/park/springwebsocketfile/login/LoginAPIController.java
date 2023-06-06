package me.park.springwebsocketfile.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.park.springwebsocketfile.util.CommonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginAPIController {

	@PostMapping("")
	public ResponseEntity<String> loginProcess(@RequestBody Map<String, Object> loginDto, HttpServletRequest request) {

		String nickname = (String) loginDto.get("nickname");

		if(CommonUtil.isEmpty(nickname)) {
			return ResponseEntity.badRequest().body("닉네임은 한 글자 이상 입력해주세요.");
		}

		String ip = (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();

		HttpSession session = request.getSession();
		session.setAttribute("nickname", nickname);

		return ResponseEntity.noContent().build();
	}
}
