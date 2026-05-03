package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.001;

    // --- UC5 Conversion Tests ---

    @Test
    void testConversion_FeetToInches() {
        // convert(1.0, FEET, INCHES) should return 12.0.
        double result = QuantityMeasurementApp.QuantityLength.convert(1.0, QuantityMeasurementApp.LengthUnit.FEET, QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(12.0, result, EPSILON);
    }

    @Test
    void testConversion_InchesToFeet() {
        // convert(24.0, INCHES, FEET) should return 2.0.
        double result = QuantityMeasurementApp.QuantityLength.convert(24.0, QuantityMeasurementApp.LengthUnit.INCH, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testConversion_YardsToFeet() {
        assertEquals(9.0, QuantityMeasurementApp.QuantityLength.convert(3.0, QuantityMeasurementApp.LengthUnit.YARD, QuantityMeasurementApp.LengthUnit.FEET), EPSILON);
    }

    @Test
    void testConversion_InchesToYards() {
        assertEquals(1.0, QuantityMeasurementApp.QuantityLength.convert(36.0, QuantityMeasurementApp.LengthUnit.INCH, QuantityMeasurementApp.LengthUnit.YARD), EPSILON);
    }

    @Test
    void testConversion_CMToInches() {
        assertEquals(0.393701, QuantityMeasurementApp.QuantityLength.convert(1.0, QuantityMeasurementApp.LengthUnit.CM, QuantityMeasurementApp.LengthUnit.INCH), EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        assertEquals(0.0, QuantityMeasurementApp.QuantityLength.convert(0.0, QuantityMeasurementApp.LengthUnit.FEET, QuantityMeasurementApp.LengthUnit.INCH), EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        assertEquals(-12.0, QuantityMeasurementApp.QuantityLength.convert(-1.0, QuantityMeasurementApp.LengthUnit.FEET, QuantityMeasurementApp.LengthUnit.INCH), EPSILON);
    }

    // --- UC5 Round-Trip & Transitive Tests ---

    @Test
    void testConversion_RoundTrip() {
        // A -> B -> A should yield original value
        double value = 10.0;
        double toInches = QuantityMeasurementApp.QuantityLength.convert(value, QuantityMeasurementApp.LengthUnit.FEET, QuantityMeasurementApp.LengthUnit.INCH);
        double backToFeet = QuantityMeasurementApp.QuantityLength.convert(toInches, QuantityMeasurementApp.LengthUnit.INCH, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(value, backToFeet, EPSILON);
    }

    @Test
    void testInstanceConversion_ConvertTo() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength inches = feet.convertTo(QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(12.0, inches.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.INCH, inches.getUnit());
    }

    // --- UC5 Validation & Error Handling ---

    @Test
    void testValidation_NullUnits() {
        assertThrows(NullPointerException.class, () -> 
            QuantityMeasurementApp.QuantityLength.convert(1.0, null, QuantityMeasurementApp.LengthUnit.INCH));
        assertThrows(NullPointerException.class, () -> 
            QuantityMeasurementApp.QuantityLength.convert(1.0, QuantityMeasurementApp.LengthUnit.FEET, null));
    }

    @Test
    void testValidation_NaNValue() {
        assertThrows(IllegalArgumentException.class, () -> 
            new QuantityMeasurementApp.QuantityLength(Double.NaN, QuantityMeasurementApp.LengthUnit.FEET));
    }

    @Test
    void testValidation_InfiniteValue() {
        assertThrows(IllegalArgumentException.class, () -> 
            new QuantityMeasurementApp.QuantityLength(Double.POSITIVE_INFINITY, QuantityMeasurementApp.LengthUnit.FEET));
    }

    // --- Backward Compatibility & ToString ---

    @Test
    void testToString_Formatting() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals("1.00 FEET", feet.toString());
    }

    @Test
    void testEquality_WithRounding() {
        // 1 cm is approx 0.393701 inches. 
        QuantityMeasurementApp.QuantityLength cm = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.CM);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(0.393701, QuantityMeasurementApp.LengthUnit.INCH);
        assertTrue(cm.equals(inches), "Should be equal within rounding tolerance");
    }
}
