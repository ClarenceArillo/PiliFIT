package com.example.pilifitproject.utils;

import java.util.HashMap;
import java.util.Map;

public class CategoryMapper {
    private static final Map<String, Integer> CATEGORY_MAP = new HashMap<>();
    private static final Map<Integer, String> REVERSE_CATEGORY_MAP = new HashMap<>();

    private static final Map<String, Integer> COLOR_MAP = new HashMap<>();
    private static final Map<Integer, String> REVERSE_COLOR_MAP = new HashMap<>();

    private static final Map<String, Integer> STYLE_MAP = new HashMap<>();
    private static final Map<Integer, String> REVERSE_STYLE_MAP = new HashMap<>();

    static {
        // Initialize category mappings
        CATEGORY_MAP.put("Top", 1);
        CATEGORY_MAP.put("Bottom", 2);
        CATEGORY_MAP.put("Shoes", 3);

        // Create reverse mappings
        CATEGORY_MAP.forEach((name, id) -> REVERSE_CATEGORY_MAP.put(id, name));

        // Initialize color mappings
        COLOR_MAP.put("Red", 1);
        COLOR_MAP.put("Orange", 2);
        COLOR_MAP.put("Yellow", 3);
        COLOR_MAP.put("Green", 4);
        COLOR_MAP.put("Blue", 5);
        COLOR_MAP.put("Violet", 6);
        COLOR_MAP.put("White", 7);
        COLOR_MAP.put("Black", 8);
        COLOR_MAP.put("Others", 9);

        // Create reverse mappings
        COLOR_MAP.forEach((name, id) -> REVERSE_COLOR_MAP.put(id, name));

        // Initialize style mappings
        STYLE_MAP.put("Formal", 1);
        STYLE_MAP.put("Casual", 2);
        STYLE_MAP.put("Semi-Formal", 3);
        STYLE_MAP.put("Others", 4);

        // Create reverse mappings
        STYLE_MAP.forEach((name, id) -> REVERSE_STYLE_MAP.put(id, name));
    }

    public static int getCategoryId(String name) {
        return CATEGORY_MAP.getOrDefault(name, 1); // Default to Top
    }

    public static String getCategoryName(int id) {
        return REVERSE_CATEGORY_MAP.getOrDefault(id, "Top");
    }

    public static int getColorId(String name) {
        return COLOR_MAP.getOrDefault(name, 9); // Default to Others
    }

    public static String getColorName(int id) {
        return REVERSE_COLOR_MAP.getOrDefault(id, "Others");
    }

    public static int getStyleId(String name) {
        return STYLE_MAP.getOrDefault(name, 4); // Default to Others
    }

    public static String getStyleName(int id) {
        return REVERSE_STYLE_MAP.getOrDefault(id, "Others");
    }
}
