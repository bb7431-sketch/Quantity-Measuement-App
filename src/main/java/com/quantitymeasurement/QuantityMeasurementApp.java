package com.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {

    // Step 1: LengthUnit Enum with conversion factors relative to a base unit (INCH)
    public enum LengthUnit {
        FEET(12.0), INCH(1.0);

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    // Step 2: Generic QuantityLength Class
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            
            // Handle null unit case
            if (this.unit == null || that.unit == null) return false;

            // Convert both to base unit (Inches) for comparison
            double value1 = this.value * this.unit.conversionFactor;
            double value2 = that.value * that.unit.conversionFactor;
            
            return Double.compare(value1, value2) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value * unit.conversionFactor);
        }
    }

    // Backward Compatibility Helpers
    public static boolean compareFeet(double v1, double v2) {
        QuantityLength q1 = new QuantityLength(v1, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(v2, LengthUnit.FEET);
        return q1.equals(q2);
    }

    public static boolean compareInches(double v1, double v2) {
        QuantityLength q1 = new QuantityLength(v1, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(v2, LengthUnit.INCH);
        return q1.equals(q2);
    }

    public static void main(String[] args) {
        QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inches12 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: Equal (" + feet1.equals(inches12) + ")");

        System.out.println("Input: Quantity(1.0, INCH) and Quantity(1.0, INCH)");
        System.out.println("Output: Equal (" + compareInches(1.0, 1.0) + ")");
    }
}
