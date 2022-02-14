package de.blutmondgilde.skyblock.util;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class MessageUtils {
    public static MutableComponent formatTicksToDuration(long ticks) {
        long seconds = Math.round(Math.floor(ticks / 20.0));
        long minutes = 0;
        long hours = 0;
        if (seconds >= 60) {
            minutes = Math.round(Math.floor(seconds / 60.0));
            seconds -= minutes * 60;
        }

        if (minutes >= 60) {
            hours = Math.round(Math.floor(minutes / 60.0));
            minutes -= hours * 60;
        }

        MutableComponent result;

        if (hours > 0) {
            result = new TranslatableComponent("skyblock.text.ticks.in.hours", hours, minutes < 10 ? "0" + minutes : minutes, seconds < 10 ? "0" + seconds : seconds);
        } else {
            if (minutes > 0) {
                result = new TranslatableComponent("skyblock.text.ticks.in.minutes", minutes, seconds < 10 ? "0" + seconds : seconds);
            } else {
                result = new TranslatableComponent("skyblock.text.ticks.in.seconds", seconds);
            }
        }


        return result;
    }

    public static int floatToPercent(float value) {
        return (int) (value * 100F);
    }
}
