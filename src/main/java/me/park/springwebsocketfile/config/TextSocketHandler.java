package me.park.springwebsocketfile.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.park.springwebsocketfile.model.Message;
import me.park.springwebsocketfile.model.MessageTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Component
public class TextSocketHandler extends TextWebSocketHandler {

	HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

	ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) {

		String textMessagePayload = textMessage.getPayload();
		String nickname = String.valueOf(webSocketSession.getAttributes().get("nickname"));

		Message message = Message.builder().type(MessageTypeEnum.MESSAGE_SELF).message(textMessagePayload).nickname(nickname).time(LocalDateTime.now()).build();
		sendSelf(webSocketSession, message);

		message.setType(MessageTypeEnum.MESSAGE);
		sendAllExceptSelf(webSocketSession, message);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		sessionMap.put(webSocketSession.getId(), webSocketSession);

		Message message = Message.builder().type(MessageTypeEnum.MESSAGE_NOTICE).message("채팅방에 연결되었습니다.").time(LocalDateTime.now()).build();
		sendSelf(webSocketSession, message);

		String nickname = String.valueOf(webSocketSession.getAttributes().get("nickname"));
		message.setMessage(nickname + "님이 접속했습니다.");
		sendAllExceptSelf(webSocketSession, message);

		super.afterConnectionEstablished(webSocketSession);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) throws Exception {
		sessionMap.remove(webSocketSession.getId());

		String nickname = String.valueOf(webSocketSession.getAttributes().get("nickname"));
		Message message = Message.builder().type(MessageTypeEnum.MESSAGE_NOTICE).message(nickname + "님이 나갔습니다.").time(LocalDateTime.now()).build();

		sendAllExceptSelf(webSocketSession, message);

		super.afterConnectionClosed(webSocketSession, status);
	}

	private void sendAllExceptSelf(WebSocketSession webSocketSession, Message message) {
		sessionMap.forEach((key, value) -> {
			if(!webSocketSession.getId().equals(key)) {
				try {
					value.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
				}
				catch(IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	private void sendSelf(WebSocketSession webSocketSession, Message message) {
		try {
			webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
