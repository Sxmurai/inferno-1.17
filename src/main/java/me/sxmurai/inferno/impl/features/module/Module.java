package me.sxmurai.inferno.impl.features.module;

import me.sxmurai.inferno.Inferno;
import me.sxmurai.inferno.impl.features.Wrapper;
import me.sxmurai.inferno.impl.settings.Bind;
import me.sxmurai.inferno.impl.settings.Setting;
import org.lwjgl.glfw.GLFW;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

public class Module implements Wrapper {
    private final String name;
    private final Category category;
    private String description;

    private final ArrayList<Setting> settings = new ArrayList<>();
    private final Setting<Boolean> drawn = new Setting<>("Drawn", true);
    private final Bind bind = new Bind("Bind", GLFW.GLFW_KEY_UNKNOWN);

    private boolean toggled = false;

    public Module() {
        Define def = this.getClass().getDeclaredAnnotation(Define.class);
        this.name = def.name();
        this.category = def.category();

        if (this.getClass().isAnnotationPresent(Info.class)) {
            Info info = this.getClass().getDeclaredAnnotation(Info.class);

            this.description = info.description();
            this.drawn.setValue(info.drawn());
            this.bind.setValue(info.bind());
        }

        this.settings.add(this.drawn);
        this.settings.add(this.bind);
    }

    public void registerAllSettings() {
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter((field) -> Setting.class.isAssignableFrom(field.getType()))
                .forEach((field) -> {
                    field.setAccessible(true);

                    try {
                        this.settings.add((Setting) field.get(this));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }


    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public int getBind() {
        return this.bind.getValue();
    }

    public void setBind(int bind) {
        this.bind.setValue(bind);
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public void onTick() { }
    public void onRenderWorld() { }
    public void onRenderHud() { }

    protected void onActivated() { }
    protected void onDeactivated() { }

    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            Inferno.EVENT_BUS.subscribe(this);
            this.onActivated();
        } else {
            Inferno.EVENT_BUS.unsubscribe(this);
            this.onDeactivated();
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    public boolean isOn() {
        return this.toggled;
    }

    public boolean isOff() {
        return !this.toggled;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Define {
        String name();
        Category category() default Category.Other;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String description() default "No description provided";
        int bind() default -1;
        boolean drawn() default true;
    }

    public enum Category {
        Client, Combat, Movement, Other, Player, Render
    }
}
