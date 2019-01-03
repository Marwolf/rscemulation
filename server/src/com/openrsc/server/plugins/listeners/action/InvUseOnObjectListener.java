package com.openrsc.server.plugins.listeners.action;

import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.GameObject;
import com.openrsc.server.model.entity.player.Player;

public interface InvUseOnObjectListener {

	/**
	 * Called when a user uses an inventory item on an game object
	 */
	public void onInvUseOnObject(GameObject obj, Item item, Player player);
}
