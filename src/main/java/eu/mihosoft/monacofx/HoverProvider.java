/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.List;

/**
 * Interface for providing hover information (tooltips/documentation).
 * 
 * <pre>{@code
 * editor.setHoverProvider("java", (text, position, word) -> {
 *     if (word.equals("System")) {
 *         return new HoverContents("**java.lang.System**\n\nThe System class contains several useful class fields and methods.");
 *     }
 *     return null;
 * });
 * }</pre>
 */
@FunctionalInterface
public interface HoverProvider {

    /**
     * Provide hover contents for the given position.
     * 
     * @param documentText Full document text
     * @param position Current hover position
     * @param word The word at the hover position
     * @return Hover contents or null if no hover info available
     */
    HoverContents provideHover(String documentText, Position position, String word);
}
