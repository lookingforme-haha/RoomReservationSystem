
public class StandedDoubleRoom extends Room implements SpecialServices {
	private double RampLength;
	private double RampWidth;
	private int NumOfEmergencyCalls;
	
	public StandedDoubleRoom(String roomName, String description, String rType, String category, double rampLength, double rampWidth, int NumOfEmg, String specService) {
		super(roomName, description, rType, category, specService);
		this.RampLength = rampLength;
		this.RampWidth = rampWidth;
		this.NumOfEmergencyCalls = NumOfEmg;
	}
	
	@Override
    public double getRampLength() {
        return RampLength;
    }

    @Override
    public double getRampWidth() {
        return RampWidth;
    }

    @Override
    public int getEmergencyCallingFacilities() {
        return NumOfEmergencyCalls;
    }

	@Override
	public double determinePrice() {
		return 350;
	}

}
