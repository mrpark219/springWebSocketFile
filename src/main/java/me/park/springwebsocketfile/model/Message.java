package me.park.springwebsocketfile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Message {

	MessageTypeEnum type;

	String message;

	String nickname;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime time;

	public Message(MessageTypeEnum type, String message, String nickname, LocalDateTime time) {
		this.type = type;
		this.message = message;
		this.nickname = nickname;
		this.time = time;
	}
}
