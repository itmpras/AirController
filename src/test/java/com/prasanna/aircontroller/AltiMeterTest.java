package com.prasanna.aircontroller;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;
import akka.testkit.TestKitSettings;
import com.prasanna.aircontroller.messages.AltimeterUpdated;
import com.prasanna.aircontroller.messages.RateOfChange;

import org.junit.Before;

public class AltiMeterTest {

    private TestActorRef<Actor> actorTestActorRef;
    AltiMeter actor;
    JavaTestKit testKit;

    @Before
    public void setUp() throws Exception {

        ActorSystem testActorSystem = ActorSystem.create("TestActorSystem");
        //      testKit = new TestKit(testActorSystem);
        testKit = new JavaTestKit(testActorSystem);
        EventSource eventSource = new EventSource();
        Props props = Props.create(AltiMeter.class, eventSource);
        actor = ((AltiMeter) TestActorRef.create(testActorSystem, props).underlyingActor());

    }

    @org.junit.Test
    public void testRegisterSource() throws Exception {

        ActorRef actorRef = testKit.getTestActor();
        actor.getEventSource().registerActor(actorRef);
        actor.getEventSource().publishEvent(new AltimeterUpdated(0.5F, .2F), actorRef);
        //  actor.onReceive(new RateOfChange(0.4f));
        testKit.expectMsgClass(AltimeterUpdated.class);
        testKit.expectMsgAnyOf("Received");


    }
}