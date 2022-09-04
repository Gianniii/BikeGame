
package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.EntityBuilder;

public abstract class GameEntity { 
	// we need to keep references on our game objects
	private Entity entity;
	private ActorGame actorGame;
	
	public GameEntity(ActorGame game, boolean fixed, Vector position) {
	   if(game == null) {
			throw new NullPointerException("L'argument game est null");
		}
		if (position == null) {
			throw new NullPointerException("L'argument position est null");
		}
		actorGame = game;
		entity = actorGame.createEntity(fixed, position);
	}
	
	public GameEntity(ActorGame game, boolean fixed) { 
		if(game == null) {
			throw new NullPointerException("L'argument game est null");
		}
		actorGame = game;
		entity = actorGame.createEntity(fixed);
	}
	
	/** Destroys entity and associated parts and constraints */ 
    public void destroy() {
    	    entity.destroy();
    }
    
    public boolean isContact (Contact contact) {
     	return (contact.getOther().getEntity() == entity); 
    }
    
    /** @return true if the list of contacts of contactListener contains the entity of this GameEntity */
    public boolean isInContactList(BasicContactListener contactListener) {
    	  return  contactListener.hasContactWith(entity);
    }
    
    protected Entity getEntity() {
    	   return entity;
    }
    
    protected ActorGame getOwner() {
    	   return actorGame;
    }
    
    public Vector getVelocity() {
		return entity.getVelocity();
	}
    
	public void setVelocity(Vector vector) {
		entity.setVelocity(vector);
	}
}
