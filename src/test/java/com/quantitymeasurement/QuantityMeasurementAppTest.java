package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // --- UC4 Yard Tests ---

    @Test
    void testEquality_YardToYard_SameValue() {
        QuantityMeasurementApp.QuantityLength yard1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength yard2 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    @Test
    void testEquality_YardToYard_DifferentValue() {
        QuantityMeasurementApp.QuantityLength yard1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength yard2 = new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertFalse(yard1.equals(yard2));
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(3.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(3.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertTrue(feet.equals(yard));
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(36.0, QuantityMeasurementApp.LengthUnit.INCH);
        assertTrue(yard.equals(inches));
    }

    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(36.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertTrue(inches.equals(yard));
    }

    // --- UC4 CM Tests ---

    @Test
    void testEquality_CMToCM_SameValue() {
        QuantityMeasurementApp.QuantityLength cm1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.CM);
        QuantityMeasurementApp.QuantityLength cm2 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.CM);
        assertTrue(cm1.equals(cm2));
    }

    @Test
    void testEquality_CMToInches_EquivalentValue() {
        // 1.0 cm = 0.393701 inches
        QuantityMeasurementApp.QuantityLength cm = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.CM);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(0.393701, QuantityMeasurementApp.LengthUnit.INCH);
        assertTrue(cm.equals(inches));
    }

    @Test
    void testEquality_CMToFeet_NonEquivalentValue() {
        QuantityMeasurementApp.QuantityLength cm = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.CM);
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertFalse(cm.equals(feet));
    }

    // --- Transitive Property Test ---

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        // A = B, B = C => A = C
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(3.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(36.0, QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yard.equals(inches));
    }

    // --- Edge Cases ---

    @Test
    void testEquality_YardNullComparison() {
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertFalse(yard.equals(null));
    }

    @Test
    void testEquality_YardSameReference() {
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertTrue(yard.equals(yard));
    }
}
