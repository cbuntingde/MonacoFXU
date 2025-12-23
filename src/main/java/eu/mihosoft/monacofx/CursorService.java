/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import javafx.beans.property.*;
import netscape.javascript.JSObject;

/**
 * Service for cursor and selection operations.
 * 
 * <pre>{@code
 * CursorService cursor = editor.getCursorService();
 * 
 * // Get current position
 * Position pos = cursor.getPosition();
 * 
 * // Move cursor
 * cursor.setPosition(10, 5);
 * 
 * // Select text
 * cursor.setSelection(1, 1, 5, 20);
 * 
 * // Listen for cursor changes
 * cursor.positionProperty().addListener((obs, oldPos, newPos) -> {
 *     System.out.println("Cursor moved to line " + newPos.getLineNumber());
 * });
 * }</pre>
 */
public final class CursorService {

    private final Editor editor;
    private final ObjectProperty<Position> positionProperty = new SimpleObjectProperty<>(new Position(1, 1));
    private final ObjectProperty<Selection> selectionProperty = new SimpleObjectProperty<>();

    CursorService(Editor editor) {
        this.editor = editor;
    }

    /**
     * Get the current cursor position.
     */
    public Position getPosition() {
        return positionProperty.get();
    }

    /**
     * Set the cursor position.
     */
    public void setPosition(int lineNumber, int column) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("setCursorPosition", lineNumber, column);
        }
        positionProperty.set(new Position(lineNumber, column));
    }

    /**
     * Property for observing cursor position changes.
     */
    public ReadOnlyObjectProperty<Position> positionProperty() {
        return positionProperty;
    }

    /**
     * Get the current selection.
     */
    public Selection getSelection() {
        return selectionProperty.get();
    }

    /**
     * Set the selection range.
     */
    public void setSelection(int startLine, int startColumn, int endLine, int endColumn) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("setSelection", startLine, startColumn, endLine, endColumn);
        }
        selectionProperty.set(new Selection(
            new Position(startLine, startColumn),
            new Position(endLine, endColumn)
        ));
    }

    /**
     * Property for observing selection changes.
     */
    public ReadOnlyObjectProperty<Selection> selectionProperty() {
        return selectionProperty;
    }

    /**
     * Reveal the given line in the center of the editor.
     */
    public void revealLine(int lineNumber) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("revealLine", lineNumber);
        }
    }

    /**
     * Get the word at the current cursor position.
     */
    public String getWordAtPosition() {
        if (editor.getJSWindow() != null) {
            Object result = editor.getJSWindow().call("getWordAtPosition");
            return result != null ? result.toString() : null;
        }
        return null;
    }

    // Called from JavaScript when cursor position changes
    void updatePosition(int lineNumber, int column) {
        positionProperty.set(new Position(lineNumber, column));
    }
}
