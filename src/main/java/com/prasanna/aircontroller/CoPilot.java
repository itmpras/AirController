package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;

/**
 * Created by gopinithya on 08/03/15.
 */
public class CoPilot extends UntypedActor {

    private ActorRef pilot;
    private ActorRef autoPilot;
    private LoggingAdapter log;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        log = getContext().system().log();

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o.equals("Check")){
            log.info("Rodger That!!");
        }

    }
}
