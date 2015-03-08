package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestKit;
import com.prasanna.aircontroller.messages.ControlSurfaceInstance;
import com.prasanna.aircontroller.messages.StickBack;
import org.junit.Before;
import org.junit.Test;

public class AvionicsTest {

    private TestKit testKit;

    private ActorRef avionics;
    private TestActor testActor;
    private ActorRef planeActor;


    @Before
    public void setUp() throws Exception {
        ActorSystem testActorSystem = ActorSystem.create("TestActorSystem");
        testKit = new TestKit(testActorSystem);
        planeActor = testKit.testActor();

        Props props = Props.create(Avionics.class, planeActor);
        avionics = testActorSystem.actorOf(props);
    }

    @Test
    public void testOnStartPlane() throws Exception {

        avionics.tell(new ControlSurfaceInstance(testKit.testActor()), ActorRef.noSender());

        testKit.expectMsgClass(StickBack.class);
        testKit.expectMsg("RandomMessage");
    }
}