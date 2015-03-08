package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import com.prasanna.aircontroller.messages.*;
import com.typesafe.config.Config;

/**
 * Created by gopinithya on 07/03/15.
 */
public class Plane extends UntypedActor {

    private ActorRef altiMeter;
    private ActorRef controlSurface;
    private ActorRef pilot;
    private ActorRef coPilot;
    private ActorRef autoPilot;
    private ActorRef leadAttendent;

    private final LoggingAdapter log;

    public Plane() {

        EventSource eventSource = new EventSource();

        Props props = Props.create(AltiMeter.class, eventSource);

        Props leadAttender = Props.create(LeadAttender.class);
        Props pilotProps = Props.create(Pilot.class);
        Props autoPilotProps = Props.create(AutoPilot.class);
        Props coPilotProps = Props.create(CoPilot.class);
        Config config = getContext().system().settings().config();

        altiMeter = context().actorOf(props, "AltiMeter");
        Props controlSurfaceProp = Props.create(ControlSurfaces.class, altiMeter);
        controlSurface = context().actorOf(controlSurfaceProp, "ControlSurface");
        leadAttendent = getContext().actorOf(leadAttender, config.getString("com.prasanna.flightcrew.leadAttendantName"));
        pilot = getContext().actorOf(pilotProps, config.getString("com.prasanna.flightcrew.pilotName"));
        coPilot = getContext().actorOf(coPilotProps, config.getString("com.prasanna.flightcrew.copilotName"));
        autoPilot = getContext().actorOf(autoPilotProps, "autoPilot");
        log = context().system().log();
        altiMeter.tell(new RegisterEventSource(self()), self());

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof GiveMeControl) {
            log.info("Received Give Me Control Request ");
            getSender().tell(new ControlSurfaceInstance(controlSurface), self());
        } else if (o instanceof AltimeterUpdated) {
            log.info(" Altimeter updated event received {}", getSender());

        } else if (o instanceof StartPlane) {
            pilot.tell(o, self());
            coPilot.tell(o, self());
            autoPilot.tell(o, self());
        }
    }
}
