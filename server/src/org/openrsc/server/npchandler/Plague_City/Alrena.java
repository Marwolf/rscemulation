/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
//npc ID 450
package org.openrsc.server.npchandler.Plague_City;
import org.openrsc.server.Config;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;



public class Alrena implements NpcHandler {

	public void handleNpc(final Npc npc, final Player owner) throws Exception 
	{
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Config.Quests.PLAGUE_CITY);
		if(q != null) 
		{
			if(q.finished())
			{
				finished(npc, owner);
			} 
			else 
			{
				switch(q.getStage())
				{
					case 0:
						noQuestStarted(npc, owner);
						break;
					case 1:
						questStarted(npc, owner);
						break;
					case 2:
						questStage2(npc, owner);
						break;
					case 3:
					case 4:
						questStage4(npc, owner);
						break;
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
						questStageRest(npc, owner);
					break;
					
					default:
						noQuestStarted(npc, owner);
				}
			}
		} 
		else 
		{
			noQuestStarted(npc, owner);
		}
	}
	

	private void noQuestStarted(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hi, can I help you?"}, true)
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500)
				{
					public void action()
					{
						final String[] options107 = {"I'm looking for a quest", "Oh sorry was just looking around"};
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
										talkToHusband(npc, owner);
									break;
									case 1:
										owner.setBusy(false);
										npc.unblock();
									break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	
	private void finished(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hello again", "thank you for your help"})
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Don't mention it"}) 
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

	
	private void talkToHusband(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Our daughter has disappeared", "Maybe you should talk to my husband"})
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Alright"})
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

	
	private void questStarted(final Npc npc, final Player owner)
	{
		if(owner.getInventory().countId(765) == 0) 
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello, Edmond has asked me to help find your daughter"}, true)
			{
				public void finished()
				{
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"yes he told me", "I've begun making your special gas mask", "But I need some dwellberries to finish it"})
					{
						public void finished() 
						{
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I'll try to get some"})
							{
								public void finished() 
								{
									World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"The best place to look is in mcgrubor's wood to the north"}) 
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
		else
		if(owner.getInventory().countId(765) > 0)
		{
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello, Edmond has asked me to help find your daughter"}, true)
			{
				public void finished()
				{
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"yes he told me", "I've begun making your special gas mask", "But I need some dwellberries to finish it"})
					{
						public void finished()
						{
							World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Yes I've got some here"})
							{
								public void finished()
								{
									owner.sendMessage("You give Alrena the dwellberries");
									owner.getInventory().remove(765, 1);
									owner.sendInventory();
									World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
									{
										public void action() 
										{
											owner.sendMessage("Alrena crushed the berries into a smooth paste");
											World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
											{
												public void action() 
												{
													owner.sendMessage("She smears the paste over a strange mask");
													World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"There we go all done", "While in west ardougne you must wear this at all times", "or you'll never make it back"})
													{
														public void finished() 
														{	
															owner.sendMessage("Alrenda gives you the mask");
															owner.getInventory().add(766, 1);
															owner.sendInventory();
															owner.incQuestCompletionStage(Config.Quests.PLAGUE_CITY);
															World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"While you two are digging I'll make a spare mask", "I'll hide it in the cupboard incase the mourners come in"})
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
			});
		}	
	}
	
	private void questStage2(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello alrena"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hello darling", "how's that tunnel coming along?"}) 
				{
					public void finished() 
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"we're getting there"})
						{
							public void finished()
							{
								World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well I'm sure you're quicker than Edmond"})
								{
									public void finished() 
									{	
										World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I just need to soften the soil and then we'll start digging"}) 
										{
											public void finished() 
											{
												World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"If you lose your protective clothing I've made a spare set", "They're hidden in the cupboard incase the mourners come in"}) 
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
	
	private void questStage4(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hi, have you managed to get through to west ardougne?"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Not yet, but I should be going through soon"})
				{
					public void finished()
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Make sure you wear your mask while you are over there", "I can't Think of a worse way to die"})
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
	
	private void questStageRest(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hi, have you managed to get through to west ardougne?"}, true) 
		{
			public void finished() 
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Yeah we have managed to break through", "shouldn't be long until I find your daughter"})
				{
					public void finished()
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Make sure you wear your mask while you are over there", "I can't Think of a worse way to die"})
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
	
	
	
	
	
}