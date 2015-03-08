package com.prasanna.aircontroller;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.LoggingAdapter;
import com.prasanna.aircontroller.messages.AltimeterUpdated;
import com.prasanna.aircontroller.messages.RateOfChange;
import com.prasanna.aircontroller.messages.RegisterEventSource;

import java.util.concurrent.TimeUnit;

/**
 * Created by gopinithya on 07/03/15.
 */
public class AltiMeter extends UntypedActor {

    private float ceiling = 43000f;
    private int maxRateOfClimb = 5000;
    private float rateOfClimb = 0f;
    private float altitude = 0;
    private long lastTick;
    LoggingAdapter log;
    EventSource eventSource;

    public AltiMeter(EventSource eventSource) {

        log = getContext().system().log();
        Tick tick = new Tick();
        getContext().system().scheduler().schedule(scala.concurrent.duration.Duration.Zero(),
                scala.concurrent.duration.Duration.create(1, TimeUnit.SECONDS), self(), tick,
                getContext().dispatcher().prepare(), ActorRef.noSender());
        this.eventSource = eventSource;
        lastTick = System.currentTimeMillis();

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof Tick) {
            long currentTimeMillis = System.currentTimeMillis();
            altitude = altitude + (((currentTimeMillis - lastTick) / 1000) * rateOfClimb * 0.5F);
            log.info("Current Rate of Climb " + rateOfClimb);
            log.info("Current Altitude " + altitude);
            lastTick = currentTimeMillis;
            publishEvent(new AltimeterUpdated(rateOfClimb, altitude));

        } else if (o instanceof RateOfChange) {
            RateOfChange rateOfChange = (RateOfChange) o;
            rateOfClimb = rateOfChange.getRateOfClimb() * maxRateOfClimb;
            publishEvent(new AltimeterUpdated(rateOfClimb, altitude));
            log.info("{} received Rate of Change event ", getSelf().path().name());
            log.info("Rate of change  {} ", rateOfChange.getRateOfClimb());
            log.info("Rate of Climb , changed to {} ", rateOfClimb);

        } else if (o instanceof RegisterEventSource) {
            RegisterEventSource registerEventSource = (RegisterEventSource) o;
            eventSource.registerActor(registerEventSource.getActorRef());
        }
    }

    private void publishEvent(AltimeterUpdated altimeterUpdated) {

        eventSource.publishEvent(altimeterUpdated, self());
    }

    public EventSource getEventSource() {

        return eventSource;
    }
}

class Tick {
    @Override
    public String toString() {

        return "Tick{}" + System.currentTimeMillis();
    }
}