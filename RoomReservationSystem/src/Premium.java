
public class Premium extends Room {
	
	public Premium(String roomName, String description, String rType, String category, String specService) {
		super(roomName, description, rType, category, specService);
		
	}

	@Override
	public double determinePrice() {
		switch (getRType()) {
        case "single":
            return 350;
        case "double":
            return 500;
        case "triple":
            return 690;
        default:
            return 0;
		}
	}

}
