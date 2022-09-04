package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

abstract public class Trigger extends GameEntity implements Actor {
	private BasicContactListener contactListener;
	private ImageGraphics graphics;
	float timer = 0;

	public Trigger(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		contactListener = new BasicContactListener();
		getEntity().addContactListener(contactListener);

		game.ajouterActor(this);
	}

	public boolean isHit() {
		return (getOwner().getPlayLoad()).isInContactList(contactListener);
	}

	public void setGraphics(ImageGraphics graphics) {
		this.graphics = graphics;
		graphics.setParent(getEntity());
	}
	
	public void setDepth(float depth) {
		graphics.setDepth(depth);
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroy() {
		super.destroy();
		getOwner().supprimerActor(this);
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		graphics.draw(canvas);
	}

}
