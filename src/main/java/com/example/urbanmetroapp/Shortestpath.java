package com.example.urbanmetroapp;

import java.util.*;

class Edge {
    String station_name;
    int next_station;
    int distance;

    Edge(int next_station, int distance, String station_name) {
        this.next_station = next_station;
        this.distance = distance;
        this.station_name = station_name;
    }
}

public class Shortestpath {
    static ArrayList<Edge>[] metro_map;
    static Map<Integer, String> stations;
    static String sendString;

    public static int srcToDest(int cid, int src, int dest) {

        metro_map = new ArrayList[22];

        // initializing
        for (int stations = 0; stations <= 21; stations++) {
            metro_map[stations] = new ArrayList<>();
        }

        // U --> V
        metro_map[0].add(new Edge(1, 5, "Agrasen Square"));
        metro_map[1].add(new Edge(2, 3, "Airport"));
        metro_map[1].add(new Edge(8, 5, "Chhatrapati Square"));
        metro_map[2].add(new Edge(3, 2, "Airport South"));
        metro_map[8].add(new Edge(3, 7, "Airport South"));
        metro_map[3].add(new Edge(7, 8, "Bansi Nagar"));
        metro_map[7].add(new Edge(6, 2, "Automotive Square"));
        metro_map[3].add(new Edge(4, 9, "Ajni Square"));
        metro_map[4].add(new Edge(5, 5, "Ambedkar Square"));
        metro_map[5].add(new Edge(19, 12, "Khapri"));
        metro_map[19].add(new Edge(20, 2, "LAD Square"));
        metro_map[8].add(new Edge(9, 10, "Sitabuldi"));
        metro_map[9].add(new Edge(11, 7, "Dosar Vaishya Square"));
        metro_map[9].add(new Edge(10, 6, "Dharampeth College"));
        metro_map[10].add(new Edge(17, 10, "Kadvi Square"));
        metro_map[17].add(new Edge(18, 8, "Kasturchand Park"));
        metro_map[11].add(new Edge(12, 9, "Gaddi Godam Square"));
        metro_map[5].add(new Edge(12, 8, "Gaddi Godam Square"));
        metro_map[12].add(new Edge(13, 4, "Indora Square"));
        metro_map[13].add(new Edge(14, 2, "Institute of Engineers"));
        metro_map[14].add(new Edge(15, 8, "Jaiprakash Nagar"));
        metro_map[15].add(new Edge(16, 3, "Jhansi Rani Square"));
        // V --> U
        metro_map[1].add(new Edge(0, 5, "Nagpur Railway Station"));
        metro_map[2].add(new Edge(1, 3, "Agrasen Square"));
        metro_map[8].add(new Edge(1, 5, "Agrasen Square"));
        metro_map[3].add(new Edge(2, 2, "Airport"));
        metro_map[3].add(new Edge(8, 7, "Chhatrapati Square"));
        metro_map[7].add(new Edge(3, 8, "Airport South"));
        metro_map[6].add(new Edge(7, 2, "Bansi Nagar"));
        metro_map[4].add(new Edge(3, 9, "Airport South"));
        metro_map[5].add(new Edge(4, 5, "Ajni Square"));
        metro_map[19].add(new Edge(5, 12, "Ambedkar Square"));
        metro_map[20].add(new Edge(19, 2, "Khapri"));
        metro_map[9].add(new Edge(8, 10, "Chhatrapati Square"));
        metro_map[11].add(new Edge(9, 7, "Sitabuldi"));
        metro_map[10].add(new Edge(9, 6, "Sitabuldi"));
        metro_map[17].add(new Edge(10, 10, "Dharampeth College"));
        metro_map[18].add(new Edge(17, 8, "Kadvi Square"));
        metro_map[12].add(new Edge(11, 9, "Dosar Vaishya Square"));
        metro_map[12].add(new Edge(5, 8, "Ambedkar Square"));
        metro_map[13].add(new Edge(12, 4, "Gaddi Godam Square"));
        metro_map[14].add(new Edge(13, 2, "Indora Square"));
        metro_map[15].add(new Edge(14, 8, "Institute of Engineers"));
        metro_map[16].add(new Edge(15, 3, "Jaiprakash Nagar"));

        stations = new TreeMap();

        stations.put(0, "Nagpur Railway Station");
        stations.put(1, "Agrasen Square");
        stations.put(2, "Airport");
        stations.put(3, "Airport South");
        stations.put(4, "Ajni Square");
        stations.put(5, "Ambedkar Square");
        stations.put(6, "Automotive Square");
        stations.put(7, "Bansi Nagar");
        stations.put(8, "Chhatrapati Square");
        stations.put(9, "Sitabuldi");
        stations.put(10, "Dharampeth College");
        stations.put(11, "Dosar Vaishya Square");
        stations.put(12, "Gaddi Godam Square");
        stations.put(13, "Indora Square");
        stations.put(14, "Institute of Engineers");
        stations.put(15, "Jaiprakash Nagar");
        stations.put(16, "Jhansi Rani Square");
        stations.put(17, "Kadvi Square");
        stations.put(18, "Kasturchand Park");
        stations.put(19, "Khapri");
        stations.put(20, "LAD Square");

        ArrayList<String> path = new ArrayList<>();
        path.add(stations.get(dest));
        int minDistance = find_path(src, dest, metro_map, path);

        Stations.updateDistAndFair(stations.get(src), stations.get(dest), cid, minDistance);

        StringBuilder finalString = new StringBuilder();
        finalString.append("YOUR ROUTE FROM " + stations.get(src) + " TO " + stations.get(dest) + " IS : \n\n");

        for (int i = 0; i < path.size(); i++) {
            finalString.append(path.get(i));
        }
        sendString = finalString.toString();
        finalString.append("\n\nMINIMUM DISTANCE: " + minDistance + " km\n\n" + "FAIR FOR RIDE: Rs." + minDistance * 3);

        return minDistance;
    }
    private static int find_path(int source_station, int destination_station, ArrayList<Edge>[] metro_map, ArrayList<String> station_list) {
        PriorityQueue<int[]> min_queue = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] min_distance = new int[21];
        int[] parent = new int[21];
        Arrays.fill(min_distance, Integer.MAX_VALUE);
        min_distance[source_station] = 0;

        min_queue.add(new int[]{min_distance[source_station], source_station});
        while (min_queue.size() > 0) {
            int[] station_removed = min_queue.remove();
            int curr_distance = station_removed[0];
            int curr_station = station_removed[1];

            if (min_distance[curr_station] < curr_distance) {
                continue;
            }

            for (Edge e : metro_map[curr_station]) {
                int next_station = e.next_station;
                int next_distance = e.distance;

                if (min_distance[next_station] > min_distance[curr_station] + next_distance) {
                    min_distance[next_station] = min_distance[curr_station] + next_distance;
                    parent[next_station] = curr_station;
                    min_queue.add(new int[]{min_distance[next_station], next_station});
                }
            }
        }

        int ans = min_distance[destination_station];
        while (destination_station != source_station) {
            for (Edge e : metro_map[destination_station]) {
                if (parent[destination_station] == e.next_station) {
                    station_list.add(e.station_name + "  -->  ");
                }
            }
            destination_station = parent[destination_station];
        }
        Collections.reverse(station_list);
        return ans;
    }
}
