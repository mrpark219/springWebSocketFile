package me.park.springwebsocketfile.chat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@GetMapping("")
	public String chat(HttpServletRequest request) {

		HttpSession session = request.getSession();
		if(session.getAttribute("nickname") == null || session.getAttribute("nickname").equals("")) {
			return "redirect:/login";
		}

		return "chat";
	}

}
