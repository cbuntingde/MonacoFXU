/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Represents a location in the editor or another file.
 * Used for go-to-definition, find references, etc.
 */
public final class Location {

    private final String uri;
    private final Range range;

    /**
     * Create a location in the current document.
     */
    public Location(Range range) {
        this.uri = null;
        this.range = range;
    }

    /**
     * Create a location in another file.
     * 
     * @param uri File URI (e.g., "file:///path/to/file.java")
     * @param range The range within the file
     */
    public Location(String uri, Range range) {
        this.uri = uri;
        this.range = range;
    }

    /**
     * Create a location pointing to a specific line in another file.
     */
    public Location(String uri, int lineNumber) {
        this.uri = uri;
        this.range = new Range(lineNumber, 1, lineNumber, 1);
    }

    /**
     * Get the file URI, or null if this location is in the current document.
     */
    public String getUri() {
        return uri;
    }

    public Range getRange() {
        return range;
    }

    /**
     * Check if this location is in the current document.
     */
    public boolean isCurrentDocument() {
        return uri == null;
    }
}
