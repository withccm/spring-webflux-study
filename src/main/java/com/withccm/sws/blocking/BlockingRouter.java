package com.withccm.sws.blocking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration
public class BlockingRouter {

	@Bean
	public RouterFunction<ServerResponse> blockRouter() {
		return RouterFunctions.route(RequestPredicates.GET("/nonBlock").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), request -> {
			return ServerResponse.ok().body(Mono.just("nonblock response"), String.class);
		}).andRoute(RequestPredicates.GET("/block").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), request -> {
			block();	// 직접 block
			return ServerResponse.ok().body(Mono.just("block response"), String.class);
		});
	}

	private void block() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
