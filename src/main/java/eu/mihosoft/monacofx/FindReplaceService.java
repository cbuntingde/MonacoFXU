/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import javafx.beans.property.*;

/**
 * Service for find and replace operations.
 * 
 * <pre>{@code
 * FindReplaceService findReplace = editor.getFindReplaceService();
 * 
 * // Open find widget
 * findReplace.openFind();
 * 
 * // Find next occurrence
 * findReplace.findNext("searchTerm");
 * 
 * // Replace all
 * findReplace.replaceAll("oldText", "newText");
 * }</pre>
 */
public final class FindReplaceService {

    private final Editor editor;

    FindReplaceService(Editor editor) {
        this.editor = editor;
    }

    /**
     * Open the find widget.
     */
    public void openFind() {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("openFind");
        }
    }

    /**
     * Open the find and replace widget.
     */
    public void openFindReplace() {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("openFindReplace");
        }
    }

    /**
     * Close the find widget.
     */
    public void closeFind() {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("closeFind");
        }
    }

    /**
     * Find next occurrence of the search term.
     */
    public void findNext(String searchTerm) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("findNext", searchTerm);
        }
    }

    /**
     * Find previous occurrence of the search term.
     */
    public void findPrevious(String searchTerm) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("findPrevious", searchTerm);
        }
    }

    /**
     * Replace current selection with replacement text.
     */
    public void replace(String searchTerm, String replacement) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("replace", searchTerm, replacement);
        }
    }

    /**
     * Replace all occurrences.
     */
    public void replaceAll(String searchTerm, String replacement) {
        if (editor.getJSWindow() != null) {
            editor.getJSWindow().call("replaceAll", searchTerm, replacement);
        }
    }

    /**
     * Get the count of matches for a search term.
     */
    public int getMatchCount(String searchTerm) {
        if (editor.getJSWindow() != null) {
            Object result = editor.getJSWindow().call("getMatchCount", searchTerm);
            if (result instanceof Number) {
                return ((Number) result).intValue();
            }
        }
        return 0;
    }
}
