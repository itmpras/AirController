package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import com.prasanna.aircontroller.messages.RateOfChange;
import com.prasanna.aircontroller.messages.StickBack;
import com.prasanna.aircontroller.messages.StickForward;

/**
 * Created by gopinithya on 07/03/15.
 */
public class ControlSurfaces extends UntypedActor {

    private ActorRef altiMeter;
    LoggingAdapter log;

    public ControlSurfaces(ActorRef altiMeter) {
        log = getContext().system().log();

        this.altiMeter = altiMeter;
    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof StickBack) {
            log.info("{} received StickBack event", getSelf().path().name());
            StickBack stickBack = (StickBack) o;
            altiMeter.tell(new RateOfChange(stickBack.getAmount()), self());
        } else if (o instanceof StickForward) {
            StickForward stickForward = (StickForward) o;
            log.info("{} received StickForward event", getSelf().path().name());
            altiMeter.tell(new RateOfChange(-1 * stickForward.getAmount()), self());
        }

    }
}
