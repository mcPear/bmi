package com.lab.gruszczynski.laboratory;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by maciej on 16.03.17.
 */
public class CountBMITest {

    //mass validator
    @Test
    public void massUnderZero_isInvalid(){
        //GIVEN
        float testMass = -1.0f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void massUnderMIN_MASS_isInvalid(){
        //GIVEN
        float testMass = 9.9f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void massMIN_MASS_isValid(){
        //GIVEN
        float testMass = 10f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isMassValid(testMass);
        assertTrue(actual);
    }

    @Test
    public void massOverMAX_MASS_isInvalid(){
        //GIVEN
        float testMass = 250.1f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void massMAX_MASS_isValid(){
        //GIVEN
        float testMass = 250.0f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isMassValid(testMass);
        assertTrue(actual);
    }

    @Test
    public void normalMass_isValid(){
        //GIVEN
        float testMass = 75.4f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isMassValid(testMass);
        assertTrue(actual);
    }

    //height validator
    @Test
    public void heightUnderZero_isInvalid(){
        //GIVEN
        float testHeight = -1.0f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isHeightValid(testHeight);
        assertFalse(actual);
    }

    @Test
    public void heightUnderMIN_HEIGHT_isInvalid(){
        //GIVEN
        float testHeight = 0.49f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isHeightValid(testHeight);
        assertFalse(actual);
    }

    @Test
    public void heightMIN_HEIGHT_isValid(){
        //GIVEN
        float testHeight = 0.5f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isHeightValid(testHeight);
        assertTrue(actual);
    }

    @Test
    public void heightOverMAX_HEIGHT_isInvalid(){
        //GIVEN
        float testHeight = 2.51f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isHeightValid(testHeight);
        assertFalse(actual);
    }

    @Test
    public void heightMAX_HEIGHT_isValid(){
        //GIVEN
        float testHeight = 2.5f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isHeightValid(testHeight);
        assertTrue(actual);
    }

    @Test
    public void normalHeight_isValid(){
        //GIVEN
        float testHeight = 1.74f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        boolean actual = counter.isHeightValid(testHeight);
        assertTrue(actual);
    }

    @Test
    public void countedBMI_isValid(){
        //GIVEN
        float testHeight = 1.74f;
        float testMass = 63.0f;
        float testCountedBMI = 20.8085612f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        float countedBMI = counter.countBMI(testMass,testHeight);
        assertEquals(testCountedBMI, countedBMI, 0.001f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgument_isInvalid(){
        //GIVEN
        float testHeight = 0.1f;
        float testMass = 63.0f;
        //WHEN
        ICountBMI counter = new CountBMIForKgM();
        //THEN
        float countedBMI = counter.countBMI(testMass,testHeight);
    }


}
