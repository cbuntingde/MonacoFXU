/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.List;

/**
 * Interface for providing inline completions (ghost text suggestions).
 * This is similar to GitHub Copilot-style suggestions.
 * 
 * <pre>{@code
 * editor.setInlineCompletionProvider((textBeforeCursor, position) -> {
 *     // Call your AI model here
 *     String suggestion = myAI.complete(textBeforeCursor);
 *     if (suggestion != null) {
 *         return List.of(new InlineCompletion(suggestion));
 *     }
 *     return List.of();
 * });
 * }</pre>
 */
@FunctionalInterface
public interface InlineCompletionProvider {

    /**
     * Provide inline completions for the current cursor position.
     * 
     * @param textBeforeCursor All text from the start of the document to the cursor
     * @param position Current cursor position
     * @return List of inline completion suggestions
     */
    List<InlineCompletion> provideInlineCompletions(String textBeforeCursor, Position position);
}
