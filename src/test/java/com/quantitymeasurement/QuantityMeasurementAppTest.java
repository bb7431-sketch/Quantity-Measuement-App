package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // --- UC1 & UC2 Backward Compatibility Tests ---

    @Test
    void testEquality_FeetToFeet_SameValue() {
        assertTrue(QuantityMeasurementApp.compareFeet(1.0, 1.0));
    }

    @Test
    void testEquality_InchToInch_SameValue() {
        assertTrue(QuantityMeasurementApp.compareInches(1.0, 1.0));
    }

    @Test
    void testEquality_FeetToFeet_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareFeet(1.0, 2.0));
    }

    @Test
    void testEquality_InchToInch_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareInches(1.0, 2.0));
    }

    // --- UC3 Cross-Unit Equality Tests ---

    @Test
    void testEquality_FeetToInch_EquivalentValue() {
        // Verifies that Quantity(1.0, "feet") equals Quantity(12.0, "inch").
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        assertTrue(feet.equals(inches), "1.0 feet should be equal to 12.0 inches");
    }

    @Test
    void testEquality_InchToFeet_EquivalentValue() {
        // Verifies symmetry: 12.0 inches equals 1.0 foot.
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertTrue(inches.equals(feet), "12.0 inches should be equal to 1.0 feet");
    }

    @Test
    void testEquality_FeetToInch_DifferentValue() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.INCH);
        assertFalse(feet.equals(inches), "1.0 feet should not be equal to 1.0 inch");
    }

    // --- Robustness & Edge Cases ---

    @Test
    void testEquality_NullComparison() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertFalse(feet.equals(null));
    }

    @Test
    void testEquality_SameReference() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertTrue(feet.equals(feet));
    }

    @Test
    void testEquality_DifferentType() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertFalse(feet.equals(new Object()));
    }

    @Test
    void testEquality_NullUnit() {
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, null);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertFalse(q1.equals(q2));
    }
}
