package me.sxmurai.inferno.impl.managers;

import me.sxmurai.inferno.Inferno;
import me.sxmurai.inferno.impl.events.input.KeyEvent;
import me.sxmurai.inferno.impl.features.module.Module;
import me.sxmurai.inferno.impl.features.module.modules.combat.Criticals;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;

import java.util.ArrayList;

public class ModuleManager implements Listenable {
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // combat
        this.modules.add(new Criticals());

        this.modules.forEach(Module::registerAllSettings);
        Inferno.LOGGER.info("Loaded {} modules!", this.modules.size());
    }

    @EventHandler
    private final Listener<KeyEvent> keyEventListener = new Listener<>((event) -> {
        if (event.action() == KeyEvent.Action.Pressed) {
            this.modules.forEach((module) -> {
                if (module.getBind() == event.code()) {
                    System.out.println(module.getName() + " toggled");
                    module.toggle();
                }
            });
        }
    });

    public ArrayList<Module> getModules() {
        return modules;
    }
}
