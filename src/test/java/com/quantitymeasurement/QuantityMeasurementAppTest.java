package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // --- Feet Equality Tests ---

    @Test
    void testFeetEquality_SameValue() {
        assertTrue(QuantityMeasurementApp.compareFeet(1.0, 1.0));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareFeet(1.0, 2.0));
    }

    @Test
    void testFeetEquality_NullComparison() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(feet.equals(null));
    }

    @Test
    void testFeetEquality_SameReference() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(feet.equals(feet));
    }

    // --- Inches Equality Tests (UC2) ---

    @Test
    void testInchesEquality_SameValue() {
        // Verifies that two numerical values of 1.0 inch are considered equal.
        assertTrue(QuantityMeasurementApp.compareInches(1.0, 1.0));
    }

    @Test
    void testInchesEquality_DifferentValue() {
        // Verifies that two numerical values of 1.0 inch and 2.0 inches are not equal.
        assertFalse(QuantityMeasurementApp.compareInches(1.0, 2.0));
    }

    @Test
    void testInchesEquality_NullComparison() {
        // Verifies that a numerical value is not equal to null.
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertFalse(inches.equals(null));
    }

    @Test
    void testInchesEquality_NonNumericInput() {
        // Verifies that type safety prevents equality with different objects.
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertFalse(inches.equals(new Object()));
    }

    @Test
    void testInchesEquality_SameReference() {
        // Verifies the reflexive property for Inches.
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertTrue(inches.equals(inches));
    }

    @Test
    void testInchesAndFeetEquality_ShouldBeFalse() {
        // UC2 mentions they are treated separately.
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertFalse(feet.equals(inches));
    }
}
