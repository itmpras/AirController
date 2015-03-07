package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import com.prasanna.aircontroller.messages.AltimeterUpdated;
import com.prasanna.aircontroller.messages.ControlSurfaceInstance;
import com.prasanna.aircontroller.messages.GiveMeControl;
import com.prasanna.aircontroller.messages.RegisterEventSource;

/**
 * Created by gopinithya on 07/03/15.
 */
public class Plane extends UntypedActor {

    ActorRef altiMeter;
    ActorRef controlSurface;
    private final LoggingAdapter log;

    public Plane() {

        EventSource eventSource = new EventSource();
        Props props = Props.create(AltiMeter.class, eventSource);
        altiMeter = context().actorOf(props, "AltiMeter");
        Props controlSurfaceProp = Props.create(ControlSurfaces.class, altiMeter);
        controlSurface = context().actorOf(controlSurfaceProp, "ControlSurface");
        log = context().system().log();

        altiMeter.tell(new RegisterEventSource(self()), self());

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof GiveMeControl) {
            log.info("Received Give Me Control Request ");
            getSender().tell(new ControlSurfaceInstance(controlSurface), self());
        } else if (o instanceof AltimeterUpdated) {
            log.info(" Altimeter updated event received");

        }
    }
}
