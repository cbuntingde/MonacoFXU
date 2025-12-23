/*
 * MIT License
 * Copyright (c) 2020-2025 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * Service for managing editor markers (diagnostics like errors, warnings).
 * Markers appear as squiggly underlines and icons in the gutter.
 * 
 * <pre>{@code
 * MarkersService markers = editor.getMarkersService();
 * 
 * // Add error markers
 * markers.setMarkers("myLinter",
 *     Marker.error("Undefined variable 'foo'", 10, 5, 10, 8),
 *     Marker.warning("Unused import", 3, 1, 3, 20)
 * );
 * 
 * // Clear all markers from this owner
 * markers.clearMarkers("myLinter");
 * }</pre>
 */
public final class MarkersService {

    private final Editor editor;
    private static final Gson GSON = new GsonBuilder().create();

    MarkersService(Editor editor) {
        this.editor = editor;
    }

    /**
     * Set markers for the editor.
     * 
     * @param owner A unique identifier for the source of these markers (e.g., "eslint", "typescript")
     * @param markers Markers to set
     */
    public void setMarkers(String owner, Marker... markers) {
        if (editor.getJSWindow() == null) {
            return;
        }

        List<Map<String, Object>> markerList = new ArrayList<>();
        for (Marker m : markers) {
            markerList.add(m.toMap());
        }

        String json = GSON.toJson(markerList);
        editor.getJSWindow().call("setMarkers", owner, json);
    }

    /**
     * Clear all markers for the given owner.
     * 
     * @param owner The owner identifier used when setting markers
     */
    public void clearMarkers(String owner) {
        if (editor.getJSWindow() == null) {
            return;
        }
        editor.getJSWindow().call("clearMarkers", owner);
    }
}
