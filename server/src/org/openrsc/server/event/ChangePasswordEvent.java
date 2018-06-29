package org.openrsc.server.event;

import org.openrsc.server.Config;
import org.openrsc.server.ServerBootstrap;
import org.openrsc.server.database.game.ChangePassword;
import org.openrsc.server.model.Player;

/**
 * @author Kenix
 */

public class ChangePasswordEvent extends TimedEvent {
    private String newPassword;
    
	public ChangePasswordEvent(Player owner, String newPassword) {
		super(owner, 100, 30*1000);
        this.newPassword = newPassword;
	}
	
	public ChangePasswordEvent(Player owner, String newPassword, int delay) {
		super(owner, delay, 30*1000);
        this.newPassword = newPassword;
	}
	
	public void confirmPassword(String confirmedPassword){
        System.out.println("confirmPassword, newPassword: '" + newPassword + "', confirmedPassword: '" + confirmedPassword + "'");
        if(newPassword.equals(confirmedPassword)){
            // Update the password as it is confirmed.
            // TODO: This should go back to mudclient.currentPass to automatically have the password for next login
            
            ServerBootstrap.getDatabaseService().submit(new ChangePassword(owner, newPassword));
            
            owner.sendMessage(Config.PREFIX + " Password changed.");
        }
        else{
            owner.sendMessage(Config.PREFIX + " Confirmation password is different from original.");
            owner.sendMessage(Config.PREFIX + " Please try again.");
        }
    }
    
    public void onComplete() {
	    owner.sendMessage(Config.PREFIX + " Password not changed.");
    }
}
