package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.view.spriteEnums.LookEnum;

import java.util.HashMap;
import java.util.Map;

public abstract class Look {
	private Map<Enum<? extends LookEnum>, Texture> sprites;
	private Texture current;

	public Look() {
		sprites = new HashMap<Enum<? extends LookEnum>, Texture>();
	}

	public Look(Enum<? extends LookEnum> kind) {
		sprites = new HashMap<Enum<? extends LookEnum>, Texture>();
		Texture t = new Texture(kind.toString() + ".png");
		sprites.put(kind, t);
		current = t;
	}

	public void dispose() {
		for (Texture t : sprites.values()) {
			t.dispose();
		}
	}

	public Texture getCurrent() {
		return current;
	}

}
