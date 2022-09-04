package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Decor extends GameEntity implements Actor {
	private ImageGraphics graphics;
	private ActorGame game;
	
	public Decor(ActorGame game, boolean fixed, Vector position, String image, float largeur, float hauteur) { //mettre float ou int
		super(game, fixed, position);
		this.game = game;
		graphics = new ImageGraphics(image, largeur, hauteur);
		graphics.setParent(getEntity());
		game.ajouterActor(this);
	}

	public void destroy() {
		super.destroy();
		game.supprimerActor(this);
	}
	
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
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
