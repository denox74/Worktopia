package Clases;

import java.util.ArrayList;
import java.util.List;

public class Oficinas {

        private String name;
        private List<Oficinas> oficina;

        public Office(String name) {
            this.name = name;
            this.rooms = new ArrayList<>();
        }

        public void addRoom(Room room) {
            rooms.add(room);
        }

        public List<Room> getRooms() {
            return rooms;
        }

        // Otros métodos relevantes
    }

