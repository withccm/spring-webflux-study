package com.withccm.sws.book;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookRouter {

	@Bean
	public RouterFunction<ServerResponse> route(BookHandler bookHandler) {
		return RouterFunctions
			.route(RequestPredicates.POST("/books").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), bookHandler::postBooks)
			.andRoute(RequestPredicates.GET("/books").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), bookHandler::getBooks)
			.andRoute(RequestPredicates.GET("/books/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), bookHandler::getBook)
			.andRoute(RequestPredicates.PUT("/books/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), bookHandler::putBooks);
	}
}
