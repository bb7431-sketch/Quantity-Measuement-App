package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.001;

    // --- UC7 Explicit Target Unit Addition Tests ---

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        // Add (1.0 FEET, 12.0 INCHES, FEET) should return 2.0 FEET
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, QuantityMeasurementApp.LengthUnit.FEET);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        // Add (1.0 FEET, 12.0 INCHES, INCHES) should return 24.0 INCHES
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, QuantityMeasurementApp.LengthUnit.INCH);
        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.INCH, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        // Add (1.0 FEET, 12.0 INCHES, YARDS) should return ~0.667 YARDS
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, QuantityMeasurementApp.LengthUnit.YARD);
        // 2 feet = 2/3 yards = 0.666...
        assertEquals(0.666, result.getValue(), 0.01);
        assertEquals(QuantityMeasurementApp.LengthUnit.YARD, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {
        // Add (1.0 INCH, 1.0 INCH, CM) should return ~5.08 CM
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, QuantityMeasurementApp.LengthUnit.CM);
        assertEquals(5.08, result.getValue(), 0.1);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.LengthUnit target = QuantityMeasurementApp.LengthUnit.YARD;

        QuantityMeasurementApp.QuantityLength r1 = q1.add(q2, target);
        QuantityMeasurementApp.QuantityLength r2 = q2.add(q1, target);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
        assertEquals(r1.getUnit(), r2.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_WithZero() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength zero = new QuantityMeasurementApp.QuantityLength(0.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = feet.add(zero, QuantityMeasurementApp.LengthUnit.YARD);
        // 5 feet = 1.666 yards
        assertEquals(1.666, result.getValue(), 0.01);
    }

    @Test
    void testAddition_ExplicitTargetUnit_NegativeValues() {
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(-2.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, QuantityMeasurementApp.LengthUnit.INCH);
        // 3 feet = 36 inches
        assertEquals(36.0, result.getValue(), EPSILON);
    }

    // --- UC7 Error Handling ---

    @Test
    void testAddition_NullTargetUnit() {
        QuantityMeasurementApp.QuantityLength q = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertThrows(NullPointerException.class, () -> q.add(q, null));
    }

    @Test
    void testAddition_LargeToSmallScale() {
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1000.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(500.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = q1.add(q2, QuantityMeasurementApp.LengthUnit.INCH);
        // 1500 feet = 18000 inches
        assertEquals(18000.0, result.getValue(), EPSILON);
    }
}
