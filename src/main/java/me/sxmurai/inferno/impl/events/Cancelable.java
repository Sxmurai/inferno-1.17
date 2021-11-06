package me.sxmurai.inferno.impl.events;

import meteordevelopment.orbit.ICancellable;

public class Cancelable implements ICancellable {
    protected boolean canceled = false;

    @Override
    public void setCancelled(boolean cancelled) {
        this.canceled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
}
