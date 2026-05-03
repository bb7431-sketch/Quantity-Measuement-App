package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testEquality_SameValue() {
        // Verifies that two numerical values of 1.0 ft are considered equal.
        QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(feet1.equals(feet2), "1.0 ft should be equal to 1.0 ft");
    }

    @Test
    void testEquality_DifferentValue() {
        // Verifies that two numerical values of 1.0 ft and 2.0 ft are not equal.
        QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(2.0);
        assertFalse(feet1.equals(feet2), "1.0 ft should not be equal to 2.0 ft");
    }

    @Test
    void testEquality_NullComparison() {
        // Verifies that a numerical value is not equal to null.
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(feet.equals(null), "1.0 ft should not be equal to null");
    }

    @Test
    void testEquality_NonNumericInput() {
        // Verifies that non-numeric inputs are handled appropriately.
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(feet.equals(new Object()), "1.0 ft should not be equal to an Object of different type");
    }

    @Test
    void testEquality_SameReference() {
        // Verifies that a numerical value is equal to itself (reflexive property).
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(feet.equals(feet), "1.0 ft should be equal to itself");
    }
}
