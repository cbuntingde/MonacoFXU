/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Represents how a color can be presented/formatted in the document.
 */
public final class ColorPresentation {

    private final String label;
    private final String textEdit;

    /**
     * Create a color presentation where the label is also the text to insert.
     */
    public ColorPresentation(String label) {
        this.label = label;
        this.textEdit = label;
    }

    /**
     * Create a color presentation with a display label and different text to insert.
     */
    public ColorPresentation(String label, String textEdit) {
        this.label = label;
        this.textEdit = textEdit;
    }

    public String getLabel() {
        return label;
    }

    public String getTextEdit() {
        return textEdit;
    }
}
