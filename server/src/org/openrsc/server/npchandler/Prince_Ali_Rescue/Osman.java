/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
package org.openrsc.server.npchandler.Prince_Ali_Rescue;

import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.InvItem;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.World;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.npchandler.NpcHandler;

import java.util.ArrayList;
import org.openrsc.server.Config;

public class Osman implements NpcHandler {

	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(Config.Quests.PRINCE_ALI_RESCUE);
		if(q != null) {
			if(q.finished()) {
				questFinished(npc, owner);
			} else {
				switch(q.getStage()) {
					case 0:
						questJustStarted(npc, owner);
						break;
					case 1:
						questUnderway(npc, owner);
						break;
					case 2:
					case 3:
					case 4:
						if(owner.getInventory().countId(242) > 0 || owner.leelaHasKey()) {
							questUnderway(npc, owner);
						} else {
							lostKey(npc, owner);
						}
						break;
					case 5:
						princeReleased(npc, owner);
				}
			}
		} else { 
			questNotStarted(npc, owner);
		}
	}

	private final void princeReleased(final Npc npc, final Player owner) {
		final String[] messages0 = {"The prince is safe, and on his way home with Leela", "You can pick up your payment from the chancellor"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private final void lostKey(final Npc npc, final Player owner) {
		if(owner.getInventory().countId(247) > 0 && owner.getInventory().countId(169) > 0) {
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well done, we can remake the key now."}, true) {
				public void finished() {
					owner.sendMessage("Osman takes the Key imprint and the bronze bar");
					owner.getInventory().remove(new InvItem(247, 1));
					owner.getInventory().remove(new InvItem(169, 1));
					owner.sendInventory();
					owner.giveLeelaKey();
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Pick up the key from Leela"}) {
						public void finished() {
							owner.setBusy(false);
							npc.unblock();
						}
					});
				}
			});
		} else {
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"You have lost the key for the Princes cell", "Get me the imprint and some more bronze, and I can get another made"}, true) {
				public void finished() {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I will go and get the key imprint again."}) {
						public void finished() {
							owner.setBusy(false);
							npc.unblock();
						}
					});
				}
			});
		}

	}

	private final void questUnderway(final Npc npc, final Player owner) {
		if(owner.getInventory().countId(247) > 0 && owner.getInventory().countId(169) > 0) {
			World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well done, we can make the key now."}, true) {
				public void finished() {
					owner.sendMessage("Osman takes the Key imprint and the bronze bar");
					owner.getInventory().remove(new InvItem(247, 1));
					owner.getInventory().remove(new InvItem(169, 1));
					owner.sendInventory();
					owner.giveLeelaKey();
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Pick up the key from Leela", "I will let you get 80 coins from the chancellor for getting this key"}) {
						public void finished() {
							owner.incQuestCompletionStage(Config.Quests.PRINCE_ALI_RESCUE);
							owner.setBusy(false);
							npc.unblock();
						}
					});
				}
			});
		} else {
			final String[] messages0 = {"Can you tell me what I still need to get?"};
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages0, true) {
				public void finished() {
					ArrayList<String> strings = new ArrayList<String>();
					strings.add("Let me check. You need:");
					boolean b1 = owner.getInventory().countId(244) > 0;
					boolean b2 = owner.getInventory().countId(194) > 0;
					boolean b3 = owner.getInventory().countId(240) > 0;
					boolean b4 = owner.getInventory().countId(237) > 0;
					if(owner.leelaHasKey() && owner.getQuest(Config.Quests.PRINCE_ALI_RESCUE).getStage() > 1) {
						strings.add("To collect the key from Leela");
					} else {
						strings.add("A print of the key in soft clay and a bronze bar");
						strings.add("Then collect the key from Leela");						
					}
			
					if(b1) {
						strings.add("The wig you have got, well done");
					} else {
						strings.add("You need to make a Blonde Wig somehow. Leela may help");
					}
					if(b2) {
						strings.add("You have got the skirt, good");
					} else {
						strings.add("A skirt the same as Keli's,");
					}
					if(b4) {
						strings.add("yes, you have the rope");
					} else {
						strings.add("Rope to tie Keli up with");
					}
					if(b3) {
						strings.add("You have got the skin paint, well done");
						strings.add("I thought you would struggle to make that");
					} else {
						strings.add("Something to colour the princes skin lighter");
					}
					strings.add("You still need some way to stop the guard from interfering");
					strings.add("Once you have everything, Go to Leela");
					strings.add("She must be ready to get the prince away");
					String[] messages = new String[strings.size()];
					strings.toArray(messages);
					World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages) {
						public void finished() {
							owner.setBusy(false);
							npc.unblock();
						}
					});
				}
			});
		}
	}

	private final void questJustStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"The chancellor trusts me. I have come for instructions"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Our Prince is captive by the Lady Keli", "We just need to make the rescue", "There are three things we need you to do"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
							public void action() {
								final String[] options0 = {"What is the first thing I must do?", "What is needed second?", "And the final thing you need?"};
								owner.setBusy(false);
								owner.sendMenu(options0);
								owner.setMenuHandler(new MenuHandler(options0) {
									public void handleReply(final int option, final String reply) {
										owner.setBusy(true);
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										switch(option) {
											case 0:
												firstThing(npc, owner);
												break;
											case 1:
												secondThing(npc, owner);
												break;
											case 2:
												thirdThing(npc, owner);
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

	private final void questFinished(final Npc npc, final Player owner) {
		final String[] messages1 = {"Well done. A great rescue", "I will remember you if I have anything dangerous to do"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages1, true) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private final void questNotStarted(final Npc npc, final Player owner) {
		final String[] messages0 = {"Hello, I am Osman", "What can I assist you with"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options0 = {"You don't seem very tough. Who are you?", "I hear wild rumours about a Prince", "I am just being nosy."};
						owner.setBusy(false);
						owner.sendMenu(options0);
						owner.setMenuHandler(new MenuHandler(options0) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										whoAreYou(npc, owner);
										break;
									case 1:
										prince(npc, owner);
										break;
									case 2:
										nosy(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void nosy(final Npc npc, final Player owner) {
		final String[] messages1 = {"That bothers me not", "The secrets of Al Kharid protect themselves"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages1) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void whoAreYou(final Npc npc, final Player owner) {
		final String[] messages2 = {"I am in the employ of the Emir", "That is all you need to know"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages2) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void prince(final Npc npc, final Player owner) {
		final String[] messages3 = {"The prince is not here. He is... away", "If you can be trusted, speak to the chancellor, Hassan"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages3) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void secondThing(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"We need the key, or a copy made", "If you can get some soft clay, then you can copy the key", "If you can convince Lady Keli to show it to you for a moment", "She is very boastful. It should not be too hard", "Bring the imprint to me, with a bar of bronze."}) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options1 = {"What is the first thing I must do?", "What exactly is needed second?", "And the final thing you need?", "Okay, I better go find some things"};
						owner.setBusy(false);
						owner.sendMenu(options1);
						owner.setMenuHandler(new MenuHandler(options1) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										firstThing(npc, owner);
										break;
									case 1:
										secondThing(npc, owner);
										break;
									case 2:
										thirdThing(npc, owner);
										break;
									case 3:
										betterGo(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void thirdThing(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"You will need to stop the guard at the door", "Find out if he has any weaknesses, and use them"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options2 = {"What is the first thing I must do?", "What is needed second?", "Okay, I better go find some things"};
						owner.setBusy(false);
						owner.sendMenu(options2);
						owner.setMenuHandler(new MenuHandler(options2) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										firstThing(npc, owner);
										break;
									case 1:
										secondThing(npc, owner);
										break;
									case 2:
										betterGo(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void betterGo(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"May good luck travel with you", "Don't forget to find Leela. It can't be done without her help"}) {
			public void finished() {
				owner.incQuestCompletionStage(Config.Quests.PRINCE_ALI_RESCUE);
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void firstThing(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"The prince is guarded by some stupid guards, and a clever woman", "The woman is our only way to get the prince out", "Only she can walk freely about the area", "I think you will need to tie her up", "one coil of rope should do for that", "And then disguise the prince as her to get him out without suspicion"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"How good must the disguise be?"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Only enough to fool the guards at a distance", "Get a skirt like hers. Same colour, same style", "We will only have a short time", "A blonde wig too. That is up to you to make or find", "Something to colour the skin of the prince.", "My daughter and top spy, leela, can help you there"}) {
							public void finished() {
								World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
									public void action() {
										final String[] options3 = {"Explain the first thing again", "What is needed second?", "And the final thing you need?", "Okay, I better go find some things"};
										owner.setBusy(false);
										owner.sendMenu(options3);
										owner.setMenuHandler(new MenuHandler(options3) {
											public void handleReply(final int option, final String reply) {
												owner.setBusy(true);
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												switch(option) {
													case 0:
														firstThing(npc, owner);
														break;
													case 1:
														secondThing(npc, owner);
														break;
													case 2:
														thirdThing(npc, owner);
														break;
													case 3:
														betterGo(npc, owner);
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
}