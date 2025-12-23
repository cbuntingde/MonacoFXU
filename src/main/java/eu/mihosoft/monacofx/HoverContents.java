/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Contents to display in a hover tooltip.
 * Supports markdown formatting.
 */
public final class HoverContents {

    private final String value;
    private final boolean isTrusted;

    /**
     * Create hover contents with markdown text.
     */
    public HoverContents(String markdownText) {
        this.value = markdownText;
        this.isTrusted = true;
    }

    /**
     * Create hover contents with markdown text and trust setting.
     * @param markdownText The markdown content
     * @param isTrusted If true, HTML in markdown will be rendered
     */
    public HoverContents(String markdownText, boolean isTrusted) {
        this.value = markdownText;
        this.isTrusted = isTrusted;
    }

    public String getValue() {
        return value;
    }

    public boolean isTrusted() {
        return isTrusted;
    }
}
