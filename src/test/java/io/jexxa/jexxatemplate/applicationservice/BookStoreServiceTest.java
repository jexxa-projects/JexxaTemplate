package io.jexxa.jexxatemplate.applicationservice;

import io.jexxa.core.JexxaMain;
import io.jexxa.jexxatemplate.JexxaTemplate;
import io.jexxa.jexxatemplate.domain.businessexception.BookNotInStockException;
import io.jexxa.jexxatemplate.domain.domainevent.BookSoldOut;
import io.jexxa.jexxatemplate.domain.valueobject.ISBN13;
import io.jexxa.jexxatemplate.domainservice.IBookRepository;
import io.jexxa.jexxatemplate.domainservice.IDomainEventPublisher;
import io.jexxa.jexxatest.JexxaTest;
import io.jexxa.jexxatest.infrastructure.drivenadapterstrategy.messaging.recording.MessageRecorder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.jexxa.jexxatemplate.domain.domainevent.BookSoldOut.bookSoldOut;
import static io.jexxa.jexxatemplate.domain.valueobject.ISBN13.createISBN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookStoreServiceTest
{
    private static final ISBN13 ISBN_13 = createISBN("978-3-86490-387-8" );
    private static JexxaMain jexxaMain;
    private BookStoreService objectUnderTest;

    private MessageRecorder publishedDomainEvents;
    private IBookRepository bookRepository;


    @BeforeAll
    static void initBeforeAll()
    {
        // We recommend instantiating JexxaMain only once for each test class.
        // If you have larger tests this speeds up Jexxa's dependency injection
        jexxaMain = new JexxaMain(BookStoreServiceTest.class)
                .addDDDPackages(JexxaTemplate.class);
    }

    @BeforeEach
    void initTest()
    {
        // JexxaTest is created for each test. It provides stubs for running your tests so that no
        // mock framework is required.
        JexxaTest jexxaTest = new JexxaTest(jexxaMain);

        // Query a message recorder for an interface which is defines in your application core.
        publishedDomainEvents = jexxaTest.getMessageRecorder(IDomainEventPublisher.class);
        // Query the repository that is internally used.
        bookRepository = jexxaTest.getRepository(IBookRepository.class);
        // Query the application service we want to test.
        objectUnderTest = jexxaTest.getInstanceOfPort(BookStoreService.class);
    }

    @Test
    void receiveBook()
    {
        //Arrange
        var amount = 5;

        //Act
        objectUnderTest.addToStock(ISBN_13.value(), amount);

        //Assert - Here you can also use all the interfaces for driven adapters defined in your application without running the infrastructure
        assertEquals( amount, objectUnderTest.amountInStock(ISBN_13) );
        assertEquals( amount, bookRepository.get( ISBN_13 ).amountInStock() );
        assertTrue( publishedDomainEvents.isEmpty() );
    }


    @Test
    void sellBook() throws BookNotInStockException
    {
        //Arrange
        var amount = 5;
        objectUnderTest.addToStock(ISBN_13.value(), amount);

        //Act
        objectUnderTest.sell(ISBN_13);

        //Assert - Here you can also use all the interfaces for driven adapters defined in your application without running the infrastructure
        assertEquals( amount - 1, objectUnderTest.amountInStock(ISBN_13) );
        assertEquals( amount - 1, bookRepository.get(ISBN_13).amountInStock() );
        assertTrue( publishedDomainEvents.isEmpty() );
    }

    @Test
    void sellBookNotInStock()
    {
        //Arrange - Nothing

        //Act/Assert
        assertThrows(BookNotInStockException.class, () -> objectUnderTest.sell(ISBN_13));
    }

    @Test
    void sellLastBook() throws BookNotInStockException
    {
        //Arrange
        objectUnderTest.addToStock(ISBN_13.value(), 1);

        //Act
        objectUnderTest.sell(ISBN_13);

        //Assert - Here you can also use all the interfaces for driven adapters defined in your application without running the infrastructure
        assertEquals( 0 , objectUnderTest.amountInStock(ISBN_13) );
        assertEquals( 1 , publishedDomainEvents.size() );
        assertEquals( bookSoldOut(ISBN_13), publishedDomainEvents.getMessage(BookSoldOut.class));
    }

}