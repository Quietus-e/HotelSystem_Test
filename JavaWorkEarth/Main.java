import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {

        Command command = new Command();
        HotelRooms HotelRooms = new HotelRooms();
        String filename = "input.txt";
        List<Command> commands = command.getCommandsFromFileName(filename);
        System.out.println("Start Command");
        System.out.println();

        for (Command cm: commands) {
            switch (cm.getName()) {
                case "create_hotel":
                    //GetData
                    List<Object> params = cm.getParams();
                    int floor = (int) params.get(0);
                    int roomPerFloor = (int) params.get(1);

                    HotelRooms.setFloor(floor); // set floors
                    HotelRooms.setRoom(roomPerFloor); //set Room
                    HotelRooms.CreateHotel(floor, roomPerFloor);
                    String hotel = "Hotel created with " + floor + " floor(s), " + roomPerFloor + " room(s) per floor.";
                    System.out.println(hotel);
                    break;


                case"book":
                    //GetData
                    List<Object> paramsForBooker = cm.getParams();
                    List<Integer> AvaliableRoom = HotelRooms.getAvaliableHotelListRoom();
                    Stack<Integer> KeyDeck = HotelRooms.getkeycardsdeck();
                    int roomNumber = (int) paramsForBooker.get(0);
                    String nameBooker = (String) paramsForBooker.get(1);

                    if(AvaliableRoom.isEmpty() && KeyDeck.empty()){
                        break;
                    }else{
                        if(AvaliableRoom.contains(roomNumber)){
                            int Key = KeyDeck.pop();
                            HotelRooms.setkeycardsdeck(KeyDeck);
                            paramsForBooker.add(Key);
                            HotelRooms.getRecordBook().put(roomNumber, paramsForBooker);
                            AvaliableRoom.remove(Integer.valueOf(roomNumber));
                            HotelRooms.setAvaliableHotelListRoom(AvaliableRoom); 
                            System.out.println("Room "+roomNumber+" is booked by "+nameBooker+" with keycard number "+Key+".");
                        }else{
                            Map<Integer, List<Object>> ShowBooked = HotelRooms.getRecordBook();
                            System.out.println("Cannot book room "+roomNumber+" for "+nameBooker+", The room is currently booked by "+ShowBooked.get(roomNumber).get(1)+".");
                        }
                    }
                    break;


                case"list_available_rooms":
                    List<Integer> list_available_rooms = HotelRooms.getAvaliableHotelListRoom();
                    StringJoiner joinerComma = new StringJoiner(",");
                        for (Integer availableRoom : list_available_rooms) {
                            joinerComma.add(availableRoom.toString());
                        }
                        String availableRooms = joinerComma.toString();
                        System.out.println(availableRooms); 
                    break;


                case"checkout":
                    //GetData
                    List<Object> paramsForCheckout = cm.getParams();
                    int keyreturn = (int)paramsForCheckout.get(0);
                    String Bookerreturn = (String)paramsForCheckout.get(1);
                    Map<Integer, List<Object>> Checkout = HotelRooms.getRecordBook();

                    int RoomQ = HotelRooms.findKeyByValue(Checkout , keyreturn);
                    
                    if(Checkout.get(RoomQ).get(1).equals(Bookerreturn)){ //Check KeyCard
                        System.out.println("Room "+ RoomQ +" is checkout."); 
                        Stack<Integer> returnthekey = HotelRooms.getkeycardsdeck();
                        returnthekey.push(keyreturn);
                        List<Integer> DoAddAvaliableRoom = HotelRooms.getAvaliableHotelListRoom();
                        DoAddAvaliableRoom.add(RoomQ);
                        HotelRooms.getRecordBook().remove(RoomQ);
                        break;
                    }else{
                        System.out.println("Only "+Checkout.get(RoomQ).get(1)+" can checkout with keycard number "+keyreturn+".");
                    }
                    break;


                case"list_guest":
                        Map<Integer, List<Object>> list_guest = HotelRooms.getRecordBook();

                        Map<Integer, List<Object>> sort = HotelRooms.sortMapByIndex(list_guest, 3);
                        String lists_guests = HotelRooms.List_Guest(sort);
                        System.out.println(lists_guests);
                    break;


                case"get_guest_in_room":
                    List<Object> get_guest_in_room = cm.getParams();
                    int inputRoom = (int)get_guest_in_room.get(0); //example : 203 
                    Map<Integer, List<Object>> GetGuestInRoom = HotelRooms.getRecordBook();
                    System.out.println(GetGuestInRoom.get(inputRoom).get(1));
                    break;


                case"list_guest_by_age":
                    List<Object> guest_by_age = cm.getParams();
                    Map<Integer, List<Object>> list_Age = HotelRooms.getRecordBook();
                    String comparer = (String)guest_by_age.get(0);
                    int Byage = (int)guest_by_age.get(1);

                    if(comparer.equals("<"))
                    {
                        for (List<Object> valueList : list_Age.values()) {
                            if ((int) valueList.get(2) < Byage) {
                                System.out.println( valueList.get(1) );
                            }
                        }
                    }

                    if(comparer.equals(">"))
                    {
                        for (List<Object> valueList : list_Age.values()) {
                            if ((int) valueList.get(2) > Byage) {
                                System.out.println( valueList.get(1) );
                            }
                        }
                    }
                    break;


                case"list_guest_by_floor":
                    List<Object> guest_by_floor = cm.getParams();
                    int Byfloor = (int) guest_by_floor.get(0);
                    Map<Integer, List<Object>> list_Floor = HotelRooms.getRecordBook();

                    String showGByFloor = HotelRooms.GuestByFloor(list_Floor , Byfloor);
                    System.out.println(showGByFloor);
                    break;


                case"checkout_guest_by_floor":
                    //GetData
                    List<Object> Checkout_by_floor = cm.getParams();
                    int CheckoutFloor = (int)Checkout_by_floor.get(0);
                    List<Integer> listDoAvaliableRoom = HotelRooms.getAvaliableHotelListRoom();
                    Map<Integer, List<Object>> checkout_guest_by_floor = HotelRooms.getRecordBook();
                    Stack<Integer> DecksKey = HotelRooms.getkeycardsdeck();

                    List<Integer> listCkRoom = HotelRooms.CheckAvaliable_CheckO_GBy_F(checkout_guest_by_floor, CheckoutFloor);
                    
                    StringJoiner joinerCommaSpeac = new StringJoiner(", ");
                    for (Integer value : listCkRoom) {
                        if (listDoAvaliableRoom.contains(value)) {
                            System.out.println("can't check out becasue this room is not book!");
                            break;
                        }else{
                            int keyreturns = (int)checkout_guest_by_floor.get(value).get(3);
                            DecksKey.push(keyreturns);
                            listDoAvaliableRoom.add(value);
                            HotelRooms.getRecordBook().remove(value);
                            joinerCommaSpeac.add(Integer.toString(value));
                        }
                    }
                    String listCkOuted = joinerCommaSpeac.toString();
                    System.out.println("Room "+listCkOuted+" are checkout.");
                    break;


                case"book_by_floor":
                    //GetData
                    List<Object> book_by_floor = cm.getParams();
                    int BookFloors = (int)book_by_floor.get(0); //floors
                    String NameBookFloors = (String)book_by_floor.get(1);
                    int AgeBookFloors = (int)book_by_floor.get(2);
                    List<Integer> listAvaliRoom = HotelRooms.getAvaliableHotelListRoom();
                    Stack<Integer> DecksBookingKey = HotelRooms.getkeycardsdeck();
                    int PerOfRoom = HotelRooms.getRoom();

                    List<Integer> listBookRoom = HotelRooms.GetCheckBooksByFloors(listAvaliRoom, BookFloors);
                    List<Integer> RequestBookFloor = HotelRooms.RequestRoomFloorsAll(BookFloors, PerOfRoom);
                    
                    StringJoiner joinerCommaSpeacBooks = new StringJoiner(", ");
                    StringJoiner joinerCommaSpeacKeys = new StringJoiner(", ");
                    Collections.sort(listBookRoom);

                        if (listBookRoom.equals(RequestBookFloor)) {
                            for (Integer value : listBookRoom){
                                
                                List<Object> BookingListFloors = new ArrayList<>();

                                int GetKey = DecksBookingKey.pop();
                                HotelRooms.setkeycardsdeck(DecksBookingKey);

                                BookingListFloors.add(value); //room
                                BookingListFloors.add(NameBookFloors); //name 
                                BookingListFloors.add(AgeBookFloors); // age
                                BookingListFloors.add(GetKey); //num key

                                HotelRooms.getRecordBook().put(value, BookingListFloors);
                                listAvaliRoom.remove(Integer.valueOf(value));
                                HotelRooms.setAvaliableHotelListRoom(listAvaliRoom);

                                joinerCommaSpeacBooks.add(Integer.toString(value));
                                Map<Integer, List<Object>> GetKeys = HotelRooms.getRecordBook();
                                int keys = (int)GetKeys.get(value).get(3);
                                joinerCommaSpeacKeys.add(Integer.toString(keys));
                               
                            }  
                        }else{
                            System.out.println("Cannot book floor "+BookFloors+" for "+NameBookFloors+".");
                            break;
                        }
                    String listBooked = joinerCommaSpeacBooks.toString();
                    String sortedKeys = Arrays.stream(joinerCommaSpeacKeys.toString().split(", ")).sorted().collect(Collectors.joining(", "));
                    System.out.println("Room "+listBooked+" are booked with keycard number "+sortedKeys);
                    break;


                default:
                    break;
            }
        }
    
    }
}