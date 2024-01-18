
public class Standed extends Room {

	

	public Standed(String roomName, String description, String rType, String category, String specService) {
		super(roomName, description, rType, category, specService);
		
	}

	@Override
	public double determinePrice() {
		switch (getRType()) {
        case "single":
            return 230;
        case "double":
            return 350;
        case "triple":
            return 500;
        default: 
        	return 0;
		}
	}

}
