package net.helium.injector.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.helium.events.ReceivePacketEvent;
import net.helium.events.SendPacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    private void receivePacket(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callback) {
        if (MinecraftForge.EVENT_BUS.post(new ReceivePacketEvent(packet))) callback.cancel();
    }
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(final Packet<?> packet, final CallbackInfo callback) {
        if (MinecraftForge.EVENT_BUS.post(new SendPacketEvent(packet))) callback.cancel();
    }
}
