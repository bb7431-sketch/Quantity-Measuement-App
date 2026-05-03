package com.quantitymeasurement;

import java.util.Objects;

/**
 * QuantityMeasurementApp provides a standard interface for length comparison, conversion, and addition.
 * The implementation delegates unit-specific conversion logic to the standalone LengthUnit enum.
 */
public class QuantityMeasurementApp {

    /**
     * QuantityLength represents a specific length measurement with a value and a unit.
     * Instances are immutable and delegate conversion logic to LengthUnit.
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
         * Converts this quantity to a target unit by delegating to LengthUnit.
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            Objects.requireNonNull(targetUnit, "Target unit cannot be null");
            double baseValue = this.unit.convertToBaseUnit(this.value);
            double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
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
            double baseValue = source.convertToBaseUnit(value);
            return target.convertFromBaseUnit(baseValue);
        }

        /**
         * Adds another measurement to this one and returns the result in the specified target unit.
         */
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            return performAddition(this, other, targetUnit);
        }

        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        /**
         * Static method to add two measurements and express the result in a target unit.
         */
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
            return performAddition(q1, q2, targetUnit);
        }

        private static QuantityLength performAddition(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
            Objects.requireNonNull(q1, "First operand cannot be null");
            Objects.requireNonNull(q2, "Second operand cannot be null");
            Objects.requireNonNull(targetUnit, "Target unit cannot be null");
            
            double sumInBase = q1.unit.convertToBaseUnit(q1.value) + q2.unit.convertToBaseUnit(q2.value);
            double finalValue = targetUnit.convertFromBaseUnit(sumInBase);
            return new QuantityLength(finalValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            
            // Equality check based on base unit normalization
            double v1 = Math.round(this.unit.convertToBaseUnit(this.value) * 100.0) / 100.0;
            double v2 = Math.round(that.unit.convertToBaseUnit(that.value) * 100.0) / 100.0;
            
            return Double.compare(v1, v2) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Math.round(unit.convertToBaseUnit(value) * 100.0) / 100.0);
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
        
        public double getValue() { return value; }
        public LengthUnit getUnit() { return unit; }
    }

    // --- API Demonstration Methods ---

    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.printf("Input: convert(%.2f, %s, %s) -> Output: %.4f\n", value, from, to, result);
    }

    public static void main(String[] args) {
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(1.0, LengthUnit.YARD, LengthUnit.FEET);
        
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("Base unit value of 1.0 FEET: " + LengthUnit.FEET.convertToBaseUnit(1.0));
        System.out.println("Base unit value of 12.0 INCHES: " + LengthUnit.INCH.convertToBaseUnit(12.0));
    }
}
