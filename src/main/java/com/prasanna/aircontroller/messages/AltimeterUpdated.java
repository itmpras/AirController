package com.prasanna.aircontroller.messages;

/**
 * Created by gopinithya on 07/03/15.
 */
public class AltimeterUpdated {

    private float rateOfClimb;
    private float altitude;

    public AltimeterUpdated(float rateOfClimb, float altitude) {
        this.rateOfClimb = rateOfClimb;
        this.altitude = altitude;
    }

    public float getRateOfClimb() {
        return rateOfClimb;
    }

    public float getAltitude() {
        return altitude;
    }
}
