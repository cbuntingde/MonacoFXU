/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

/**
 * Represents a range in the editor (start and end positions).
 */
public final class Range {

    private final int startLineNumber;
    private final int startColumn;
    private final int endLineNumber;
    private final int endColumn;

    public Range(int startLineNumber, int startColumn, int endLineNumber, int endColumn) {
        this.startLineNumber = startLineNumber;
        this.startColumn = startColumn;
        this.endLineNumber = endLineNumber;
        this.endColumn = endColumn;
    }

    public int getStartLineNumber() { return startLineNumber; }
    public int getStartColumn() { return startColumn; }
    public int getEndLineNumber() { return endLineNumber; }
    public int getEndColumn() { return endColumn; }

    /**
     * Check if this range contains the given position.
     */
    public boolean contains(Position position) {
        int line = position.lineNumber;
        int col = position.column;
        
        if (line < startLineNumber || line > endLineNumber) {
            return false;
        }
        if (line == startLineNumber && col < startColumn) {
            return false;
        }
        if (line == endLineNumber && col > endColumn) {
            return false;
        }
        return true;
    }
}
