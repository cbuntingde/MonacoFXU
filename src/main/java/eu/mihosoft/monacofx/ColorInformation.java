/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Represents a color value with its position in the document.
 */
public final class ColorInformation {

    private final EditorColor color;
    private final Range range;

    public ColorInformation(EditorColor color, int startLine, int startCol, int endLine, int endCol) {
        this.color = color;
        this.range = new Range(startLine, startCol, endLine, endCol);
    }

    public ColorInformation(EditorColor color, Range range) {
        this.color = color;
        this.range = range;
    }

    public EditorColor getColor() {
        return color;
    }

    public Range getRange() {
        return range;
    }
}
