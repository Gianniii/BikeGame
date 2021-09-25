package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SlipperyBlock extends GameEntity implements Actor {
	private final static String nomImage = "glass.3.png";
	private ImageGraphics graphics;

	public SlipperyBlock(ActorGame game, Vector position, float length, float height) {
		super(game, true, position);
		
		if (length < 0 || height < 0) {
			throw new IllegalArgumentException("La hauteur et la longueur doivent être strictement positive");
		}
		
		// set graphics
		graphics = new ImageGraphics(nomImage, length, height);
		graphics.setParent(getEntity());
		
		// build part
		partBuilder(length, height);

		game.ajouterActor(this);
	}

	// construit le corp physique de L'objet
	public void partBuilder(float length, float height) {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(length, 0.0f), new Vector(length, height),
				new Vector(0.0f, height));
		// Make it slippery
		partBuilder.setFriction(0);
		partBuilder.setShape(polygon);
		partBuilder.build();
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
