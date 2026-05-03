package com.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {

    // Step 1: Updated LengthUnit Enum with YARDS and CENTIMETERS
    public enum LengthUnit {
        YARD(36.0), FEET(12.0), INCH(1.0), CM(0.393701);

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    // Step 2: Generic QuantityLength Class (remains unchanged from UC3)
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
            
            if (this.unit == null || that.unit == null) return false;

            // Convert to base unit (Inches)
            double value1 = this.value * this.unit.conversionFactor;
            double value2 = that.value * that.unit.conversionFactor;
            
            // Use a small delta for floating point comparison if necessary, 
            // but for these factors Double.compare is usually sufficient.
            return Math.abs(value1 - value2) < 0.0001;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value * unit.conversionFactor);
        }
    }

    // Backward Compatibility Helpers
    public static boolean compareFeet(double v1, double v2) {
        return new QuantityLength(v1, LengthUnit.FEET).equals(new QuantityLength(v2, LengthUnit.FEET));
    }

    public static boolean compareInches(double v1, double v2) {
        return new QuantityLength(v1, LengthUnit.INCH).equals(new QuantityLength(v2, LengthUnit.INCH));
    }

    public static void main(String[] args) {
        System.out.println("Input: Quantity(1.0, YARD) and Quantity(3.0, FEET)");
        System.out.println("Output: Equal (" + new QuantityLength(1.0, LengthUnit.YARD).equals(new QuantityLength(3.0, LengthUnit.FEET)) + ")");

        System.out.println("Input: Quantity(1.0, YARD) and Quantity(36.0, INCH)");
        System.out.println("Output: Equal (" + new QuantityLength(1.0, LengthUnit.YARD).equals(new QuantityLength(36.0, LengthUnit.INCH)) + ")");

        System.out.println("Input: Quantity(1.0, CM) and Quantity(0.393701, INCH)");
        System.out.println("Output: Equal (" + new QuantityLength(1.0, LengthUnit.CM).equals(new QuantityLength(0.393701, LengthUnit.INCH)) + ")");
    }
}
