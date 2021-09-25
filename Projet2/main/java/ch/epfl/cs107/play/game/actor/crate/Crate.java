package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Entity;

public class Crate extends GameEntity implements Actor {
	private ImageGraphics graphics;
	private float largeur;
	private float hauteur;
	
	public Crate(ActorGame game, boolean fixed, Vector position, String image, float largeur, float hauteur) { 
		super(game, fixed, position);
		if (largeur < 0 || hauteur < 0) {
			throw new IllegalArgumentException("La hauteur et la longueur doivent être strictement positive");
		}
		this.largeur = largeur;
		this.hauteur = hauteur;
		graphics = new ImageGraphics(image, largeur, hauteur);
		graphics.setParent(getEntity());
		partBuilder();
		game.ajouterActor(this);
	}
	
	public void partBuilder() { 
		   PartBuilder partBuilder = getEntity().createPartBuilder();
		   Polygon polygon = new Polygon(
					new Vector(0.0f, 0.0f),
					new Vector(largeur, 0.0f),
					new Vector(largeur, hauteur),
					new Vector(0.0f, hauteur)
				);
			partBuilder.setShape(polygon);
			partBuilder.build();
	}
	public void destroy() {
		super.destroy();
		getOwner().supprimerActor(this);
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
