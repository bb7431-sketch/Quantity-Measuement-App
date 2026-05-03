package com.quantitymeasurement;

/**
 * LengthUnit handles conversion logic between different length units.
 * All units are defined relative to the base unit (FEET).
 */
public enum LengthUnit {
    FEET(1.0), 
    INCH(1.0 / 12.0), 
    YARD(3.0), 
    CM(0.393701 / 12.0); // 1 cm = 0.393701 inches, then convert to feet

    public final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * Converts a value in this unit to the base unit (FEET).
     */
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    /**
     * Converts a value from the base unit (FEET) to this unit.
     */
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
