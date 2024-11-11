package org.example


//list of the stations, what line they on, what stations you can go to
//we have all the Stations and the connections it has in a List.
// From this list I can get estimated time of arrival for A*
data class Station(
    val name: String,
    val line: String,
    val connections: List<Connections>
)

data class Connections(
    val station: Station,
    val estimatedTime: Int
)

object MetroMap{
    private val stations: MutableMap<String, Station> = mutableMapOf()

    init {
        //init stations
        // 3
        stations["Парк Победы-3"] = Station("Парк Победы", "3", emptyList())
        stations["Киевская-3"] = Station("Киевская","3", emptyList())
        stations["Смоленская-3"] = Station("Смоленская","3", emptyList())
        stations["Арбатская-3"] = Station("Арбатская","3",emptyList())
        stations["Площадь Революции-3"] = Station("Площадь Революции","3",emptyList())
        // 4
        stations["Кутузовская-4"] = Station("Кутузовская","4",emptyList())
        stations["Студенческая-4"] = Station("Студенческая","4",emptyList())
        stations["Киевская-4"] = Station("Киевская","4",emptyList())
        stations["Смоленская-4"] = Station("Смоленская","4",emptyList())
        stations["Арбатская-4"] = Station("Арбатская","4",emptyList())
        stations["Александровский Сад-4"] = Station("Александровский Сад","4",emptyList())
        //5
        stations["Краснопресненская-5"] = Station("Краснопресненская","5",emptyList())
        stations["Киевская-5"] = Station("Киевская","5",emptyList())
        stations["Парк Культуры-5"] = Station("Парк Культуры","5",emptyList())
        //init the connections between lines
        addConnection("Киевская-3","Киевская-4",5)
        addConnection("Киевская-3","Киевская-5",4)
        addConnection("Киевская-4","Киевская-5",5)
        addConnection("Арбатская-3", "Александровский Сад-4",2)
        //init the connections on the lines
        //3
        addConnection("Парк Победы-3","Киевская-4",5)
        addConnection("Киевская-3","Смоленской-3",2)
        addConnection("Смоленская-3","Арбатская-3",2)
        addConnection("Арбатская-3","Площадь Революции-3",2)
        //4
        addConnection("Кутузовская-4","Студенческая-4",3)
        addConnection("Студенческая-4","Киевская-4",4)
        addConnection("Киевская-4","Смоленская-4",4)
        addConnection("Смоленская-4","Арбатская-4",3)
        addConnection("Арбатская-4","Александровский Сад-4",5)
        //5
        addConnection("Краснопресненская-5","Киевская-5",3)
        addConnection("Киевская-5","Парк Культуры-5",3)
    }
    private fun addConnection(from:String, to:String, travelTime: Int){
        val fromStation = stations[from]
        val toStation = stations[to]
        if (fromStation != null && toStation != null){
            stations[from] = fromStation.copy(connections = fromStation.connections + Connections(toStation,travelTime))
            stations[to] = toStation.copy(connections = toStation.connections + Connections(fromStation,travelTime))
        }
    }
    fun getStation(name: String): Station? =stations[name]
    fun getAllStations(): List<Station> = stations.values.toList()
}
fun main() {

}