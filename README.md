# MonacoFX

> ⚠️ **Updated December 2024**: This project was originally created 4-5 years ago by [Michael Hoffer](https://github.com/miho). It has been updated by **Chris Bunting** to use Monaco Editor 0.52.0 with modern features. **This update has not yet been fully tested.**

JavaFX editor node based on the powerful [Monaco Editor](https://microsoft.github.io/monaco-editor/) that drives [VS Code](https://github.com/Microsoft/vscode).

<img src="resources/img/screenshot.png">

## Original Author

This project was created by **[Michael Hoffer](https://github.com/miho)** ([@miaborgo](https://twitter.com/miaborgo)).

- Original Repository: [github.com/miho/MonacoFX](https://github.com/miho/MonacoFX)
- Website: [michaelhoffer.de](https://michaelhoffer.de)

## What's New (v0.2.0)

- **Monaco Editor 0.52.0** (upgraded from 0.20.0)
- **JDK 17+ / JavaFX 21** support
- **Maven build** (replaced Gradle)
- **New Features:**
  - Minimap with section headers
  - Bracket pair colorization
  - Sticky scroll
  - Diff Editor (side-by-side & inline)
  - Decorations API (error highlighting)
  - Markers API (diagnostics/linting)
  - Inline Completions (AI ghost text)
  - Custom IntelliSense providers

## Using MonacoFX

### Basic Editor

```java
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create editor
        MonacoFX monacoFX = new MonacoFX();
        StackPane root = new StackPane(monacoFX);

        // Set content and language
        monacoFX.getEditor().getDocument().setText(
            "public class Hello {\n" +
            "    public static void main(String[] args) {\n" +
            "        System.out.println(\"Hello, World!\");\n" +
            "    }\n" +
            "}"
        );
        monacoFX.getEditor().setCurrentLanguage("java");
        monacoFX.getEditor().setCurrentTheme("vs-dark");

        // Configure modern features
        monacoFX.getEditor().setOptions(EditorOptions.builder()
            .minimap(true)
            .minimapShowRegionSectionHeaders(true)
            .stickyScroll(true)
            .bracketPairColorization(true)
            .build()
        );

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MonacoFX Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

### Diff Editor

```java
DiffEditorFX diffEditor = new DiffEditorFX();

diffEditor.getDiffEditor().setOriginal("Hello World", "text");
diffEditor.getDiffEditor().setModified("Hello Monaco World", "text");
diffEditor.getDiffEditor().setInlineView(false); // side-by-side
```

### Error Highlighting (Decorations)

```java
Editor editor = monacoFX.getEditor();

// Add error decoration
String[] ids = editor.getDecorationsService().addDecorations(
    Decoration.builder()
        .range(5, 1, 5, 20)
        .className("error-underline")
        .hoverMessage("Undefined variable")
        .build()
);

// Remove later
editor.getDecorationsService().removeDecorations(ids);
```

### Linting (Markers)

```java
editor.getMarkersService().setMarkers("myLinter",
    Marker.error("Syntax error", 10, 5, 10, 15),
    Marker.warning("Unused import", 3, 1, 3, 25)
);
```

## Building

### Requirements

- JDK 17+
- Maven 3.8+

### Build

```bash
mvn clean package
```

### Run Sample

```bash
mvn javafx:run
```

## License

MIT License - see [LICENSE](LICENSE)

## Acknowledgments

- **Michael Hoffer** - Original MonacoFX creator
- **Microsoft** - Monaco Editor
