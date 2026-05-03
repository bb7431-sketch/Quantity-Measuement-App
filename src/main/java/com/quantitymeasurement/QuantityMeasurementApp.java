package com.quantitymeasurement;

import java.util.Objects;

/**
 * QuantityMeasurementApp provides a standard interface for length comparison and conversion.
 * It uses a centralized LengthUnit enum to handle conversion factors relative to a base unit (INCH).
 */
public class QuantityMeasurementApp {

    /**
     * LengthUnit enum encapsulates measurement units and their conversion factors.
     */
    public enum LengthUnit {
        YARD(36.0), FEET(12.0), INCH(1.0), CM(0.393701);

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    /**
     * QuantityLength represents a specific length measurement with a value and a unit.
     * Instances are immutable.
     */
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            validateValue(value);
            Objects.requireNonNull(unit, "Unit cannot be null");
            this.value = value;
            this.unit = unit;
        }

        private void validateValue(double value) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be a finite number");
            }
        }

        /**
         * Converts this quantity to a target unit and returns a new QuantityLength instance.
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            Objects.requireNonNull(targetUnit, "Target unit cannot be null");
            double convertedValue = (this.value * this.unit.conversionFactor) / targetUnit.conversionFactor;
            return new QuantityLength(convertedValue, targetUnit);
        }

        /**
         * Static utility method for raw numeric conversion.
         */
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            Objects.requireNonNull(source, "Source unit cannot be null");
            Objects.requireNonNull(target, "Target unit cannot be null");
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be a finite number");
            }
            return (value * source.conversionFactor) / target.conversionFactor;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            
            // Rounding to two decimal places for consistency as per UC5 hints
            double v1 = Math.round(this.value * this.unit.conversionFactor * 100.0) / 100.0;
            double v2 = Math.round(that.value * that.unit.conversionFactor * 100.0) / 100.0;
            
            return Double.compare(v1, v2) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Math.round(value * unit.conversionFactor * 100.0) / 100.0);
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
        
        public double getValue() { return value; }
        public LengthUnit getUnit() { return unit; }
    }

    // --- API Demonstration Methods (Overloading) ---

    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.printf("Input: convert(%.2f, %s, %s) -> Output: %.4f\n", value, from, to, result);
    }

    public static void demonstrateLengthConversion(QuantityLength length, LengthUnit to) {
        QuantityLength result = length.convertTo(to);
        System.out.printf("Input: %s converted to %s -> Output: %s\n", length, to, result);
    }

    public static void demonstrateLengthEquality(QuantityLength l1, QuantityLength l2) {
        boolean result = l1.equals(l2);
        System.out.printf("Comparison: %s and %s -> Equal (%b)\n", l1, l2, result);
    }

    public static void main(String[] args) {
        // Basic Conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CM, LengthUnit.INCH);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCH);

        // Instance Method Conversion
        QuantityLength myLength = new QuantityLength(1.0, LengthUnit.YARD);
        demonstrateLengthConversion(myLength, LengthUnit.INCH);

        // Equality checks
        demonstrateLengthEquality(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(12.0, LengthUnit.INCH));
    }
}
