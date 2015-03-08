package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by gopinithya on 07/03/15.
 */
public class EventSource {

    List<ActorRef> actorRefs;

    public EventSource() {

        this.actorRefs = Lists.newArrayList();
    }

    public void registerActor(ActorRef actorRef) {

        actorRefs.add(actorRef);
    }

    public void unregisterActor(ActorRef actorRef) {

        actorRefs.remove(actorRef);
    }

    public void publishEvent(Object event, ActorRef sender) {

        for (ActorRef actorRef : actorRefs) {
            actorRef.tell(event, sender);
            sender.tell("Received", ActorRef.noSender());
        }
    }
}
