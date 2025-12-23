/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.List;

/**
 * Interface for providing custom code completions (IntelliSense).
 * 
 * <pre>{@code
 * editor.setCompletionProvider("java", (text, position, trigger) -> {
 *     List<CompletionItem> items = new ArrayList<>();
 *     items.add(CompletionItem.builder()
 *         .label("System.out.println")
 *         .kind(CompletionItemKind.METHOD)
 *         .insertText("System.out.println($1);")
 *         .detail("Print to console")
 *         .build());
 *     return items;
 * });
 * }</pre>
 */
@FunctionalInterface
public interface CompletionItemProvider {

    /**
     * Provide completion items for the current position.
     * 
     * @param textUntilPosition All text from the start to the cursor position
     * @param position Current cursor position
     * @param triggerCharacter The character that triggered completion (e.g., ".")
     * @return List of completion suggestions
     */
    List<CompletionItem> provideCompletionItems(String textUntilPosition, Position position, String triggerCharacter);
}
