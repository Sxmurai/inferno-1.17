package me.sxmurai.inferno.impl.events.input;

import org.lwjgl.glfw.GLFW;

public record KeyEvent(Action action, int code, int scancode) {
    public enum Action {
        Release(GLFW.GLFW_RELEASE),
        Pressed(GLFW.GLFW_PRESS),
        Held(GLFW.GLFW_REPEAT);

        private final int action;
        Action(int action) {
            this.action = action;
        }

        public static Action fromAction(int action) {
            for (Action a : Action.class.getEnumConstants()) {
                if (a.action == action) {
                    return a;
                }
            }

            return null;
        }
    }
}
