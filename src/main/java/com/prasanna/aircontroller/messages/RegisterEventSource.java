package com.prasanna.aircontroller.messages;

import akka.actor.ActorRef;

/**
 * Created by gopinithya on 07/03/15.
 */
public class RegisterEventSource {
    private ActorRef actorRef;

    public RegisterEventSource(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }
}
