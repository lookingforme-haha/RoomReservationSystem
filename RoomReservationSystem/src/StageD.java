import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

public class StageD {
//	private static final int NumOfRooms = 6;
	
	private static Scanner scan = new Scanner(System.in);
	private static ArrayList<Room> Accommodations = new ArrayList<>();
//	private static Room[] Accommodation = new Room[NumOfRooms]; 
	
	public static void main(String[] args) {
		loadRoomsFromFile(); //checking whether the file has data or not and load the data if available
		DisplayOptions(); //display the selection menu
		
	}
	
	public static void DisplayOptions() {
		int selection;
		//selection menu
		do { 
			System.out.println("Select: ");
			System.out.println("1. Add Room");
			System.out.println("2. Get Details");
			System.out.println("3. Check in");
			System.out.println("4. Check out");
			System.out.println("5. Rooms Summery");
			System.out.println("6. Save");
			System.out.println("7. Exit without Saving");
			System.out.println("Enter the Selection: ");			
			selection = scan.nextInt();
			scan.nextLine();
			
			switch (selection) {
			case 1: 
				AddRoom();
				break;
			case 2:
				System.out.println("Enter room ID: ");
				int rId = scan.nextInt();
				GetDetails(rId);
				break;
			case 3:
				System.out.println("Enter room ID: ");
				int rmId = scan.nextInt();
				System.out.println("Enter guest ID: ");
				String gId = scan.next();
				System.out.println("Number of days: ");
				int days = scan.nextInt();
				scan.nextLine();				
				CheckIn(rmId, gId, days);
				break;
			case 4:
				System.out.println("Enter room ID: ");
				int roId = scan.nextInt();
				CheckOut(roId);
				break;
			case 5:
				RoomSum();
				break;
			case 6:
				System.out.println("saving ... enter 7 to exit ...");
                saveRoomsToFile();
                break;
			default:
				if (selection > 6) {
					System.out.println("Invalid Selection Please try again !");
				} else if (selection == 7) {
					System.out.println("exiting ...");
				}
			}
			
		} while (selection != 7);		
	}

	private static void RoomSum() {
	    int booked = 0;
	    int available = 0;

//	    for (int i = 0; i < NumOfRooms; i++) {
//	        if (Accommodation[i] != null && Accommodation[i].Availability()) {
//	            available++;
//	        } else if (Accommodation[i] != null) {
//	            booked++;
//	        }
//	    }
//	    
//	    System.out.format("%-15s %-15s %-15s %-10s %-30s%n", "Room ID", "Room Type", "Category", "Status", "Service");
//
//	    for (int i = 0; i < NumOfRooms; i++) {
//	        if (Accommodation[i] != null) {
//	            System.out.format("%-15s %-15s %-15s %-10s %-30s%n",
//	                    Accommodation[i].getid(),
//	                    Accommodation[i].getRType(),
//	                    Accommodation[i].getCategories(),
//	                    Accommodation[i].Availability() ? "Available" : "Booked",
//	                    (Accommodation[i] instanceof SpecialServices) ? "special" : "normal");
//	        }
//	    }
	    
	    //Summary of all the rooms available and booked
	    System.out.format("%-15s %-15s %-15s %-10s %-30s%n", "Room ID", "Room Type", "Category", "Status", "Service");

        for (Room room : Accommodations) {
            System.out.format("%-15s %-15s %-15s %-10s %-30s%n",
                    room.getid(),
                    room.getRType(),
                    room.getCategories(),
                    room.Availability() ? "Available" : "Booked",
                    (room instanceof SpecialServices) ? "special" : "normal");

            if (room.Availability()) {
                available++;
            } else {
                booked++;
            }
        }
	    
	    System.out.println();
	    System.out.println("Total Booked Amount: " + booked);
	    System.out.println("Total Available Amount: " + available);
	}

	//checkout booked rooms
	private static void CheckOut(int ID) {
		Room room = Accommodations.get(ID-101);
		
		try {
            room.checkoutRoom();
            System.out.println("Check-out successful.");
        } catch (AccommodationException e) {
            System.out.println("Check-out failed. " + e.getMessage());
        }
	}
	
	//checking to available rooms
	private static void CheckIn(int ID, String gID, int Days) {
		Room room = Accommodations.get(ID-101);
		
			try {
	            room.checkinRoom(gID, Days);
	            double totalAmount = room.determinePrice() * Days;
	            System.out.println("Check-in successful. \nTotal amount to be paid: " + totalAmount);
	        } catch (AccommodationException e) {
	            System.out.println("Check-in failed. " + e.getMessage());
	        }
		
	}
	
	//get details about a specific room
	private static void GetDetails(int ID) {
		if (ID >= 101 && ID <= 106) {
			Room room = Accommodations.get(ID-101);
			
			System.out.println("Room Name: " + room.getRoomName());
			System.out.println("Description: " + room.getDescription());
			System.out.println("Room type: " + room.getRType());
			System.out.println("Room Availability: " + (room.Availability() ? "Available" : "Booked"));
		} else {
			System.out.println("Enter a Valid ID.");
			return;
		}
		
	}
	
