/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx.demo;

import eu.mihosoft.monacofx.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Comprehensive demo application showcasing all MonacoFX features.
 * 
 * Run with: mvn javafx:run
 */
public class DemoApp extends Application {

    private MonacoFX monacoFX;
    private DiffEditorFX diffEditorFX;
    private TabPane editorTabs;
    private TextArea logArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
        // Create toolbar
        ToolBar toolbar = createToolbar();
        root.setTop(toolbar);
        
        // Create main content with tabs
        editorTabs = new TabPane();
        
        // Standard Editor Tab
        Tab editorTab = new Tab("Editor");
        editorTab.setClosable(false);
        monacoFX = new MonacoFX();
        editorTab.setContent(monacoFX);
        
        // Diff Editor Tab
        Tab diffTab = new Tab("Diff Editor");
        diffTab.setClosable(false);
        diffEditorFX = new DiffEditorFX();
        diffTab.setContent(diffEditorFX);
        
        editorTabs.getTabs().addAll(editorTab, diffTab);
        
        // Log area at bottom
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefRowCount(5);
        logArea.setStyle("-fx-font-family: monospace;");
        
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(editorTabs, logArea);
        splitPane.setDividerPositions(0.8);
        
        root.setCenter(splitPane);
        
        // Initialize editor with sample code
        initializeEditor();
        initializeDiffEditor();
        
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("MonacoFX Demo - Monaco Editor 0.52.0");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        log("MonacoFX Demo started. Explore the toolbar buttons to test features!");
    }

    private ToolBar createToolbar() {
        ToolBar toolbar = new ToolBar();
        
        // Theme selector
        ComboBox<String> themeCombo = new ComboBox<>();
        themeCombo.getItems().addAll("vs-dark", "vs", "hc-black");
        themeCombo.setValue("vs-dark");
        themeCombo.setOnAction(e -> {
            monacoFX.getEditor().setCurrentTheme(themeCombo.getValue());
            diffEditorFX.getDiffEditor().setTheme(themeCombo.getValue());
            log("Theme changed to: " + themeCombo.getValue());
        });
        
        // Language selector
        ComboBox<String> langCombo = new ComboBox<>();
        langCombo.getItems().addAll("java", "javascript", "typescript", "python", "html", "css", "json", "markdown", "sql");
        langCombo.setValue("java");
        langCombo.setOnAction(e -> {
            monacoFX.getEditor().setCurrentLanguage(langCombo.getValue());
            log("Language changed to: " + langCombo.getValue());
        });
        
        // Options button
        Button optionsBtn = new Button("Apply Options");
        optionsBtn.setOnAction(e -> applyEditorOptions());
        
        // Decorations button
        Button decorationsBtn = new Button("Add Decorations");
        decorationsBtn.setOnAction(e -> addSampleDecorations());
        
        // Clear decorations
        Button clearDecorationsBtn = new Button("Clear Decorations");
        clearDecorationsBtn.setOnAction(e -> {
            monacoFX.getEditor().getDecorationsService().clearAllDecorations();
            log("Cleared all decorations");
        });
        
        // Markers button
        Button markersBtn = new Button("Add Markers");
        markersBtn.setOnAction(e -> addSampleMarkers());
        
        // Clear markers
        Button clearMarkersBtn = new Button("Clear Markers");
        clearMarkersBtn.setOnAction(e -> {
            monacoFX.getEditor().getMarkersService().clearMarkers("demo");
            log("Cleared all markers");
        });
        
        // Diff mode toggle
        ToggleButton diffInlineBtn = new ToggleButton("Inline Diff");
        diffInlineBtn.setOnAction(e -> {
            diffEditorFX.getDiffEditor().setInlineView(diffInlineBtn.isSelected());
            log("Diff mode: " + (diffInlineBtn.isSelected() ? "inline" : "side-by-side"));
        });
        
        toolbar.getItems().addAll(
            new Label("Theme:"), themeCombo,
            new Separator(),
            new Label("Language:"), langCombo,
            new Separator(),
            optionsBtn,
            new Separator(),
            decorationsBtn, clearDecorationsBtn,
            new Separator(),
            markersBtn, clearMarkersBtn,
            new Separator(),
            diffInlineBtn
        );
        
        return toolbar;
    }

    private void initializeEditor() {
        String sampleCode = """
            package com.example;
            
            import java.util.List;
            import java.util.ArrayList;
            
            /**
             * Sample class demonstrating MonacoFX features.
             * Try the toolbar buttons to test decorations, markers, and options!
             */
            public class HelloWorld {
                
                // #region Constants
                private static final String GREETING = "Hello, Monaco!";
                private static final int MAX_COUNT = 100;
                // #endregion
                
                // #region Main Method
                public static void main(String[] args) {
                    HelloWorld app = new HelloWorld();
                    app.run();
                }
                // #endregion
                
                // #region Instance Methods
                public void run() {
                    List<String> items = new ArrayList<>();
                    items.add(GREETING);
                    
                    for (int i = 0; i < MAX_COUNT; i++) {
                        processItem(items.get(0), i);
                    }
                    
                    // Bracket colorization demo: {[()]}
                    System.out.println(calculate(1, (2 + 3), [4, 5]));
                }
                
                private void processItem(String item, int index) {
                    if (item != null && !item.isEmpty()) {
                        System.out.printf("[%d] %s%n", index, item);
                    }
                }
                
                private int calculate(int a, int b, int[] arr) {
                    return a + b + arr[0];
                }
                // #endregion
            }
            """;
        
        monacoFX.getEditor().getDocument().setText(sampleCode);
        monacoFX.getEditor().setCurrentLanguage("java");
        monacoFX.getEditor().setCurrentTheme("vs-dark");
        
        // Apply modern options
        applyEditorOptions();
        
        // Listen for text changes
        monacoFX.getEditor().getDocument().textProperty().addListener((obs, oldVal, newVal) -> {
            int lines = newVal.split("\n").length;
            log("Document changed: " + lines + " lines");
        });
    }

    private void initializeDiffEditor() {
        String original = """
            public class Example {
                private String name;
                
                public Example(String name) {
                    this.name = name;
                }
                
                public String getName() {
                    return name;
                }
            }
            """;
        
        String modified = """
            public class Example {
                private String name;
                private int age;
                
                public Example(String name, int age) {
                    this.name = name;
                    this.age = age;
                }
                
                public String getName() {
                    return name;
                }
                
                public int getAge() {
                    return age;
                }
            }
            """;
        
        diffEditorFX.getDiffEditor().setOriginal(original, "java");
        diffEditorFX.getDiffEditor().setModified(modified, "java");
        diffEditorFX.getDiffEditor().setTheme("vs-dark");
    }

    private void applyEditorOptions() {
        EditorOptions options = EditorOptions.builder()
            .fontSize(14)
            .minimap(true)
            .minimapShowRegionSectionHeaders(true)
            .minimapSectionHeaderFontSize(10)
            .stickyScroll(true)
            .stickyScrollMaxLineCount(5)
            .bracketPairColorization(true)
            .bracketPairIndependentColorPool(true)
            .smoothScrolling(true)
            .cursorBlinking(EditorOptions.CursorBlinking.SMOOTH)
            .cursorSmoothCaretAnimation(true)
            .roundedSelection(true)
            .renderLineHighlight(EditorOptions.RenderLineHighlight.ALL)
            .build();
        
        monacoFX.getEditor().setOptions(options);
        log("Applied editor options: minimap, sticky scroll, bracket colorization, smooth cursor");
    }

    private void addSampleDecorations() {
        DecorationsService decorations = monacoFX.getEditor().getDecorationsService();
        
        // Add some sample decorations
        String[] ids = decorations.addDecorations(
            // Error-style decoration on line 31 (the syntax error line)
            Decoration.builder()
                .range(31, 1, 31, 60)
                .className("squiggly-error")
                .inlineClassName("error-text")
                .hoverMessage("Syntax error: arrays use {} not []")
                .build(),
            
            // Warning decoration
            Decoration.builder()
                .range(3, 1, 3, 30)
                .className("squiggly-warning")
                .hoverMessage("Warning: unused import")
                .build(),
            
            // Info decoration (line highlight)
            Decoration.builder()
                .range(17, 1, 17, 1)
                .isWholeLine(true)
                .className("line-highlight-info")
                .glyphMarginClassName("glyph-info")
                .build()
        );
        
        log("Added " + ids.length + " decorations. Hover over lines 3, 17, 31 to see messages.");
    }

    private void addSampleMarkers() {
        MarkersService markers = monacoFX.getEditor().getMarkersService();
        
        markers.setMarkers("demo",
            Marker.error("Syntax error: arrays use {} not []", 31, 45, 31, 52),
            Marker.warning("Unused import: java.util.ArrayList", 3, 1, 3, 30),
            Marker.info("Consider using var for local variables", 26, 9, 26, 22),
            Marker.hint("Method could be static", 35, 5, 35, 50)
        );
        
        log("Added 4 markers (error, warning, info, hint). Check the gutter and squiggly lines!");
    }

    private void log(String message) {
        String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
        logArea.appendText("[" + timestamp + "] " + message + "\n");
        logArea.setScrollTop(Double.MAX_VALUE);
    }
}
