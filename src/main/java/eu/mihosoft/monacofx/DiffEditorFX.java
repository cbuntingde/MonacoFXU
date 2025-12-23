/*
 * MIT License
 * Copyright (c) 2020-2024 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JavaFX component for displaying a Monaco Diff Editor.
 * Shows side-by-side or inline comparison of two texts.
 * 
 * <pre>{@code
 * DiffEditorFX diffEditor = new DiffEditorFX();
 * 
 * diffEditor.getDiffEditor().setOriginal("Hello World", "text");
 * diffEditor.getDiffEditor().setModified("Hello Monaco World", "text");
 * 
 * // Switch to inline mode
 * diffEditor.getDiffEditor().setInlineView(true);
 * }</pre>
 */
public class DiffEditorFX extends Region {

    private final WebView view;
    private final WebEngine engine;
    private final DiffEditor diffEditor;

    private final static String EDITOR_HTML_RESOURCE_LOCATION = "/eu/mihosoft/monacofx/monaco-editor-0.52.0/diff-editor.html";

    public DiffEditorFX() {
        view = new WebView();
        getChildren().add(view);
        engine = view.getEngine();
        
        String url = getClass().getResource(EDITOR_HTML_RESOURCE_LOCATION).toExternalForm();
        engine.load(url);

        diffEditor = new DiffEditor(engine);

        engine.getLoadWorker().stateProperty().addListener((o, old, state) -> {
            if (state == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");

                AtomicBoolean jsDone = new AtomicBoolean(false);
                AtomicInteger attempts = new AtomicInteger();

                Thread thread = new Thread(() -> {
                    while (!jsDone.get()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            Object jsDiffEditorObj = window.call("getDiffEditorView");
                            if (jsDiffEditorObj instanceof JSObject) {
                                diffEditor.setEditor(window, (JSObject) jsDiffEditorObj);
                                jsDone.set(true);
                            }
                        });

                        if (attempts.getAndIncrement() > 10) {
                            throw new RuntimeException(
                                "Cannot initialize diff editor. Max number of attempts reached."
                            );
                        }
                    }
                });
                thread.start();
            }
        });
    }

    @Override
    protected double computePrefWidth(double height) {
        return view.prefWidth(height);
    }

    @Override
    protected double computePrefHeight(double width) {
        return view.prefHeight(width);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        layoutInArea(view, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
    }

    /**
     * Get the diff editor API.
     */
    public DiffEditor getDiffEditor() {
        return diffEditor;
    }

    /**
     * @deprecated Use getDiffEditor() instead
     */
    @Deprecated
    public WebEngine getWebEngine() {
        return engine;
    }
}
