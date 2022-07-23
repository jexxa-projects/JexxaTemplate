package io.jexxa.jexxatemplate.domainservice;


import io.jexxa.addend.applicationcore.InfrastructureService;
import io.jexxa.jexxatemplate.domain.book.BookSoldOut;

@InfrastructureService
public interface IDomainEventPublisher
{
    void publish(BookSoldOut domainEvent);
}
