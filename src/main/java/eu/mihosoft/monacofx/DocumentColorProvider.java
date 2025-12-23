/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.List;

/**
 * Interface for providing color information in documents (e.g., CSS color values).
 * When implemented, color values in the editor will show color swatches and 
 * clicking them opens a color picker.
 * 
 * <pre>{@code
 * editor.setDocumentColorProvider("css", new DocumentColorProvider() {
 *     @Override
 *     public List<ColorInformation> provideDocumentColors(String documentText) {
 *         List<ColorInformation> colors = new ArrayList<>();
 *         // Find hex colors like #ff0000
 *         Pattern pattern = Pattern.compile("#([0-9a-fA-F]{6})");
 *         Matcher matcher = pattern.matcher(documentText);
 *         while (matcher.find()) {
 *             int line = countLines(documentText, matcher.start());
 *             colors.add(new ColorInformation(
 *                 parseHexColor(matcher.group(1)),
 *                 line, matcher.start(), line, matcher.end()
 *             ));
 *         }
 *         return colors;
 *     }
 *     
 *     @Override
 *     public List<ColorPresentation> provideColorPresentations(EditorColor color, Range range) {
 *         return List.of(new ColorPresentation(color.toHexString()));
 *     }
 * });
 * }</pre>
 */
public interface DocumentColorProvider {

    /**
     * Find all color values in the document.
     * 
     * @param documentText The full document text
     * @return List of color information with positions
     */
    List<ColorInformation> provideDocumentColors(String documentText);

    /**
     * Provide color presentation options when user picks a color.
     * 
     * @param color The selected color
     * @param range The range of the original color text
     * @return List of ways to represent this color (hex, rgb, hsl, etc.)
     */
    List<ColorPresentation> provideColorPresentations(EditorColor color, Range range);
}
