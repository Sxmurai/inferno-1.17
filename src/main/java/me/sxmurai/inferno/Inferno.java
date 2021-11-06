package me.sxmurai.inferno;

import me.sxmurai.inferno.impl.managers.ModuleManager;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class Inferno implements ClientModInitializer {
    public static final IEventBus EVENT_BUS = Inferno.createEventHandler("me.sxmurai.inferno");
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

    private static IEventBus createEventHandler(String packageName) {
        IEventBus bus = new EventBus();
        bus.registerLambdaFactory(packageName, (method, clazz) -> (MethodHandles.Lookup) method.invoke(null, clazz, MethodHandles.lookup()));
        return bus;
    }
}
