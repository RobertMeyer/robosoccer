package net.sf.robocode.test.items;

import java.util.Random;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.item.HaltPack;
import net.sf.robocode.battle.item.HealthPack;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.item.PoisonPack;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.control.RandomFactory;
import static org.mockito.Mockito.*;

public class TestItemPosition{

	
	
	/*@Test
	public void main(){
		
		Battle battle = new Battle(Mockito.mock(ISettingsManager.class), Mockito.mock(IBattleManager.class),
				Mockito.mock(IHostManager.class), Mockito.mock(IRepositoryManager.class),
				Mockito.mock(ICpuManager.class), Mockito.mock(BattleEventDispatcher.class));
		
		HealthPack health = new HealthPack(battle,"health1");
		HaltPack halt = new HaltPack(battle, "halt1");
		PoisonPack poison = new PoisonPack(battle, "poison1");
		
		RandomFactory.resetDeterministic(0);
		final Random random = RandomFactory.getRandom();
		
		health.setXLocation(health.getWidth() + random.nextDouble() * (800 - 2 * health.getWidth()));
		health.setYLocation(health.getHeight() + random.nextDouble() * (600 - 2 * health.getHeight()));
		halt.setXLocation(halt.getWidth() + random.nextDouble() * (800 - 2 * halt.getWidth()));
		halt.setYLocation(halt.getHeight() + random.nextDouble() * (600 - 2 * halt.getHeight()));
		poison.setXLocation(poison.getWidth() + random.nextDouble() * (800 - 2 * poison.getWidth()));
		poison.setYLocation(poison.getHeight() + random.nextDouble() * (600 - 2 * poison.getHeight()));
		
		Assert.assertNear(566.296806911193, health.getXLocation());
		Assert.assertNear(165.07893614917265, health.getYLocation());
		Assert.assertNear(498.9405462520779, halt.getXLocation());
		Assert.assertNear(326.22724266116967, halt.getYLocation());
		Assert.assertNear(470.2326000139853, poison.getXLocation());
		Assert.assertNear(213.27356772785788, poison.getYLocation());
		
	}*/

}
