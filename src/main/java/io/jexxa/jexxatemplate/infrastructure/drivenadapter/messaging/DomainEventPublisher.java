package io.jexxa.jexxatemplate.infrastructure.drivenadapter.messaging;

import io.jexxa.jexxatemplate.domain.book.BookSoldOut;
import io.jexxa.jexxatemplate.domainservice.IDomainEventPublisher;
import io.jexxa.addend.infrastructure.DrivenAdapter;
import io.jexxa.infrastructure.drivenadapterstrategy.messaging.MessageSender;

import java.util.Objects;
import java.util.Properties;

import static io.jexxa.infrastructure.drivenadapterstrategy.messaging.MessageSenderManager.getMessageSender;

@SuppressWarnings("unused")
@DrivenAdapter
public class DomainEventPublisher implements IDomainEventPublisher
{
    private final MessageSender messageSender;

    public DomainEventPublisher(Properties properties)
    {
        messageSender = getMessageSender(IDomainEventPublisher.class, properties);
    }

    @Override
    public void publish(BookSoldOut domainEvent)
    {
        Objects.requireNonNull(domainEvent);
        messageSender
                .send(domainEvent)
                .toTopic("BookStoreTopic")
                .addHeader("Type", domainEvent.getClass().getSimpleName())
                .asJson();
    }
}
