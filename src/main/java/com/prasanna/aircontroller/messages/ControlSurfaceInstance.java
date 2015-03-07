package com.prasanna.aircontroller.messages;

import akka.actor.ActorRef;

/**
 * Created by gopinithya on 07/03/15.
 */
public class ControlSurfaceInstance {

    ActorRef controlSurface;

    public ControlSurfaceInstance(ActorRef controlSurface) {
        this.controlSurface = controlSurface;
    }

    public ActorRef getControlSurface() {
        return controlSurface;
    }
}


