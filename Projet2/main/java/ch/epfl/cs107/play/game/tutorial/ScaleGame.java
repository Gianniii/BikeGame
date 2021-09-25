package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.Game;

public class ScaleGame implements Game {
	private ImageGraphics blockGraphics;
	private ImageGraphics ballGraphics;
	private ImageGraphics plankGraphics;
	private Entity ball;
	private Entity block;
	private Entity plank;
	private Window window;
	private World world;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(true);
		entityBuilder.setPosition(new Vector(-5.0f, -1.0f));
		block = entityBuilder.build();

		EntityBuilder entityBuilder1 = world.createEntityBuilder();
		entityBuilder1.setFixed(false);
		entityBuilder1.setPosition(new Vector(0.5f, 4.f));
		ball = entityBuilder1.build();
		
		EntityBuilder entityBuilder2 = world.createEntityBuilder();
		entityBuilder2.setFixed(false);
		entityBuilder2.setPosition(new Vector(-2.5f, 0.8f));
		plank = entityBuilder2.build();
		
		PartBuilder partBuilder = block.createPartBuilder();
		Polygon polygon = new Polygon (
				new Vector(0.0f, 0.0f),
				new Vector(10.0f, 0.0f),
				new Vector(10.0f, 1.0f),
				new Vector(0.0f, 1.0f)
			);
		partBuilder.setShape(polygon);
		partBuilder.build();
		
		Polygon polygon1 = new Polygon (
				new Vector(0.0f, 0.0f),
				new Vector(5f, 0.0f),
				new Vector(5f, 0.2f),
				new Vector(0.0f, 0.2f)
			);
		
		PartBuilder partBuilder1 = plank.createPartBuilder();
		partBuilder1.setShape(polygon1);
		partBuilder1.build();
		
		PartBuilder partBuilder2 = ball.createPartBuilder();
		Circle circle = new Circle(0.5f);
		partBuilder2.setShape(circle);
		partBuilder2.build();
		
		
		
		
		
		plankGraphics = new ImageGraphics("wood.3.png", 5f, 0.2f);
		plankGraphics.setParent(plank);
		
		ballGraphics = new ImageGraphics("explosive.11.png",2.0f * 0.5f, 2.0f * 0.5f, new Vector(0.5f, 0.5f));
		ballGraphics.setParent(ball);

		blockGraphics = new ImageGraphics("stone.broken.4.png", 10, 1);
		blockGraphics.setParent(block);

		RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
		revoluteConstraintBuilder.setFirstEntity(block);
		revoluteConstraintBuilder.setFirstAnchor(new Vector(10.0f / 2, (1.0f * 7) / 4));
		revoluteConstraintBuilder.setSecondEntity(plank);
		revoluteConstraintBuilder.setSecondAnchor(new Vector(5.0f / 2, 0.2f / 2));
		revoluteConstraintBuilder.setInternalCollision(true);
		revoluteConstraintBuilder.build();
				
		return true;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
			ball.applyAngularForce(10.0f) ;
			} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			ball.applyAngularForce(-10.0f) ;
			}
			
		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		blockGraphics.draw(window);
		ballGraphics.draw(window);
		plankGraphics.draw(window);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
	}
}