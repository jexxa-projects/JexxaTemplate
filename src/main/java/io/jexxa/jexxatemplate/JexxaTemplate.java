package io.jexxa.jexxatemplate;

import io.jexxa.addend.applicationcore.ApplicationService;
import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivingadapter.rest.RESTfulRPCAdapter;
import io.jexxa.jexxatemplate.domainservice.ReferenceLibrary;

public final class JexxaTemplate
{
    public static void main(String[] args)
    {
        var jexxaMain = new JexxaMain(JexxaTemplate.class);

        jexxaMain
                //Get the latest books when starting the application
                .bootstrap(ReferenceLibrary.class).with(ReferenceLibrary::addLatestBooks)

                //bind all application services and the bounded context to driving adapters
                .bind(RESTfulRPCAdapter.class).toAnnotation(ApplicationService.class)
                .bind(RESTfulRPCAdapter.class).to(jexxaMain.getBoundedContext())

                //run the application
                .run();
    }

    private JexxaTemplate()
    {
        //Private constructor since we only offer main
    }


}
