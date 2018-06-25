/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
//npc ID 484
package org.openrsc.server.npchandler.Biohazard;
import org.openrsc.server.Config;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.InvItem;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;



public class Omart implements NpcHandler
 {
	public void handleNpc(final Npc npc, final Player owner) throws Exception
	{
		npc.blockedBy(owner);
		owner.setBusy(true);
		
		Quest q = owner.getQuest(Config.Quests.BIOHAZARD);
		Quest plagueCity = owner.getQuest(Config.Quests.PLAGUE_CITY);
		
		if(q != null) 
		{
			if(q.finished()) 
			{
				owner.sendMessage("Omart seems to be too busy to talk");
				owner.setBusy(false);
				npc.unblock();
			}
			else 
			{
				if(owner.getQuest(Config.Quests.BIOHAZARD) != null && owner.getQuest(Config.Quests.BIOHAZARD).getStage() >= 2 && owner.getQuest(Config.Quests.BIOHAZARD).getStage() <= 6)
				{
					switch(q.getStage())
					{
						case 2:
							questStage2(npc, owner);
						break;
						case 3:
							questStage3(npc, owner);
						break;
						case 4:
						case 5:
						case 6:
							questStageRest(npc, owner);
						break;
					}
				}
				else
				{
					owner.sendMessage("Omart seems to be too busy to talk");
					owner.setBusy(false);
					npc.unblock();
				}
			}
		} 
		else
		{
			owner.sendMessage("Omart seems to be too busy to talk");
			owner.setBusy(false);
			npc.unblock();
		}
	}
	
	private void questStage2(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Omart, Jerico said you might be able to help me"}, true) 
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"He informed me of your problem traveller", "I would be glad to help, I have a rope ladder", "and my associate, Kilron, is waiting on the other side"}) 
				{
					public void finished()
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Good stuff"}) 
						{
							public void finished()
							{
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Unfortunately we can't risk it with the watch tower so close", "So first we need to distract the guards in the tower"}) 
								{
									public void finished()
									{
										World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"How?"}) 
										{
											public void finished()
											{
												World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Try asking Jerico, if he's not too busy with his pigeons", "I'll be waiting here for you"}) 
												{
													public void finished()
													{
														owner.setBusy(false);
														npc.unblock();
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	private void questStage3(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well done, the guards are having real trouble with those birds", "You must go now traveller, its your only chance"}, true) 
		{
			public void finished()
			{
				owner.sendMessage("Omart calls to his associate");
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Kilron!"}) 
				{
					public void finished()
					{
						World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
						{
							public void action()
							{
								owner.sendMessage("He throws one end of the rope ladder over the wall");
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Go now Traveller"}) 
								{
									public void finished()
									{
										World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
										{
											public void action()
											{
												final String[] options107 = {"Ok lets do it", "I'll be back soon"};
												owner.setBusy(false);
												owner.sendMenu(options107);
												owner.setMenuHandler(new MenuHandler(options107) 
												{
													public void handleReply(final int option, final String reply)
													{
														owner.setBusy(true);
														for(Player informee : owner.getViewArea().getPlayersInView())
														{
															informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
														}
														switch(option) 
														{
															case 0:
																wallClimb(npc, owner);
															break;
															case 1:
																World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Don't take long", "The mourners will soon be rid of those birds"})
																{
																	public void finished()
																	{
																		owner.setBusy(false);
																		npc.unblock();
																	}
																});
															break;
														}
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	private void questStageRest(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hello traveller", "The guards are still distracted if you wish to cross the wall"}, true)
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
				{
					public void action()
					{
						final String[] options107 = {"Ok lets do it", "I'll be back soon"};
						owner.setBusy(false);
						owner.sendMenu(options107);
						owner.setMenuHandler(new MenuHandler(options107) 
						{
							public void handleReply(final int option, final String reply)
							{
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView())
								{
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) 
								{
									case 0:
										wallClimb(npc, owner);
									break;
									case 1:
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Don't take long", "The mourners will soon be rid of those birds"})
										{
											public void finished()
											{
												owner.setBusy(false);
												npc.unblock();
											}
										});
									break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private void wallClimb(final Npc npc, final Player owner) 
	{
		owner.sendMessage("You climb up the rope ladder");
		World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
		{
			public void action()
			{
				owner.sendMessage("and drop down on the other side");
				owner.teleport(625, 610, false);
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
}