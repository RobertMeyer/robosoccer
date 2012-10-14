package soccer;

import static org.junit.Assert.*;

import org.junit.Test;

public class SoccerPlayerTest {

	@Test
	public void absBearingTest() {
		SoccerPlayer player = new SoccerPlayer();
		System.out.println(player.getAbsoluteBearing(0, 0, -1, 0));
	}

}
