package io.jexxa.jexxatemplate;

import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivingadapter.rest.RESTfulRPCAdapter;
import io.jexxa.jexxatemplate.applicationservice.BookStoreService;
import io.jexxa.jexxatemplate.domainservice.ReferenceLibrary;
import io.jexxa.jexxatemplate.infrastructure.support.JsonRecordConverter;

public final class JexxaTemplate
{
    public static void main(String[] args)
    {
        //Set a JsonConverter that support java records
        JsonRecordConverter.registerRecordFactory();

        var jexxaMain = new JexxaMain(JexxaTemplate.class);

        jexxaMain
                //Define the default packages for inbound and outbound ports
                .addDDDPackages(JexxaTemplate.class)

                //Get the latest books when starting the application
                .bootstrap(ReferenceLibrary.class).with(ReferenceLibrary::addLatestBooks)

                //bind all application services and the bounded context to driving adapters
                .bind(RESTfulRPCAdapter.class).to(BookStoreService.class)
                .bind(RESTfulRPCAdapter.class).to(jexxaMain.getBoundedContext())

                //control the application itself
                .start()
                .waitForShutdown()
                .stop();
    }

    private JexxaTemplate()
    {
        //Private constructor since we only offer main
    }


}
