package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.001;

    // --- UC6 Addition Tests ---

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        // Add (1.0 FEET, 2.0 FEET) should return 3.0 FEET
        QuantityMeasurementApp.QuantityLength f1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength f2 = new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = f1.add(f2);
        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_SameUnit_InchPlusInch() {
        QuantityMeasurementApp.QuantityLength i1 = new QuantityMeasurementApp.QuantityLength(6.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength i2 = new QuantityMeasurementApp.QuantityLength(6.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = i1.add(i2);
        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        // 1.0 FEET + 12.0 INCHES should return 2.0 FEET
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = feet.add(inches);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        // 12.0 INCHES + 1.0 FEET should return 24.0 INCHES
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = inches.add(feet);
        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.INCH, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_YardPlusFeet() {
        // 1.0 YARD + 3.0 FEET should return 2.0 YARDS
        QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(3.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = yard.add(feet);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_CentimeterPlusInch() {
        // 2.54 CM + 1.0 INCH should return ~5.08 CM
        QuantityMeasurementApp.QuantityLength cm = new QuantityMeasurementApp.QuantityLength(2.54, QuantityMeasurementApp.LengthUnit.CM);
        QuantityMeasurementApp.QuantityLength inches = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = cm.add(inches);
        // 1.0 inch is approx 2.54 cm. So 2.54 + 2.54 = 5.08
        assertEquals(5.08, result.getValue(), 0.1); 
    }

    // --- UC6 Mathematical Properties ---

    @Test
    void testAddition_Commutativity() {
        // add(A, B, target) == add(B, A, target)
        QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.LengthUnit target = QuantityMeasurementApp.LengthUnit.INCH;

        QuantityLength r1 = QuantityMeasurementApp.QuantityLength.add(q1, q2, target);
        QuantityLength r2 = QuantityMeasurementApp.QuantityLength.add(q2, q1, target);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
        assertEquals(r1.getUnit(), r2.getUnit());
    }

    @Test
    void testAddition_WithZero() {
        // Identity element
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength zeroInches = new QuantityMeasurementApp.QuantityLength(0.0, QuantityMeasurementApp.LengthUnit.INCH);
        QuantityMeasurementApp.QuantityLength result = feet.add(zeroInches);
        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_NegativeValues() {
        QuantityMeasurementApp.QuantityLength f1 = new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength f2 = new QuantityMeasurementApp.QuantityLength(-2.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = f1.add(f2);
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    // --- UC6 Error Handling ---

    @Test
    void testAddition_NullOperand() {
        QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        assertThrows(NullPointerException.class, () -> feet.add(null));
    }

    @Test
    void testAddition_LargeValues() {
        QuantityMeasurementApp.QuantityLength f1 = new QuantityMeasurementApp.QuantityLength(1e6, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength f2 = new QuantityMeasurementApp.QuantityLength(1e6, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength result = f1.add(f2);
        assertEquals(2e6, result.getValue(), EPSILON);
    }
}
