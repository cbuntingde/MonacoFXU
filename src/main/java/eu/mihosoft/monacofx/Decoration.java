/*
 * MIT License
 * Copyright (c) 2020-2024 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a decoration (visual annotation) in the editor.
 * 
 * <pre>{@code
 * Decoration errorHighlight = Decoration.builder()
 *     .range(10, 1, 10, 25)
 *     .className("error-highlight")
 *     .glyphMarginClassName("error-glyph")
 *     .hoverMessage("Undefined variable 'foo'")
 *     .isWholeLine(false)
 *     .build();
 * }</pre>
 */
public final class Decoration {

    private final int startLineNumber;
    private final int startColumn;
    private final int endLineNumber;
    private final int endColumn;
    private final String className;
    private final String glyphMarginClassName;
    private final String hoverMessage;
    private final boolean isWholeLine;
    private final String inlineClassName;
    private final String beforeContentClassName;
    private final String afterContentClassName;

    private Decoration(Builder builder) {
        this.startLineNumber = builder.startLineNumber;
        this.startColumn = builder.startColumn;
        this.endLineNumber = builder.endLineNumber;
        this.endColumn = builder.endColumn;
        this.className = builder.className;
        this.glyphMarginClassName = builder.glyphMarginClassName;
        this.hoverMessage = builder.hoverMessage;
        this.isWholeLine = builder.isWholeLine;
        this.inlineClassName = builder.inlineClassName;
        this.beforeContentClassName = builder.beforeContentClassName;
        this.afterContentClassName = builder.afterContentClassName;
    }

    Map<String, Object> toMap() {
        Map<String, Object> range = new HashMap<>();
        range.put("startLineNumber", startLineNumber);
        range.put("startColumn", startColumn);
        range.put("endLineNumber", endLineNumber);
        range.put("endColumn", endColumn);

        Map<String, Object> options = new HashMap<>();
        if (className != null) options.put("className", className);
        if (glyphMarginClassName != null) options.put("glyphMarginClassName", glyphMarginClassName);
        if (inlineClassName != null) options.put("inlineClassName", inlineClassName);
        if (beforeContentClassName != null) options.put("beforeContentClassName", beforeContentClassName);
        if (afterContentClassName != null) options.put("afterContentClassName", afterContentClassName);
        options.put("isWholeLine", isWholeLine);

        if (hoverMessage != null) {
            Map<String, Object> hover = new HashMap<>();
            hover.put("value", hoverMessage);
            options.put("hoverMessage", hover);
        }

        Map<String, Object> decoration = new HashMap<>();
        decoration.put("range", range);
        decoration.put("options", options);
        return decoration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int startLineNumber = 1;
        private int startColumn = 1;
        private int endLineNumber = 1;
        private int endColumn = 1;
        private String className;
        private String glyphMarginClassName;
        private String hoverMessage;
        private boolean isWholeLine = false;
        private String inlineClassName;
        private String beforeContentClassName;
        private String afterContentClassName;

        /**
         * Set the range for this decoration.
         */
        public Builder range(int startLine, int startCol, int endLine, int endCol) {
            this.startLineNumber = startLine;
            this.startColumn = startCol;
            this.endLineNumber = endLine;
            this.endColumn = endCol;
            return this;
        }

        /**
         * CSS class for the decoration (applied to the text).
         */
        public Builder className(String className) {
            this.className = className;
            return this;
        }

        /**
         * CSS class for the glyph margin (left gutter icon).
         */
        public Builder glyphMarginClassName(String className) {
            this.glyphMarginClassName = className;
            return this;
        }

        /**
         * CSS class applied inline within the text.
         */
        public Builder inlineClassName(String className) {
            this.inlineClassName = className;
            return this;
        }

        /**
         * Hover message shown when hovering over the decoration.
         */
        public Builder hoverMessage(String message) {
            this.hoverMessage = message;
            return this;
        }

        /**
         * Whether the decoration spans the whole line.
         */
        public Builder isWholeLine(boolean wholeLine) {
            this.isWholeLine = wholeLine;
            return this;
        }

        public Decoration build() {
            return new Decoration(this);
        }
    }
}
