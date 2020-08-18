package me.potatoes.core;

public class NonExistentEffectException extends Exception {
    public NonExistentEffectException(String effectName) {
        super("Effect " + effectName + " Doesn't exist");
    }
}
