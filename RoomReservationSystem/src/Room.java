import java.util.StringJoiner;

public abstract class Room {
	private static int NextId = 101;
	private int id;
	private String RoomName;
	private String Description;
	private String RType;
	private String categories;
	private String guestId = null;
	private String SpecService;
	private int NDays = 0;
		
	
	public Room(String roomName, String description, String rType, String category, String SpecService) {
		this.id = NextId++;
		this.RoomName = roomName;
		this.Description = description;
		this.RType = rType;
		this.categories = category;
		this.SpecService = SpecService;
	}

	public int getid() {
		return id;
	}
	
	public String getSpecService() {
		return SpecService;
	}
	
	public String getCategories() {
		return categories;
	}

	public String getRoomName() {
		return RoomName;
	}

	public String getDescription() {
		return Description;
	}

	public String getRType() {
		return RType;
	}

//	public double getCostPerDay() {
//		return CostPerDay;
//	}

	public void checkinRoom(String guestID, int numDays) throws AccommodationException {
	    if (this.guestId == null) {
	        this.guestId = guestID;
	        this.NDays = numDays;
	    } else {
	        throw new AccommodationException("Already Booked!!!");
	    }
	}
	
	public boolean Availability() {
		if (this.guestId == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void checkoutRoom() throws AccommodationException {
		if(this.guestId != null) {
			this.guestId = null;
			this.NDays = 0;			
		} else {
			throw new AccommodationException("No Bookings found to check-out!!!");
		}
	}
	
	public abstract double determinePrice();
	
	public String toFileString() {
	    StringJoiner joiner = new StringJoiner(",");
	    joiner.add(RoomName);
	    joiner.add(Description);
	    joiner.add(RType);
	    joiner.add(categories);
	    joiner.add(SpecService);

	    if (this instanceof SpecialServices) {
	        SpecialServices specialRoom = (SpecialServices) this;
	        joiner.add(String.valueOf(specialRoom.getRampLength()));
	        joiner.add(String.valueOf(specialRoom.getRampWidth()));
	        joiner.add(String.valueOf(specialRoom.getEmergencyCallingFacilities()));
	    }

	    return joiner.toString();
	}	

	public static Room parseRoomFromString(String roomData) {
	    String[] data = roomData.split(",");
	    String roomName = data[0];
	    String description = data[1];
	    String rType = data[2];
	    String category = data[3];
	    String SpecService = data[4];

	    switch (category.toLowerCase()) {
	        case "deluxe":
	            return new Deluxe(roomName, description, rType, category, SpecService);
	        case "premium":
	            return new Premium(roomName, description, rType, category, SpecService);
	        case "standed":
	            return new Standed(roomName, description, rType, category, SpecService);
	        default:
	            if ("yes".equalsIgnoreCase(SpecService)) {
	                double rLength = Double.parseDouble(data[5]);
	                double rWidth = Double.parseDouble(data[6]);
	                int numOfEmg = Integer.parseInt(data[7]);
	                if ("double".equalsIgnoreCase(rType)) {
	                    return new StandedDoubleRoom(roomName, description, rType, category, rLength, rWidth, numOfEmg, SpecService);
	                } else if ("tripal".equalsIgnoreCase(rType)) {
	                    return new StandedTripalRoom(roomName, description, rType, category, rLength, rWidth, numOfEmg, SpecService);
	                }
	            }
	    }

	    return null;
	}

}
