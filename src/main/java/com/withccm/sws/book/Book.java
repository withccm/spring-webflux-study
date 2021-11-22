package com.withccm.sws.book;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(value = "book")	// @Table 을 사용해야 bean으로 등록이됨
public class Book {

	@Id
	private Integer id;
	private String bookname;
}
