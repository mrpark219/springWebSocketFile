package me.park.springwebsocketfile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final TextSocketHandler textSocketHandler;

	public WebSocketConfig(TextSocketHandler textSocketHandler) {
		this.textSocketHandler = textSocketHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(textSocketHandler, "/ws/chat").addInterceptors(new HttpSessionHandshakeInterceptor()).setAllowedOrigins("*");
	}
}