	//adding a room
	private static void AddRoom() {
        System.out.println("Special service room (yes/no): ");
        String specService = scan.nextLine();

        while (!"yes".equalsIgnoreCase(specService) && !"no".equalsIgnoreCase(specService)) {
            System.out.println("Please enter 'yes' or 'no': ");
            specService = scan.nextLine();
        }

        if ("yes".equalsIgnoreCase(specService)) {
            
        	// special service room 
            System.out.println("Enter room type (double/tripal): ");
            String roomType = scan.nextLine();

            while (!"double".equalsIgnoreCase(roomType) && !"tripal".equalsIgnoreCase(roomType)) {
                System.out.println("Please enter 'double' or 'tripal': ");
                roomType = scan.nextLine();
            }

            System.out.println("Enter room name: ");
            String roomName = scan.nextLine();

            System.out.println("Enter room description: ");
            String description = scan.nextLine();

            String category = "standed";

            try {
                switch (roomType.toLowerCase()) {
                    case "double":
                        System.out.println("Ramp Length: ");
                        double rLength = scan.nextDouble();
                        System.out.println("Ramp Width: ");
                        double rWidth = scan.nextDouble();
                        System.out.println("Number of Emergency Calls: ");
                        int numOfEmg = scan.nextInt();
                        scan.nextLine();
                        StandedDoubleRoom sdRoom = new StandedDoubleRoom(roomName, description, roomType, category, rLength, rWidth, numOfEmg, specService);
                        Accommodations.add(sdRoom);
                        break;
                    case "tripal":
                        System.out.println("Ramp Length: ");
                        double rtLength = scan.nextDouble();
                        System.out.println("Ramp Width: ");
                        double rtWidth = scan.nextDouble();
                        System.out.println("Number of Emergency Calls: ");
                        int tNumOfEmg = scan.nextInt();
                        scan.nextLine();
                        StandedTripalRoom stRoom = new StandedTripalRoom(roomName, description, roomType, category, rtLength, rtWidth, tNumOfEmg, specService);
                        Accommodations.add(stRoom);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid room type. Room cannot be added.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } else {
            
        	// non-special service room
            System.out.println("Enter room type (single/double/tripal): ");
            String roomType = scan.nextLine();

            while (!"single".equalsIgnoreCase(roomType) && !"double".equalsIgnoreCase(roomType) && !"tripal".equalsIgnoreCase(roomType)) {
                System.out.println("Please enter 'single', 'double', or 'tripal': ");
                roomType = scan.nextLine();
            }

            System.out.println("Enter room name: ");
            String roomName = scan.nextLine();

            System.out.println("Enter room description: ");
            String description = scan.nextLine();

            System.out.println("Enter room category (deluxe/premium/standed): ");
            String category = scan.nextLine();

            try {
                switch (category.toLowerCase()) {
                    case "deluxe":
                        Deluxe Droom = new Deluxe(roomName, description, roomType, category, specService);
                        Accommodations.add(Droom);
                        break;
                    case "premium":
                        Premium Proom = new Premium(roomName, description, roomType, category, specService);
                        Accommodations.add(Proom);
                        break;
                    case "standed":
                        Standed Sroom = new Standed(roomName, description, roomType, category, specService);
                        Accommodations.add(Sroom);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid room category. Room not added.");
                }
                System.out.println("Room successfully added.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
	
	//saving data into the room.txt file
	private static void saveRoomsToFile() {
        try {
            FileWriter writer = new FileWriter("rooms.txt");
            for (Room room : Accommodations) {
                writer.write(room.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving rooms to file: " + e.getMessage());
        }
    }
	
	//loading data from room.txt
	private static void loadRoomsFromFile() {
	    try {
	        File file = new File("rooms.txt");
	        
	        if (file.length() == 0) {
	            System.out.println("No data available. (empty mode)");
	            return;
	        }

	        Scanner fileScanner = new Scanner(file);
	        while (fileScanner.hasNextLine()) {            
	            String roomData = fileScanner.nextLine().trim();
	            if (!roomData.isEmpty()) {
	                Room room = Room.parseRoomFromString(roomData);
	                if (room != null) {
	                    Accommodations.add(room);
	                }
	            }
	        }
	        System.out.println("Data available and successfully loaded.\n");
	        fileScanner.close();
	    } catch (IOException | NoSuchElementException e) {
	        System.out.println("Error loading data: " + e.getMessage());
	    }
	}


	
//	private static void loadRoomsFromFile() {
//        try {
//            Scanner fileScanner = new Scanner(new File("rooms.txt"));
//            while (fileScanner.hasNextLine()) {           	
//                String roomData = fileScanner.nextLine();
//                Room room = Room.parseRoomFromString(roomData);
//                if (room != null) {
//                    Accommodations.add(room);
//                }
//            }
//            System.out.println("data available and successfully loaded.\n");
//            fileScanner.close();
//        } catch (IOException | NoSuchElementException e) {
//            System.out.println("No data availbale. (empty mode)");
//        }
//    }

}
