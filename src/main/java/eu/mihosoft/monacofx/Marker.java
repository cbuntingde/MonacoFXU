/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a diagnostic marker (error, warning, info, hint).
 * Markers appear as squiggly underlines in the editor with icons in the gutter.
 * 
 * <pre>{@code
 * Marker error = Marker.error("Syntax error: unexpected token", 5, 10, 5, 15);
 * Marker warning = Marker.warning("Variable is never used", 10, 1, 10, 8);
 * Marker info = Marker.info("Consider using const", 15, 1, 15, 10);
 * }</pre>
 */
public final class Marker {

    private final MarkerSeverity severity;
    private final String message;
    private final int startLineNumber;
    private final int startColumn;
    private final int endLineNumber;
    private final int endColumn;
    private final String source;
    private final String code;

    private Marker(MarkerSeverity severity, String message, 
                   int startLine, int startCol, int endLine, int endCol,
                   String source, String code) {
        this.severity = severity;
        this.message = message;
        this.startLineNumber = startLine;
        this.startColumn = startCol;
        this.endLineNumber = endLine;
        this.endColumn = endCol;
        this.source = source;
        this.code = code;
    }

    // ========== Factory Methods ==========

    public static Marker error(String message, int startLine, int startCol, int endLine, int endCol) {
        return new Marker(MarkerSeverity.ERROR, message, startLine, startCol, endLine, endCol, null, null);
    }

    public static Marker warning(String message, int startLine, int startCol, int endLine, int endCol) {
        return new Marker(MarkerSeverity.WARNING, message, startLine, startCol, endLine, endCol, null, null);
    }

    public static Marker info(String message, int startLine, int startCol, int endLine, int endCol) {
        return new Marker(MarkerSeverity.INFO, message, startLine, startCol, endLine, endCol, null, null);
    }

    public static Marker hint(String message, int startLine, int startCol, int endLine, int endCol) {
        return new Marker(MarkerSeverity.HINT, message, startLine, startCol, endLine, endCol, null, null);
    }

    public static Builder builder() {
        return new Builder();
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("severity", severity.getValue());
        map.put("message", message);
        map.put("startLineNumber", startLineNumber);
        map.put("startColumn", startColumn);
        map.put("endLineNumber", endLineNumber);
        map.put("endColumn", endColumn);
        if (source != null) map.put("source", source);
        if (code != null) map.put("code", code);
        return map;
    }

    public static class Builder {
        private MarkerSeverity severity = MarkerSeverity.ERROR;
        private String message = "";
        private int startLineNumber = 1;
        private int startColumn = 1;
        private int endLineNumber = 1;
        private int endColumn = 1;
        private String source;
        private String code;

        public Builder severity(MarkerSeverity severity) {
            this.severity = severity;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder range(int startLine, int startCol, int endLine, int endCol) {
            this.startLineNumber = startLine;
            this.startColumn = startCol;
            this.endLineNumber = endLine;
            this.endColumn = endCol;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Marker build() {
            return new Marker(severity, message, startLineNumber, startColumn, 
                              endLineNumber, endColumn, source, code);
        }
    }

    /**
     * Marker severity levels.
     */
    public enum MarkerSeverity {
        HINT(1),
        INFO(2),
        WARNING(4),
        ERROR(8);

        private final int value;
        MarkerSeverity(int value) { this.value = value; }
        public int getValue() { return value; }
    }
}
