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

        /**
         * Adds another measurement to this one and returns the result in this unit.
         */
        public QuantityLength add(QuantityLength other) {
            Objects.requireNonNull(other, "Operand cannot be null");
            double sumInBase = (this.value * this.unit.conversionFactor) + (other.value * other.unit.conversionFactor);
            double finalValue = sumInBase / this.unit.conversionFactor;
            return new QuantityLength(finalValue, this.unit);
        }

        /**
         * Static method to add two measurements and express the result in a target unit.
         */
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
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

    public static void demonstrateLengthAddition(QuantityLength q1, QuantityLength q2) {
        QuantityLength result = q1.add(q2);
        System.out.printf("Addition: %s + %s -> Result: %s\n", q1, q2, result);
    }

    public static void demonstrateLengthAddition(QuantityLength q1, QuantityLength q2, LengthUnit target) {
        QuantityLength result = QuantityLength.add(q1, q2, target);
        System.out.printf("Addition: %s + %s (Target %s) -> Result: %s\n", q1, q2, target, result);
    }

    public static void main(String[] args) {
        // Same Unit Addition
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(2.0, LengthUnit.FEET));

        // Cross Unit Addition
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(12.0, LengthUnit.INCH));
        demonstrateLengthAddition(new QuantityLength(12.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.FEET));
        
        // Yard and CM Addition
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.YARD), new QuantityLength(3.0, LengthUnit.FEET));
        demonstrateLengthAddition(new QuantityLength(2.54, LengthUnit.CM), new QuantityLength(1.0, LengthUnit.INCH));
    }
}
