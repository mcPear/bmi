package com.lab.gruszczynski.laboratory;

/**
 * Created by maciej on 16.03.17.
 */
public interface ICountBMI {

    boolean isMassValid(float mass);

    boolean isHeightValid(float height);

    float countBMI(float mass, float height);

}
