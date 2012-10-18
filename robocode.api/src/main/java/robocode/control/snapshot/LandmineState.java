package robocode.control.snapshot;

public enum LandmineState {
	
	FIRED(0),
	HIT_VICTIM(1),
	EXPLODED(2),
	INACTIVE(3),
	WAITING(4);
	
	private final int value;
	
	private LandmineState(int value) {
        this.value = value;
    }
	
	public int getValue() {
        return value;
    }
	
	public static LandmineState toState(int value) {
        switch (value) {
            case 0:
                return FIRED;

            case 1:
                return HIT_VICTIM;

            case 2:
                return EXPLODED;

            case 3:
                return INACTIVE;
                
            case 4:
                return WAITING;

            default:
                throw new IllegalArgumentException("unknown value");
        }
    }

    public boolean isActive() {
        return this == FIRED;
    }

}
