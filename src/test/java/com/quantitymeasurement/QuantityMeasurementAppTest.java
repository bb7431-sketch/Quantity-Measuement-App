package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.001;

    // --- UC8 Standalone LengthUnit Tests ---

    @Test
    void testLengthUnitEnum_FeetConstant() {
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
    }

    @Test
    void testLengthUnitEnum_InchesConstant() {
        // 1/12 = 0.0833
        assertEquals(0.0833, LengthUnit.INCH.getConversionFactor(), EPSILON);
    }

    @Test
    void testConvertToBaseUnit_InchesToFeet() {
        // 12 inches should be 1.0 foot (base unit)
        assertEquals(1.0, LengthUnit.INCH.convertToBaseUnit(12.0), EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToInches() {
        // 1.0 foot should be 12.0 inches
        assertEquals(12.0, LengthUnit.INCH.convertFromBaseUnit(1.0), EPSILON);
    }

    @Test
    void testConvertToBaseUnit_YardsToFeet() {
        assertEquals(3.0, LengthUnit.YARD.convertToBaseUnit(1.0), EPSILON);
    }

    // --- UC8 QuantityLength Delegation Tests ---

    @Test
    void testQuantityLengthRefactored_Equality() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(12.0, LengthUnit.INCH);
        assertTrue(feet.equals(inches));
    }

    @Test
    void testQuantityLengthRefactored_ConvertTo() {
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength feet = yard.convertTo(LengthUnit.FEET);
        assertEquals(3.0, feet.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, feet.getUnit());
    }

    @Test
    void testQuantityLengthRefactored_Add() {
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(12.0, LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, LengthUnit.YARD);
        // 2 feet = 0.666 yards
        assertEquals(0.666, result.getValue(), 0.01);
    }

    // --- Backward Compatibility Verification (UC1–UC7) ---

    @Test
    void testBackwardCompatibility_AllUnitsEquality() {
        assertTrue(new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.YARD).equals(new QuantityMeasurementApp.QuantityLength(3.0, LengthUnit.FEET)));
        assertTrue(new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.YARD).equals(new QuantityMeasurementApp.QuantityLength(36.0, LengthUnit.INCH)));
        // CM to Inch: 1 cm = 0.393701 inches
        assertTrue(new QuantityMeasurementApp.QuantityLength(1.0, LengthUnit.CM).equals(new QuantityMeasurementApp.QuantityLength(0.393701, LengthUnit.INCH)));
    }

    @Test
    void testValidation_NullUnit() {
        assertThrows(NullPointerException.class, () -> new QuantityMeasurementApp.QuantityLength(1.0, null));
    }

    @Test
    void testRoundTripConversion_Precision() {
        double originalValue = 10.0;
        double feetToBase = LengthUnit.FEET.convertToBaseUnit(originalValue);
        double backToFeet = LengthUnit.FEET.convertFromBaseUnit(feetToBase);
        assertEquals(originalValue, backToFeet, EPSILON);

        double inchToBase = LengthUnit.INCH.convertToBaseUnit(originalValue);
        double backToInch = LengthUnit.INCH.convertFromBaseUnit(inchToBase);
        assertEquals(originalValue, backToInch, EPSILON);
    }
}
