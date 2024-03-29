package net.sf.robocode.test.equipment;

import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

/**
 *A simple test that the sound paths are getting passes though the system
 *correctly, are being assigned to their robots correctly, and
 *remain the same throughout the duration of the battle
 * 
 * @author Jarred FIlmer
 */
public class TestEquipInvalidSoundFile extends RobocodeTestBed {
	
	@Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "tested.equipment.SoundInvalidFile";
    }
}
