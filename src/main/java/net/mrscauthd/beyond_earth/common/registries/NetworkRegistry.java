package net.mrscauthd.beyond_earth.common.registries;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.OxygenBubbleDistributorBlockEntity;
import net.mrscauthd.beyond_earth.common.config.data.PlanetData.PlanetDataHandler;
import net.mrscauthd.beyond_earth.common.keybinds.KeyHandler;
import net.mrscauthd.beyond_earth.common.menus.planetselection.PlanetSelectionMenuNetworkHandler;

public class NetworkRegistry {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = net.minecraftforge.network.NetworkRegistry.newSimpleChannel(new ResourceLocation(BeyondEarth.MODID, BeyondEarth.MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID;

    public static void register() {
        addNetworkMessage(OxygenBubbleDistributorBlockEntity.ChangeRangeMessage.class, OxygenBubbleDistributorBlockEntity.ChangeRangeMessage::encode, OxygenBubbleDistributorBlockEntity.ChangeRangeMessage::decode, OxygenBubbleDistributorBlockEntity.ChangeRangeMessage::handle);
        addNetworkMessage(OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage.class, OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage::encode, OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage::decode, OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage::handle);
        addNetworkMessage(PlanetSelectionMenuNetworkHandler.class, PlanetSelectionMenuNetworkHandler::encode, PlanetSelectionMenuNetworkHandler::decode, PlanetSelectionMenuNetworkHandler::handle);
        addNetworkMessage(KeyHandler.class, KeyHandler::encode, KeyHandler::decode, KeyHandler::handle);
        addNetworkMessage(PlanetDataHandler.class, PlanetDataHandler::encode, PlanetDataHandler::decode, PlanetDataHandler::handle);
    }

    private static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
}
