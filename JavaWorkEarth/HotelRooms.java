import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringJoiner;

class HotelRooms {
    
    public HotelRooms()
    {
        RecordBook = new HashMap<>();
    }


    public HotelRooms(List<Integer> AvaliableHotelListRoom)
    {
        this.AvaliableHotelListRoom = AvaliableHotelListRoom;
    }

    //Aribute
    private int floor;
    private int room;
    private Stack<Integer> keycardsdeck;
    private List<Integer> AvaliableHotelListRoom ;
    private Map<Integer, List<Object>> RecordBook;

    //setter
    public void setFloor(int floor)
    {
        this.floor = floor;
    }
    public void setRoom(int room)
    {
        this.room = room;
    }
    public void setkeycardsdeck(Stack<Integer> keycardsdeck)
    {
        this.keycardsdeck = keycardsdeck;
    }

    public void setAvaliableHotelListRoom(List<Integer> AvaliableHotelListRoom)
    {
        this.AvaliableHotelListRoom = AvaliableHotelListRoom;
    }

    public void setRecordBook(Map<Integer, List<Object>> RecordBook)
    {
        this.RecordBook = RecordBook;
    }

    //getter
    public int getFloor()
    {
        return floor;
    }
    public int getRoom()
    {
        return room;
    }
    public Stack<Integer> getkeycardsdeck()
    {
        return keycardsdeck;
    }
    public List<Integer> getAvaliableHotelListRoom()
    {
        return AvaliableHotelListRoom;
    }
    public Map<Integer, List<Object>> getRecordBook()
    {
        return RecordBook;
    }
    
    //Method Create Hotel Floor and Room
    public List<Integer> CreateHotel(int floor , int room)
    {
        List<Integer> HotelCreate = new ArrayList<>();
        Stack<Integer> GenKey = new Stack<>();
        for(int f = 1; f <= floor; f++ ){
            int floors = f * 100;
            for(int i = 1; i <= room; i++){
                int sum = floors + i;
                HotelCreate.add(sum);
            }
        }
        //Make KeyCard
        for(int k = 1; k <= HotelCreate.size(); k++){
            GenKey.push(k);
        }
        Collections.reverse(GenKey);
        setkeycardsdeck(GenKey);
        setAvaliableHotelListRoom(HotelCreate);
        return getAvaliableHotelListRoom();
    }
    
    //Method for findKeyByValue : Map<Integer, List<Object>>
    public Integer findKeyByValue(Map<Integer, List<Object>> map, Object value) {
        for (Map.Entry<Integer, List<Object>> MapgetterKey : map.entrySet()) {
            if (MapgetterKey.getValue().contains(value)) {
                return MapgetterKey.getKey();
            }
        }
        return null;
    }

    //Method for sort in Map<Integer, List<Object>> {index = in List<Object> to sort}
    public Map<Integer, List<Object>> sortMapByIndex(Map<Integer, List<Object>> map, int index) {

        List<Map.Entry<Integer, List<Object>>> listOfMapper = new LinkedList<>(map.entrySet());
        //Build-in Collections.sort and Comparator
        Collections.sort(listOfMapper, new Comparator<Map.Entry<Integer, List<Object>>>() {

            public int compare(Map.Entry<Integer, List<Object>> Obj_1, Map.Entry<Integer, List<Object>> Obj_2) {
                Object Value_1 = Obj_1.getValue().get(index);
                Object Value_2 = Obj_2.getValue().get(index);

                return ((Comparable) Value_1).compareTo(Value_2);
            }
        });
    
        Map<Integer, List<Object>> result = new LinkedHashMap<>();

        for (Map.Entry<Integer, List<Object>> MapperList : listOfMapper) {
            result.put(MapperList.getKey(), MapperList.getValue());
        }
        return result;
    }
    
    //Method List_Guest
    public String List_Guest(Map<Integer, List<Object>> recordB){
        StringJoiner joinerComma = new StringJoiner(", ");
        for (Map.Entry<Integer, List<Object>> Mapper : recordB.entrySet()) {
            List<Object> valueList = Mapper.getValue();
            joinerComma.add((String) valueList.get(1));
        }
        String listGuestWithComma = joinerComma.toString();
        return listGuestWithComma;
    }
    
    //Method GuestByFloor
    public String GuestByFloor(Map<Integer, List<Object>> recordF , int Byfloor)
    {
        StringJoiner joinerComma = new StringJoiner(", ");
        for (Map.Entry<Integer, List<Object>> Mapper : recordF.entrySet()) {
            List<Object> values = Mapper.getValue();
            int GenByfloor = Byfloor*100;
            int TopFloorOnRoom = (GenByfloor+100) - 1;
            if ((int)values.get(0) >= GenByfloor && (int)values.get(0) <= TopFloorOnRoom) {
                joinerComma.add((String)values.get(1));
            }
        }
        String listByFloor = joinerComma.toString();
        return listByFloor;
    }

    //Method to Check Avaliable Room
    public List<Integer> CheckAvaliable_CheckO_GBy_F(Map<Integer, List<Object>> recordF , int Byfloor)
    {
        List<Integer> listCkRoom = new ArrayList<>();
        for (Map.Entry<Integer, List<Object>> Mapper : recordF.entrySet()) {
            List<Object> values = Mapper.getValue();
            int GenByfloor = Byfloor*100;
            int TopFloorOnRoom = (GenByfloor+100) - 1;

            if ((int)values.get(0) >= GenByfloor && (int)values.get(0) <= TopFloorOnRoom) {
                listCkRoom.add((int)values.get(0));
                //System.out.println(values.get(0));
            }
        }
        return listCkRoom;

    }

    //Method GetCheckBooksByFloors
    public List<Integer> GetCheckBooksByFloors (List<Integer> listAvaliableRoom, int Byfloor)
    {
        List<Integer> listBookRoom = new ArrayList<>();

        for (Integer Room : listAvaliableRoom){
            int GenByfloor = Byfloor*100;
            int TopFloorOnRoom = (GenByfloor+100) - 1;
            if(Room >= GenByfloor && Room <= TopFloorOnRoom){
                listBookRoom.add(Room);
            }
        }
        return listBookRoom;

    }
    
    //Method do request for booking all room by floor
    public List<Integer> RequestRoomFloorsAll (int floors , int PerRoom)
    {
        List<Integer> RoomAllFloors = new ArrayList<>();
        int GenFloors = floors*100;

        for(int i = 1; i <= PerRoom; i++){
            int room = GenFloors+i;
            RoomAllFloors.add(room);
        }
        return RoomAllFloors;
    }

    

    


}
