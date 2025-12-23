/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 * API for controlling the Monaco Diff Editor.
 */
public final class DiffEditor {

    private final WebEngine engine;
    private JSObject window;
    private JSObject editor;

    private final StringProperty originalTextProperty = new SimpleStringProperty("");
    private final StringProperty modifiedTextProperty = new SimpleStringProperty("");
    private final StringProperty languageProperty = new SimpleStringProperty("text");
    private final BooleanProperty inlineViewProperty = new SimpleBooleanProperty(false);

    DiffEditor(WebEngine engine) {
        this.engine = engine;
    }

    void setEditor(JSObject window, JSObject editor) {
        this.window = window;
        this.editor = editor;

        // Apply initial values
        if (originalTextProperty.get() != null && modifiedTextProperty.get() != null) {
            updateModel();
        }

        // Listen for property changes
        originalTextProperty.addListener((obs, oldVal, newVal) -> updateModel());
        modifiedTextProperty.addListener((obs, oldVal, newVal) -> updateModel());
        languageProperty.addListener((obs, oldVal, newVal) -> updateModel());
        inlineViewProperty.addListener((obs, oldVal, newVal) -> {
            if (window != null) {
                window.call("setDiffEditorInlineMode", newVal);
            }
        });
    }

    private void updateModel() {
        if (window != null) {
            window.call("setDiffModel", 
                originalTextProperty.get(), 
                modifiedTextProperty.get(), 
                languageProperty.get()
            );
        }
    }

    // ========== Properties ==========

    public StringProperty originalTextProperty() {
        return originalTextProperty;
    }

    public void setOriginal(String text, String language) {
        originalTextProperty.set(text);
        languageProperty.set(language);
    }

    public String getOriginalText() {
        return originalTextProperty.get();
    }

    public StringProperty modifiedTextProperty() {
        return modifiedTextProperty;
    }

    public void setModified(String text, String language) {
        modifiedTextProperty.set(text);
        languageProperty.set(language);
    }

    public String getModifiedText() {
        return modifiedTextProperty.get();
    }

    public StringProperty languageProperty() {
        return languageProperty;
    }

    public void setLanguage(String language) {
        languageProperty.set(language);
    }

    public String getLanguage() {
        return languageProperty.get();
    }

    public BooleanProperty inlineViewProperty() {
        return inlineViewProperty;
    }

    /**
     * Set to true for inline diff view, false for side-by-side.
     */
    public void setInlineView(boolean inline) {
        inlineViewProperty.set(inline);
    }

    public boolean isInlineView() {
        return inlineViewProperty.get();
    }

    /**
     * Set the theme.
     */
    public void setTheme(String theme) {
        if (window != null) {
            engine.executeScript("monaco.editor.setTheme('" + theme + "')");
        }
    }
}
