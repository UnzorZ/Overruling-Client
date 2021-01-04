package me.overruling.client.mixin.client;

import io.netty.channel.ChannelHandlerContext;
import me.overruling.client.Overruling;
import me.overruling.client.event.custom.networkmanager.NetworkPacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        NetworkPacketEvent networkPacketEvent = new NetworkPacketEvent(packet);
        Overruling.EVENT_BUS.post(networkPacketEvent);
        if(networkPacketEvent.isCancelled()) callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext handlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
        NetworkPacketEvent networkPacketEvent = new NetworkPacketEvent(packet);
        Overruling.EVENT_BUS.post(networkPacketEvent);
        if(networkPacketEvent.isCancelled()) callbackInfo.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"))
    private void onPostSendPacket(Packet<?> packet, CallbackInfo callbackInfo) { Overruling.EVENT_BUS.post(new NetworkPacketEvent(packet));}

    @Inject(method = "channelRead0", at = @At("RETURN"))
    private void onPostChannelRead(ChannelHandlerContext handlerContext, Packet<?> packet, CallbackInfo callbackInfo) { Overruling.EVENT_BUS.post(new NetworkPacketEvent(packet));}
}