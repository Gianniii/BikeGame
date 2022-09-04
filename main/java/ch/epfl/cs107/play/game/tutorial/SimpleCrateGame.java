package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.Game;

public class SimpleCrateGame implements Game {
	private ImageGraphics graphics;
	private ImageGraphics graphics1;
	private Entity crate;
	private Entity block;
	private Window window;
	private World world;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(true);
		entityBuilder.setPosition(new Vector(1.f, 0.5f));
		block = entityBuilder.build();

		EntityBuilder entityBuilder1 = world.createEntityBuilder();
		entityBuilder1.setFixed(false);
		entityBuilder1.setPosition(new Vector(0.2f, 4.0f));
		crate = entityBuilder1.build();

		graphics1 = new ImageGraphics("box.4.png", 1, 1);
		graphics1.setAlpha(1.0f);
		graphics1.setDepth(0.0f);
		graphics1.setParent(crate);

		graphics = new ImageGraphics("stone.broken.4.png", 1, 1);
		graphics.setAlpha(1.0f);
		graphics.setDepth(0.0f);
		graphics.setParent(block);
		
		// At this point, your body is in the world, but it ha no geometry
		// You need to use another builder to add shapes
		PartBuilder partBuilder = block.createPartBuilder();
		// Create a square polygon, and set the shape of the builder to this polygon
		Polygon polygon = new Polygon(
				new Vector(0.0f, 0.0f),
				new Vector(1.0f, 0.0f),
				new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f)
			);
		partBuilder.setShape(polygon);
		
		//Finally, do not forget the following line
		partBuilder.build();
		
		//Note : we do not need to keep a reference on partBuilder
		
		PartBuilder partBuilder1 = crate.createPartBuilder();
		partBuilder1.setShape(polygon);
		partBuilder1.build();

		return true;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		graphics.draw(window);
		graphics1.draw(window);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
	}
}

