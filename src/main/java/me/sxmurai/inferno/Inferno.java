package me.sxmurai.inferno;

import me.sxmurai.inferno.impl.managers.ModuleManager;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Inferno implements ClientModInitializer {
    public static final EventBus EVENT_BUS = new EventManager();
    public static final Logger LOGGER = LogManager.getLogger(Inferno.class);

    public static boolean loaded = false;

    public static ModuleManager moduleManager;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Welcome to Inferno v1.0.0");

        moduleManager = new ModuleManager();

        EVENT_BUS.subscribe(moduleManager);

        loaded = true;
    }
}
