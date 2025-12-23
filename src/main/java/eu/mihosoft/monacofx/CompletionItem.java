/*
 * MIT License
 * Copyright (c) 2020-2024 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 */
package eu.mihosoft.monacofx;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a completion item for IntelliSense suggestions.
 */
public final class CompletionItem {

    private final String label;
    private final CompletionItemKind kind;
    private final String insertText;
    private final String detail;
    private final String documentation;
    private final boolean isSnippet;
    private final String sortText;
    private final String filterText;

    private CompletionItem(Builder builder) {
        this.label = builder.label;
        this.kind = builder.kind;
        this.insertText = builder.insertText;
        this.detail = builder.detail;
        this.documentation = builder.documentation;
        this.isSnippet = builder.isSnippet;
        this.sortText = builder.sortText;
        this.filterText = builder.filterText;
    }

    public static Builder builder() {
        return new Builder();
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("label", label);
        map.put("kind", kind.getValue());
        map.put("insertText", insertText != null ? insertText : label);
        if (detail != null) map.put("detail", detail);
        if (documentation != null) {
            Map<String, Object> doc = new HashMap<>();
            doc.put("value", documentation);
            map.put("documentation", doc);
        }
        if (isSnippet) {
            map.put("insertTextRules", 4); // InsertAsSnippet
        }
        if (sortText != null) map.put("sortText", sortText);
        if (filterText != null) map.put("filterText", filterText);
        return map;
    }

    public static class Builder {
        private String label;
        private CompletionItemKind kind = CompletionItemKind.TEXT;
        private String insertText;
        private String detail;
        private String documentation;
        private boolean isSnippet = false;
        private String sortText;
        private String filterText;

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder kind(CompletionItemKind kind) {
            this.kind = kind;
            return this;
        }

        public Builder insertText(String text) {
            this.insertText = text;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder documentation(String doc) {
            this.documentation = doc;
            return this;
        }

        public Builder isSnippet(boolean snippet) {
            this.isSnippet = snippet;
            return this;
        }

        public Builder sortText(String sortText) {
            this.sortText = sortText;
            return this;
        }

        public Builder filterText(String filterText) {
            this.filterText = filterText;
            return this;
        }

        public CompletionItem build() {
            if (label == null) throw new IllegalStateException("label is required");
            return new CompletionItem(this);
        }
    }

    /**
     * Kinds of completion items (icons shown in IntelliSense).
     */
    public enum CompletionItemKind {
        METHOD(0),
        FUNCTION(1),
        CONSTRUCTOR(2),
        FIELD(3),
        VARIABLE(4),
        CLASS(5),
        STRUCT(6),
        INTERFACE(7),
        MODULE(8),
        PROPERTY(9),
        EVENT(10),
        OPERATOR(11),
        UNIT(12),
        VALUE(13),
        CONSTANT(14),
        ENUM(15),
        ENUM_MEMBER(16),
        KEYWORD(17),
        TEXT(18),
        COLOR(19),
        FILE(20),
        REFERENCE(21),
        CUSTOMCOLOR(22),
        FOLDER(23),
        TYPE_PARAMETER(24),
        USER(25),
        ISSUE(26),
        SNIPPET(27);

        private final int value;
        CompletionItemKind(int value) { this.value = value; }
        public int getValue() { return value; }
    }
}
