package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import com.prasanna.aircontroller.messages.*;
import com.typesafe.config.Config;

/**
 * Created by gopinithya on 08/03/15.
 */
public class Pilot extends UntypedActor {

    private ActorRef coPilot;
    private ActorRef autoPilot;
    private ActorRef controlSurface;
    private LoggingAdapter log;

    @Override
    public void preStart() throws Exception {

        super.preStart();
        log = getContext().system().log();

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof StartPlane) {
            log.info("Requesting ControlSurface from Parent ");
            getContext().parent().tell(new GiveMeControl(), self());
        } else if (o instanceof ControlSurfaceInstance) {
            log.info("Received ControlSurface ");
            ControlSurfaceInstance controlSurfaceInstance = (ControlSurfaceInstance) o;
            controlSurface = controlSurfaceInstance.getControlSurface();
            autoPilot = context().actorFor("../autoPilot");
            String name = getContext().system().settings().config().getString("com.prasanna.flightcrew.copilotName");
            coPilot = context().actorFor("../" + name);

            log.info("Getting in touch with friends ");
            coPilot.tell("Check", self());
            autoPilot.tell("Check", self());

            controlSurface.tell(new StickBack(0.2f), self());
        }

    }
}
