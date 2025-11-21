package org.GEPurchaseData;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GrandExchangeOfferChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;

import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.api.ChatMessageType;
import javax.inject.Inject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@PluginDescriptor(
	name = "GE Purchase Data"
)
public class MainPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private MainConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("MainPlugin started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("MainPlugin stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "MainPlugin says " + config.greeting(), null);
		}
	}

	@Provides
    MainConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MainConfig.class);
	}

    @Inject
    private ChatMessageManager chatMessageManager;

    @Subscribe
    public void onGrandExchangeOfferChanged(GrandExchangeOfferChanged event)
    {
        GrandExchangeOffer offer = event.getOffer();
        if (offer == null)
        {
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", ">>> OFFER IS NULL !!!", null);
            return;
        }

        int slot = event.getSlot();
        int sold = offer.getQuantitySold();

        //client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "GE Slot " + slot + " updated: quantity sold = " + sold, null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "----------", null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getSlot: " + event.getSlot(), null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getQuantitySold: " + event.getOffer().getQuantitySold(), null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getItemId: " + event.getOffer().getItemId(), null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getPrice: " + event.getOffer().getPrice(), null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getSpent: " + event.getOffer().getSpent(), null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getState: " + event.getOffer().getState(), null);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "getTotalQuantity: " + event.getOffer().getTotalQuantity(), null);

        //Chat Test Outputs
        //event.toString(): GrandExchangeOfferChanged(offer=qg@28998c8d, slot=7)
        //event.getSlot(): 0-7
        //event.getOffer().getState(): BUYING, BOUGHT, CANCELLED_BUY, SELLING, SOLD, CANCELLED_SELL, EMPTY
        //event.getOffer().getItemId(): 314
        //event.getOffer().getPrice(): 2 (Offer or sell price per item.)
        //event.getOffer().getSpent(): 0 (Total amount of money spent so far.)
        //event.getOffer().getQuantitySold(): 0 (Quantity of bought or sold items so far.)
        //event.getOffer().getTotalQuantity(): 100 (Total quantity being bought or sold.)

        //offer.toString(): qg@28998c8d
        //offer.getItemId(): 314
    }



}
