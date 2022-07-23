package io.jexxa.jexxatemplate.domainservice;

import io.jexxa.addend.applicationcore.DomainService;
import io.jexxa.jexxatemplate.domain.book.Book;
import io.jexxa.jexxatemplate.domain.book.IBookRepository;
import io.jexxa.jexxatemplate.domain.book.ISBN13;

import java.util.Objects;
import java.util.stream.Stream;

import static io.jexxa.jexxatemplate.domain.book.ISBN13.createISBN;

@DomainService
public class ReferenceLibrary
{
    private final IBookRepository bookRepository;

    public ReferenceLibrary(IBookRepository bookRepository)
    {
        this.bookRepository = Objects.requireNonNull(bookRepository);
    }

    public void addLatestBooks()
    {
        getLatestBooks()
                .filter(book -> ! bookRepository.isRegistered(book)) // Filter already maintained books.
                .forEach(isbn13 -> bookRepository.add(Book.newBook(isbn13)));
    }

    /** Some Random books found in internet */
    private Stream<ISBN13> getLatestBooks()
    {
        return Stream.of(
                createISBN("978-1-60309-025-4" ),
                createISBN("978-1-60309-025-4" ),
                createISBN("978-1-60309-047-6" ),
                createISBN("978-1-60309-322-4" ),
                createISBN("978-1-891830-85-3" ),
                createISBN("978-1-60309-016-2" ),
                createISBN("978-1-60309-265-4" )
        );
    }
}
