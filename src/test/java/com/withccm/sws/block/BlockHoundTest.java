package com.withccm.sws.block;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
public class BlockHoundTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void nonBlockCallTest() {
		webTestClient.get().uri("/nonBlock").exchange().expectStatus().isOk();
		log.info("Calling nonblock request  is succeeded");
	}

	/**
	 * testImplementation 'io.projectreactor.tools:blockhound-junit-platform:1.0.6.RELEASE'
	 *
	 * blockhound 라이브러리에서 block이 있는 경우에 에러를 발생시킴
	 */
	@Test
	public void blockCallTest() {
		webTestClient.get().uri("/block").exchange().expectStatus().isOk();
	}
}
