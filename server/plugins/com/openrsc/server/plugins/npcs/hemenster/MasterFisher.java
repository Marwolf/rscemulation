package com.openrsc.server.plugins.npcs.hemenster;

import com.openrsc.server.constants.NpcId;
import com.openrsc.server.constants.Skills;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.listeners.TalkToNpcListener;

import static com.openrsc.server.plugins.Functions.getCurrentLevel;
import static com.openrsc.server.plugins.Functions.npcTalk;

public class MasterFisher implements TalkToNpcListener {

	@Override
	public boolean blockTalkToNpc(Player p, Npc n) {
		return p.getWorld().getServer().getConfig().WANT_MISSING_GUILD_GREETINGS && n.getID() == NpcId.MASTER_FISHER.id();
	}

	@Override
	public void onTalkToNpc(Player p, Npc n) {
		if (p.getWorld().getServer().getConfig().WANT_MISSING_GUILD_GREETINGS && n.getID() == NpcId.MASTER_FISHER.id()) {
			if (getCurrentLevel(p, Skills.FISHING) < 68) {
				npcTalk(p, n, "Hello only the top fishers are allowed in here");
				p.message("You need a fishing level of 68 to enter");
			} else {
				npcTalk(p, n, "Hello, welcome to the fishing guild",
					"Please feel free to make use of any of our facilities");
			}
		}
	}
}
