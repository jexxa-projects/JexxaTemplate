package io.jexxa.jexxatemplate.domain.book;

import io.jexxa.addend.applicationcore.DomainEvent;

import java.util.Objects;

@DomainEvent
public record BookSoldOut(ISBN13 isbn13) {

    public static BookSoldOut bookSoldOut(ISBN13 isbn13) {
        Objects.requireNonNull(isbn13);
        return new BookSoldOut(isbn13);
    }
}
