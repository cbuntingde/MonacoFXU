/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.List;

/**
 * Interface for providing go-to-definition functionality.
 * When implemented, Ctrl+Click or F12 on a symbol will navigate to its definition.
 * 
 * <pre>{@code
 * editor.setDefinitionProvider("java", (documentText, position, word) -> {
 *     // Look up the definition location for 'word'
 *     if (symbolTable.contains(word)) {
 *         SymbolInfo info = symbolTable.get(word);
 *         return List.of(new Location(
 *             info.getUri(),
 *             new Range(info.getLine(), 1, info.getLine(), 50)
 *         ));
 *     }
 *     return List.of();
 * });
 * }</pre>
 */
@FunctionalInterface
public interface DefinitionProvider {

    /**
     * Provide definition locations for the symbol at the given position.
     * 
     * @param documentText The full document text
     * @param position The cursor position
     * @param word The word at the cursor position
     * @return List of locations where the symbol is defined
     */
    List<Location> provideDefinition(String documentText, Position position, String word);
}
