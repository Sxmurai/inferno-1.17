package me.sxmurai.inferno.impl.settings;

import java.util.function.Supplier;

public class Setting<T> {
    private final String name;
    private Setting parent;
    private T value;

    private Number min;
    private Number max;

    private final Supplier<Boolean> visibility;

    public Setting(String name, T value) {
        this(null, name, value, null);
    }

    public Setting(String name, T value, Supplier<Boolean> visibility) {
        this(null, name, value, visibility);
    }

    public Setting(String name, T value, Number min, Number max, Supplier<Boolean> supplier) {
        this(null, name, value, min, max, supplier);
    }

    public Setting(String name, T value, Number min, Number max) {
        this(null, name, value, min, max, null);
    }

    public Setting(Setting parent, String name, T value, Supplier<Boolean> visibility) {
        this(parent, name, value, null, null, visibility);
    }

    public Setting(Setting parent, String name, T value, Number min, Number max, Supplier<Boolean> visibility) {
        this.parent = parent;
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public Setting getParent() {
        return parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isVisible() {
        return this.visibility == null || this.visibility.get();
    }

    public static Enum fromName(Class<Enum> clazz, String name) {
        return Enum.valueOf(clazz, name);
    }

    public static String toName(Enum clazz) {
        String name = clazz.name();
        return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }
}