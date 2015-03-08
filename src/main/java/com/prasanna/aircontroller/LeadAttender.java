package com.prasanna.aircontroller;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;

import java.util.List;

/**
 * Created by gopinithya on 08/03/15.
 */
public class LeadAttender extends UntypedActor {

    private LoggingAdapter log;

    @Override
    public void preStart() throws Exception {

        super.preStart();
        log = getContext().system().log();
        List<String> stringList = getContext().system().settings().config().getStringList("com.prasanna.flightcrew.attendentNames");
        log.info("Attender size {} ", stringList.size());
        Props attendets = Props.create(FlightAttendent.class);
        for (String attendents : stringList) {
            getContext().actorOf(attendets, attendents);
        }
    }

    @Override
    public void onReceive(Object o) throws Exception {

    }
}
