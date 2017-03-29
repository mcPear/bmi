package com.lab.gruszczynski.laboratory;

/**
 * Created by maciej on 29.03.17.
 */
public class CountBMIForLbsFt implements ICountBMI{

    static final float MIN_MASS = 22.0f;
    static final float MAX_MASS = 550.0f;
    static final float MIN_HEIGHT = 1.5f;
    static final float MAX_HEIGHT = 8.0f;
    static final float lbsToKgFactor = 0.45f;
    static final float footToMeterFactor = 0.3f;

    @Override
    public boolean isMassValid(float mass) {
        return mass>=MIN_MASS&&mass<=MAX_MASS;
    }

    @Override
    public boolean isHeightValid(float height) {
        return height>=MIN_HEIGHT&&height<=MAX_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) {
        if(isMassValid(mass)&&isHeightValid(height)) {
            return lbsToKg(mass) / (float)Math.pow(footToMeter(height),2);
        }
        else throw new IllegalArgumentException("too skinny/fat/high/low");
    }

    private float lbsToKg(float value){
        return value*lbsToKgFactor;
    }

    private float footToMeter(float value){
        return value*footToMeterFactor;
    }


}
