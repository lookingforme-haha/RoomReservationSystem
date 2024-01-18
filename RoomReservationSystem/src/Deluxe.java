
public class Deluxe extends Room {	

	public Deluxe(String roomName, String description, String rType, String category, String specService) {
		super(roomName, description, rType, category, specService);
		
	}

	@Override
	public double determinePrice() {
		switch (getRType()) {
        case "single":
            return 280;
        case "double":
            return 430;
        case "triple":
            return 600;
        default:
            return 0;
		}
	}

}
