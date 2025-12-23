/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Represents an inline completion suggestion (ghost text).
 */
public final class InlineCompletion {

    private final String insertText;
    private final Integer startLineNumber;
    private final Integer startColumn;
    private final Integer endLineNumber;
    private final Integer endColumn;

    /**
     * Create an inline completion with just the text to insert.
     * The text will be inserted at the current cursor position.
     */
    public InlineCompletion(String insertText) {
        this.insertText = insertText;
        this.startLineNumber = null;
        this.startColumn = null;
        this.endLineNumber = null;
        this.endColumn = null;
    }

    /**
     * Create an inline completion with a specific range to replace.
     */
    public InlineCompletion(String insertText, int startLine, int startCol, int endLine, int endCol) {
        this.insertText = insertText;
        this.startLineNumber = startLine;
        this.startColumn = startCol;
        this.endLineNumber = endLine;
        this.endColumn = endCol;
    }

    public String getInsertText() {
        return insertText;
    }

    public Integer getStartLineNumber() {
        return startLineNumber;
    }

    public Integer getStartColumn() {
        return startColumn;
    }

    public Integer getEndLineNumber() {
        return endLineNumber;
    }

    public Integer getEndColumn() {
        return endColumn;
    }

    public boolean hasRange() {
        return startLineNumber != null;
    }
}
