package me.overruling.client.module.modules.render;

import me.overruling.client.event.custom.CustomEvent;
import me.overruling.client.event.custom.networkmanager.NetworkPacketEvent;
import me.overruling.client.event.custom.player.PlayerUpdateEvent;
import me.overruling.client.module.Module;
import me.overruling.client.module.ModuleType;
import me.overruling.exeterimports.mcapi.settings.ModeSetting;
import me.overruling.exeterimports.mcapi.settings.ToggleableSetting;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

public class FullbrightModule extends Module {
    public static final ModeSetting mode = new ModeSetting("Mode", null, Mode.GAMMA, Mode.values());
    public static final ToggleableSetting effects = new ToggleableSetting("Effects", null, false);

    private float previousSetting;

    public FullbrightModule() {
        super("Fullbright", "Name says it all.", ModuleType.Render, 0);
        addOption(mode);
        addOption(effects);
        endOption();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.previousSetting = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if(mode.getValue() == Mode.POTION) /* POTION */
            mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        mc.gameSettings.gammaSetting = previousSetting;
    }

    @EventHandler
    private Listener<PlayerUpdateEvent> updateEventListener = new Listener<>(event -> {
        if(mode.getValue() == Mode.GAMMA) /* GAMMA */
            mc.gameSettings.gammaSetting = 1000.0f;
        if(mode.getValue() == Mode.POTION) /* POTION */
            mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
    });

    @EventHandler
    private Listener<NetworkPacketEvent> packetEventListener = new Listener<>(event -> {
        if (event.getEra() == CustomEvent.Era.PRE && event.getPacket() instanceof SPacketEntityEffect && effects.isEnabled()) {
            final SPacketEntityEffect packet = (SPacketEntityEffect) event.getPacket();
            if (mc.player != null && packet.getEntityId() == mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15))
                event.cancel();
        }
    });

    @Override
    public String getMeta() { return mode.getValue().name(); }

    public enum Mode { GAMMA, POTION }
}