package me.sxmurai.inferno.impl.managers;

import me.sxmurai.inferno.Inferno;
import me.sxmurai.inferno.impl.events.input.KeyEvent;
import me.sxmurai.inferno.impl.features.module.Module;
import me.sxmurai.inferno.impl.features.module.modules.combat.Criticals;
import me.sxmurai.inferno.impl.features.module.modules.combat.SelfFill;
import meteordevelopment.orbit.EventHandler;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // @todo dynamically load these classes with reflections?

        // combat
        this.modules.add(new Criticals());
        this.modules.add(new SelfFill());

        this.modules.forEach(Module::registerAllSettings);
        Inferno.LOGGER.info("Loaded {} modules!", this.modules.size());
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (event.action() == KeyEvent.Action.Pressed && event.code() != GLFW.GLFW_KEY_UNKNOWN) {
            this.modules.forEach((module) -> {
                if (module.getBind() == event.code()) {
                    module.toggle();
                }
            });
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
}
