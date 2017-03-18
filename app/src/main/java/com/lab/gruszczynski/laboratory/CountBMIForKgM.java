package com.lab.gruszczynski.laboratory;

/**
 * Created by maciej on 16.03.17.
 */
public class CountBMIForKgM implements ICountBMI{
    static final float MIN_MASS = 10.0f;
    static final float MAX_MASS = 250.0f;
    static final float MIN_HEIGHT = 0.5f;
    static final float MAX_HEIGHT = 2.5f;

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
            return mass / height / height;
        }
        else throw new IllegalArgumentException("too skinny/fat/high/low");
    }
}
