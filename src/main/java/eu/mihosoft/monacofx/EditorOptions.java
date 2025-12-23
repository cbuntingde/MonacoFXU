/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for Monaco Editor options.
 * Use this class to configure modern editor features like minimap, sticky scroll,
 * bracket colorization, and more.
 * 
 * <pre>{@code
 * EditorOptions options = EditorOptions.builder()
 *     .fontSize(14)
 *     .theme("vs-dark")
 *     .minimap(true)
 *     .minimapShowRegionSectionHeaders(true)
 *     .stickyScroll(true)
 *     .bracketPairColorization(true)
 *     .build();
 * editor.setOptions(options);
 * }</pre>
 */
public final class EditorOptions {

    private final Map<String, Object> options;
    private static final Gson GSON = new GsonBuilder().create();

    private EditorOptions(Map<String, Object> options) {
        this.options = new HashMap<>(options);
    }

    /**
     * Convert options to JSON for JavaScript execution.
     */
    public String toJson() {
        return GSON.toJson(options);
    }

    /**
     * Create a new builder for EditorOptions.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EditorOptions.
     */
    public static class Builder {
        private final Map<String, Object> options = new HashMap<>();

        private Builder() {
            // Set sensible defaults
            options.put("automaticLayout", true);
        }

        // ========== Basic Options ==========

        public Builder fontSize(int size) {
            options.put("fontSize", size);
            return this;
        }

        public Builder fontFamily(String family) {
            options.put("fontFamily", family);
            return this;
        }

        public Builder tabSize(int size) {
            options.put("tabSize", size);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            options.put("readOnly", readOnly);
            return this;
        }

        public Builder wordWrap(WordWrap wrap) {
            options.put("wordWrap", wrap.getValue());
            return this;
        }

        public Builder theme(String theme) {
            options.put("theme", theme);
            return this;
        }

        // ========== Line Numbers ==========

        public Builder lineNumbers(LineNumbers mode) {
            options.put("lineNumbers", mode.getValue());
            return this;
        }

        public Builder lineNumbersMinChars(int minChars) {
            options.put("lineNumbersMinChars", minChars);
            return this;
        }

        // ========== Minimap ==========

        public Builder minimap(boolean enabled) {
            getMinimapOptions().put("enabled", enabled);
            return this;
        }

        public Builder minimapSide(MinimapSide side) {
            getMinimapOptions().put("side", side.getValue());
            return this;
        }

        public Builder minimapScale(int scale) {
            getMinimapOptions().put("scale", scale);
            return this;
        }

        public Builder minimapShowRegionSectionHeaders(boolean show) {
            getMinimapOptions().put("showRegionSectionHeaders", show);
            return this;
        }

        public Builder minimapSectionHeaderFontSize(int size) {
            getMinimapOptions().put("sectionHeaderFontSize", size);
            return this;
        }

        @SuppressWarnings("unchecked")
        private Map<String, Object> getMinimapOptions() {
            return (Map<String, Object>) options.computeIfAbsent("minimap", k -> new HashMap<>());
        }

        // ========== Sticky Scroll ==========

        public Builder stickyScroll(boolean enabled) {
            getStickyScrollOptions().put("enabled", enabled);
            return this;
        }

        public Builder stickyScrollMaxLineCount(int maxLines) {
            getStickyScrollOptions().put("maxLineCount", maxLines);
            return this;
        }

        @SuppressWarnings("unchecked")
        private Map<String, Object> getStickyScrollOptions() {
            return (Map<String, Object>) options.computeIfAbsent("stickyScroll", k -> new HashMap<>());
        }

        // ========== Bracket Pair Colorization ==========

        public Builder bracketPairColorization(boolean enabled) {
            getBracketOptions().put("enabled", enabled);
            return this;
        }

        public Builder bracketPairIndependentColorPool(boolean independent) {
            getBracketOptions().put("independentColorPoolPerBracketType", independent);
            return this;
        }

        @SuppressWarnings("unchecked")
        private Map<String, Object> getBracketOptions() {
            return (Map<String, Object>) options.computeIfAbsent("bracketPairColorization", k -> new HashMap<>());
        }

        // ========== Scrolling ==========

        public Builder smoothScrolling(boolean smooth) {
            options.put("smoothScrolling", smooth);
            return this;
        }

        public Builder scrollBeyondLastLine(boolean scroll) {
            options.put("scrollBeyondLastLine", scroll);
            return this;
        }

        // ========== Cursor ==========

        public Builder cursorBlinking(CursorBlinking style) {
            options.put("cursorBlinking", style.getValue());
            return this;
        }

        public Builder cursorSmoothCaretAnimation(boolean enabled) {
            options.put("cursorSmoothCaretAnimation", enabled ? "on" : "off");
            return this;
        }

        public Builder cursorWidth(int width) {
            options.put("cursorWidth", width);
            return this;
        }

        // ========== Rendering ==========

        public Builder renderWhitespace(RenderWhitespace mode) {
            options.put("renderWhitespace", mode.getValue());
            return this;
        }

        public Builder renderLineHighlight(RenderLineHighlight mode) {
            options.put("renderLineHighlight", mode.getValue());
            return this;
        }

        public Builder roundedSelection(boolean rounded) {
            options.put("roundedSelection", rounded);
            return this;
        }

        // ========== Build ==========

        public EditorOptions build() {
            return new EditorOptions(options);
        }
    }

    // ========== Enums ==========

    public enum WordWrap {
        OFF("off"),
        ON("on"),
        WORD_WRAP_COLUMN("wordWrapColumn"),
        BOUNDED("bounded");

        private final String value;
        WordWrap(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public enum LineNumbers {
        ON("on"),
        OFF("off"),
        RELATIVE("relative"),
        INTERVAL("interval");

        private final String value;
        LineNumbers(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public enum MinimapSide {
        LEFT("left"),
        RIGHT("right");

        private final String value;
        MinimapSide(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public enum CursorBlinking {
        BLINK("blink"),
        SMOOTH("smooth"),
        PHASE("phase"),
        EXPAND("expand"),
        SOLID("solid");

        private final String value;
        CursorBlinking(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public enum RenderWhitespace {
        NONE("none"),
        BOUNDARY("boundary"),
        SELECTION("selection"),
        TRAILING("trailing"),
        ALL("all");

        private final String value;
        RenderWhitespace(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public enum RenderLineHighlight {
        NONE("none"),
        GUTTER("gutter"),
        LINE("line"),
        ALL("all");

        private final String value;
        RenderLineHighlight(String value) { this.value = value; }
        public String getValue() { return value; }
    }
}
