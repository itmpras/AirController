package com.prasanna;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.prasanna.aircontroller.LeadAttender;
import com.prasanna.aircontroller.Plane;
import com.prasanna.aircontroller.Avionics;
import com.prasanna.aircontroller.messages.GiveMeControl;
import com.prasanna.aircontroller.messages.StartPlane;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * Created by gopinithya on 07/03/15.
 */
public class AirControllerMain {

    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("AirConrollerSystem");
        Props planProps = Props.create(Plane.class);
        ActorRef plane = actorSystem.actorOf(planProps);
        Props avionicsProps = Props.create(Avionics.class, plane);
        ActorRef avionics = actorSystem.actorOf(avionicsProps);
        avionics.tell(new StartPlane(), ActorRef.noSender());

        // Asynchronous request
       /* Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(plane, new GiveMeControl(), timeout);
        akka.pattern.Patterns.pipe(future, actorSystem.dispatcher()).to(avionics);*/

    }
}
