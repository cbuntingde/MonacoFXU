/*
 * MIT License
 * Copyright (c) 2020-2024 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import netscape.javascript.JSObject;

import java.util.*;

/**
 * Service for managing editor decorations.
 * Decorations are visual annotations like error highlights, current line markers, etc.
 * 
 * <pre>{@code
 * DecorationsService decorations = editor.getDecorationsService();
 * 
 * // Add error highlight
 * String[] ids = decorations.addDecorations(
 *     Decoration.builder()
 *         .range(5, 1, 5, 20)
 *         .className("error-underline")
 *         .hoverMessage("Syntax error here")
 *         .build()
 * );
 * 
 * // Later, remove them
 * decorations.removeDecorations(ids);
 * }</pre>
 */
public final class DecorationsService {

    private final Editor editor;
    private static final Gson GSON = new GsonBuilder().create();

    DecorationsService(Editor editor) {
        this.editor = editor;
    }

    /**
     * Add decorations to the editor.
     * 
     * @param decorations Decorations to add
     * @return Array of decoration IDs that can be used to remove them later
     */
    public String[] addDecorations(Decoration... decorations) {
        if (editor.getJSWindow() == null) {
            return new String[0];
        }

        List<Map<String, Object>> decorationList = new ArrayList<>();
        for (Decoration d : decorations) {
            decorationList.add(d.toMap());
        }

        String json = GSON.toJson(decorationList);
        Object result = editor.getJSWindow().call("addDecorations", json);
        
        if (result instanceof String) {
            String[] ids = GSON.fromJson((String) result, String[].class);
            return ids != null ? ids : new String[0];
        }
        return new String[0];
    }

    /**
     * Remove decorations by their IDs.
     * 
     * @param decorationIds IDs returned from addDecorations()
     */
    public void removeDecorations(String... decorationIds) {
        if (editor.getJSWindow() == null) {
            return;
        }
        String json = GSON.toJson(decorationIds);
        editor.getJSWindow().call("removeDecorations", json);
    }

    /**
     * Clear all decorations added through this service.
     */
    public void clearAllDecorations() {
        if (editor.getJSWindow() == null) {
            return;
        }
        editor.getJSWindow().call("clearAllDecorations");
    }
}
