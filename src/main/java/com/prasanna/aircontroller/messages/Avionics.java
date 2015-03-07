package com.prasanna.aircontroller.messages;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.prasanna.aircontroller.ControlSurfaces;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * Created by gopinithya on 07/03/15.
 */
public class Avionics extends UntypedActor {
    ActorRef plane;
    ActorRef controlSurface;
    LoggingAdapter log;


    public Avionics(ActorRef plane) {
        this.plane = plane;
        log = getContext().system().log();

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof StartPlane) {
            log.info("Starting Plane ");


            //plane.tell(new GiveMeControl(), self());

            //  Timeout timeout = new Timeout(Duration.create(5, "seconds"));

           /* // Blocking code
            Future<Object> future = Patterns.ask(plane, new GiveMeControl(), timeout);
            log.info("RequestingControls ");
            ControlSurfaceInstance result = (ControlSurfaceInstance) Await.result(future, timeout.duration());
            log.info("Received Controls");
            controlSurface = result.getControlSurface();

            controlSurface.tell(new StickBack(0.5F), self()); */


        } else if (o instanceof ControlSurfaceInstance) {

            log.info("Received Controls ");
            ControlSurfaceInstance controlSurfaceInstance = (ControlSurfaceInstance) o;
            controlSurface = controlSurfaceInstance.getControlSurface();

            controlSurface.tell(new StickBack(0.5F), self());
        }

    }
}
