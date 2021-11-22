package com.withccm.sws.book;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookHandler {

	private final BookRepository bookRepository;

	public Mono<ServerResponse> postBooks(ServerRequest serverRequest) {
		Mono<BookRequest> bookRequestMono = serverRequest.bodyToMono(BookRequest.class);
		Mono<Book> bookMono = bookRequestMono.map(bookRequest -> Book.builder().bookname(bookRequest.getBookname()).build()).flatMap(book -> bookRepository.save(book));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(bookMono, Book.class);
	}

	public Mono<ServerResponse> getBooks(ServerRequest serverRequest) {
		Flux<Book> bookFlux = bookRepository.findAll();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(bookFlux, Book.class);
	}

	public Mono<ServerResponse> getBook(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");

		Mono<Book> bookMono = bookRepository.findById(Integer.valueOf(id));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(bookMono, Book.class);
	}

	public Mono<ServerResponse> putBooks(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");

		Mono<BookRequest> bookRequestMono = serverRequest.bodyToMono(BookRequest.class);
		Mono<Book> bookMono = bookRequestMono.map(bookRequest -> Book.builder().id(Integer.valueOf(id)).bookname(bookRequest.getBookname()).build())
			.flatMap(book -> bookRepository.save(book));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(bookMono, Book.class);
	}

}
