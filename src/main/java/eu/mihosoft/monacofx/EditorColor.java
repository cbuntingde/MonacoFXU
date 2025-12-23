/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Represents a color in the editor (RGBA format, values 0-1).
 */
public final class EditorColor {

    private final double red;
    private final double green;
    private final double blue;
    private final double alpha;

    /**
     * Create a color from RGBA values (0-1 range).
     */
    public EditorColor(double red, double green, double blue, double alpha) {
        this.red = Math.max(0, Math.min(1, red));
        this.green = Math.max(0, Math.min(1, green));
        this.blue = Math.max(0, Math.min(1, blue));
        this.alpha = Math.max(0, Math.min(1, alpha));
    }

    /**
     * Create a color from RGB values (0-1 range), alpha = 1.
     */
    public EditorColor(double red, double green, double blue) {
        this(red, green, blue, 1.0);
    }

    /**
     * Create a color from a hex string (e.g., "#ff0000" or "ff0000").
     */
    public static EditorColor fromHex(String hex) {
        hex = hex.replace("#", "");
        if (hex.length() == 6) {
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            return new EditorColor(r / 255.0, g / 255.0, b / 255.0);
        } else if (hex.length() == 8) {
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            int a = Integer.parseInt(hex.substring(6, 8), 16);
            return new EditorColor(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
        }
        return new EditorColor(0, 0, 0);
    }

    /**
     * Create a color from RGB integer values (0-255 range).
     */
    public static EditorColor fromRGB(int r, int g, int b) {
        return new EditorColor(r / 255.0, g / 255.0, b / 255.0);
    }

    /**
     * Create a color from RGBA integer values (0-255 range).
     */
    public static EditorColor fromRGBA(int r, int g, int b, int a) {
        return new EditorColor(r / 255.0, g / 255.0, b / 255.0, a / 255.0);
    }

    public double getRed() { return red; }
    public double getGreen() { return green; }
    public double getBlue() { return blue; }
    public double getAlpha() { return alpha; }

    /**
     * Convert to hex string (e.g., "#ff0000").
     */
    public String toHexString() {
        return String.format("#%02x%02x%02x", 
            (int)(red * 255), 
            (int)(green * 255), 
            (int)(blue * 255));
    }

    /**
     * Convert to RGB string (e.g., "rgb(255, 0, 0)").
     */
    public String toRgbString() {
        return String.format("rgb(%d, %d, %d)", 
            (int)(red * 255), 
            (int)(green * 255), 
            (int)(blue * 255));
    }

    /**
     * Convert to RGBA string (e.g., "rgba(255, 0, 0, 1)").
     */
    public String toRgbaString() {
        return String.format("rgba(%d, %d, %d, %.2f)", 
            (int)(red * 255), 
            (int)(green * 255), 
            (int)(blue * 255),
            alpha);
    }
}
