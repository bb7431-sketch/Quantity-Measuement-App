package com.quantitymeasurement;

import java.util.Objects;

/**
 * QuantityMeasurementApp provides a standard interface for length comparison, conversion, and addition.
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
         * Adds another measurement to this one and returns the result in this instance's unit.
         * (UC6 Implementation)
         */
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        /**
         * Adds another measurement to this one and returns the result in the specified target unit.
         * (UC7 Implementation)
         */
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            return performAddition(this, other, targetUnit);
        }

        /**
         * Static method to add two measurements and express the result in a target unit.
         */
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
            return performAddition(q1, q2, targetUnit);
        }

        /**
         * Private utility method to centralize addition logic and avoid code duplication.
         */
        private static QuantityLength performAddition(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
            Objects.requireNonNull(q1, "First operand cannot be null");
            Objects.requireNonNull(q2, "Second operand cannot be null");
            Objects.requireNonNull(targetUnit, "Target unit cannot be null");
            
            double sumInBase = (q1.value * q1.unit.conversionFactor) + (q2.value * q2.unit.conversionFactor);
            double finalValue = sumInBase / targetUnit.conversionFactor;
            return new QuantityLength(finalValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            
            // Consistent rounding to two decimal places for equality checks
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

    // --- API Demonstration Methods ---

    public static void demonstrateLengthAddition(QuantityLength q1, QuantityLength q2, LengthUnit target) {
        QuantityLength result = q1.add(q2, target);
        System.out.printf("Addition: %s + %s -> Result: %s\n", q1, q2, result);
    }

    public static void main(String[] args) {
        // UC7 Demonstration
        QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inches12 = new QuantityLength(12.0, LengthUnit.INCH);

        demonstrateLengthAddition(feet1, inches12, LengthUnit.FEET);   // Result in FEET
        demonstrateLengthAddition(feet1, inches12, LengthUnit.INCH);   // Result in INCH
        demonstrateLengthAddition(feet1, inches12, LengthUnit.YARD);   // Result in YARD
        
        // CM Example
        demonstrateLengthAddition(new QuantityLength(2.54, LengthUnit.CM), new QuantityLength(1.0, LengthUnit.INCH), LengthUnit.CM);
    }
}
