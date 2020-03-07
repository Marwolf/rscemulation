package com.openrsc.server.plugins.npcs.shilo;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.constants.NpcId;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.listeners.InvUseOnNpcListener;
import com.openrsc.server.plugins.listeners.TalkToNpcListener;

import java.util.Optional;

import static com.openrsc.server.plugins.Functions.*;

public class Yanni implements TalkToNpcListener, InvUseOnNpcListener {

	@Override
	public boolean blockTalkToNpc(Player p, Npc n) {
		return n.getID() == NpcId.YANNI.id();
	}

	@Override
	public void onTalkToNpc(Player p, Npc n) {
		boolean hasItemsInterest = false;
		if (n.getID() == NpcId.YANNI.id()) {
			playerTalk(p, n, "Hello there!");
			npcTalk(p, n, "Greetings Bwana!",
					"My name is Yanni and I buy and sell antiques ",
					"and other interesting items.",
					"If you have any interesting items that you might",
					"want to sell me, please let me see them and I'll",
					"offer you a fair price.",
					"Would you like me to have a look at your items",
					"and give you a quote?");
			int menu = showMenu(p, n,
					"Yes please!",
					"Maybe some other time?");
			if (menu == 0) {
				npcTalk(p, n, "Great Bwana!");
				if (p.getCarriedItems().hasCatalogID(ItemId.BONE_KEY.id(), Optional.of(false))) {
					npcTalk(p, n, "I'll give you 100 Gold for the Bone Key.");
					hasItemsInterest |= true;
				}
				if (p.getCarriedItems().hasCatalogID(ItemId.STONE_PLAQUE.id(), Optional.of(false))) {
					npcTalk(p, n, "I'll give you 100 Gold for the Stone-Plaque.");
					hasItemsInterest |= true;
				}
				if (p.getCarriedItems().hasCatalogID(ItemId.TATTERED_SCROLL.id(), Optional.of(false))) {
					npcTalk(p, n, "I'll give you 100 Gold for your tattered scroll");
					hasItemsInterest |= true;
				}
				if (p.getCarriedItems().hasCatalogID(ItemId.CRUMPLED_SCROLL.id(), Optional.of(false))) {
					npcTalk(p, n, "I'll give you 100 Gold for your crumpled scroll");
					hasItemsInterest |= true;
				}
				if (p.getCarriedItems().hasCatalogID(ItemId.BERVIRIUS_TOMB_NOTES.id(), Optional.of(false))) {
					npcTalk(p, n, "I'll give you 100 Gold for your Bervirius Tomb Notes.");
					hasItemsInterest |= true;
				}
				if (p.getCarriedItems().hasCatalogID(ItemId.LOCATING_CRYSTAL.id(), Optional.of(false))) {
					npcTalk(p, n, "WOW! I'll give you 500 Gold for your Locating Crystal!");
					hasItemsInterest |= true;
				}
				if (p.getCarriedItems().hasCatalogID(ItemId.BEADS_OF_THE_DEAD.id(), Optional.of(false))) {
					npcTalk(p, n, "Great I'll give you 1000 Gold for your Beads of the Dead.");
					hasItemsInterest |= true;
				}
				if (hasItemsInterest) {
					npcTalk(p, n, "Those are the items I am interested in Bwana.",
							"If you want to sell me those items, simply show them to me.");
				} else {
					npcTalk(p, n, "Sorry Bwana, you have nothing I am interested in.");
				}

			} else if (menu == 1) {
				npcTalk(p, n, "Sure thing.",
						"Have a nice day Bwana.");
			}
		}
	}

	@Override
	public boolean blockInvUseOnNpc(Player p, Npc npc, Item item) {
		return npc.getID() == NpcId.YANNI.id();
	}

	@Override
	public void onInvUseOnNpc(Player p, Npc npc, Item item) {
		if (npc.getID() == NpcId.YANNI.id()) {
			switch (ItemId.getById(item.getCatalogId())) {
			case BONE_KEY:
				npcTalk(p, npc, "Great item, here's 100 Gold for it.");
				removeItem(p, ItemId.BONE_KEY.id(), 1);
				addItem(p, ItemId.COINS.id(), 100);
				p.message("You sell the Bone Key.");
				break;
			case STONE_PLAQUE:
				npcTalk(p, npc, "Great item, here's 100 Gold for it.");
				removeItem(p, ItemId.STONE_PLAQUE.id(), 1);
				addItem(p, ItemId.COINS.id(), 100);
				p.message("You sell the Stone Plaque.");
				break;
			case TATTERED_SCROLL:
				npcTalk(p, npc, "Great item, here's 100 Gold for it.");
				removeItem(p, ItemId.TATTERED_SCROLL.id(), 1);
				addItem(p, ItemId.COINS.id(), 100);
				p.message("You sell the Tattered Scroll.");
				break;
			case CRUMPLED_SCROLL:
				npcTalk(p, npc, "Great item, here's 100 Gold for it.");
				removeItem(p, ItemId.CRUMPLED_SCROLL.id(), 1);
				addItem(p, ItemId.COINS.id(), 100);
				p.message("You sell the crumpled Scroll.");
				break;
			case BERVIRIUS_TOMB_NOTES:
				npcTalk(p, npc, "Great item, here's 100 Gold for it.");
				removeItem(p, ItemId.BERVIRIUS_TOMB_NOTES.id(), 1);
				addItem(p, ItemId.COINS.id(), 100);
				p.message("You sell the Bervirius Tomb Notes.");
				break;
			case LOCATING_CRYSTAL:
				npcTalk(p, npc, "Great item, here's 500 Gold for it.");
				removeItem(p, ItemId.LOCATING_CRYSTAL.id(), 1);
				addItem(p, ItemId.COINS.id(), 500);
				p.message("You sell the Locating Crystal.");
				break;
			case BEADS_OF_THE_DEAD:
				npcTalk(p, npc, "Great item, here's 1000 Gold for it.");
				removeItem(p, ItemId.BEADS_OF_THE_DEAD.id(), 1);
				addItem(p, ItemId.COINS.id(), 1000);
				p.message("You sell Beads of the Dead.");
				break;
			default:
				p.message("Nothing interesting happens");
				break;
			}
		}
	}
}
