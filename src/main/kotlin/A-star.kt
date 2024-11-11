package org.example

import java.util.*

data class PathNode(
    val station: Station,
    val g: Int,  // Cost from start to the current station
    val h: Int,  // Estimated cost from current station to the goal
    val parent: PathNode? = null // For path reconstruction
) : Comparable<PathNode> {
    val f: Int get() = g + h

    override fun compareTo(other: PathNode): Int = f - other.f
}

fun aStarPathfinding(start: Station, goal: Station): List<Station> {
    val openSet = PriorityQueue<PathNode>()
    val closedSet = mutableSetOf<Station>()
    val gScores = mutableMapOf<Station, Int>().withDefault { Int.MAX_VALUE }
    val cameFrom = mutableMapOf<Station, PathNode>()

    gScores[start] = 0
    openSet.add(PathNode(start, 0, estimateHeuristic(start, goal)))

    while (openSet.isNotEmpty()) {
        val currentNode = openSet.poll()

        // Debugging: Check if goal is reached
        if (currentNode.station == goal) {
            println("Goal reached: ${currentNode.station.name}")
            return reconstructPath(currentNode)
        }

        closedSet.add(currentNode.station)

        for (connection in currentNode.station.connections) {
            val neighbor = connection.station
            if (neighbor in closedSet) continue

            val tentativeGScore = currentNode.g + connection.estimatedTime

            //debug
            // Debugging current station
            println("Visiting station: ${currentNode.station.name}, Line: ${currentNode.station.line}")

            // Debugging neighbors of the current station
            println("Neighbors of ${currentNode.station.name}:")
            for (connection in currentNode.station.connections) {
                println(" - ${connection.station.name} (Line: ${connection.station.line}, Time: ${connection.estimatedTime})")
            }

            // Debugging gScores
            println("Tentative G Score for ${neighbor.name}: $tentativeGScore (Current G Score: ${gScores.getValue(neighbor)})")

            //end of debug
            if (tentativeGScore < gScores.getValue(neighbor)) {
                gScores[neighbor] = tentativeGScore
                val hScore = estimateHeuristic(neighbor, goal)
                val neighborNode = PathNode(neighbor, tentativeGScore, hScore, currentNode)
                openSet.add(neighborNode)
                cameFrom[neighbor] = neighborNode
            }
        }
    }

    // If no path found, print this message
    println("No path found.")
    return emptyList() // No path found
}


fun estimateHeuristic(current: Station, goal: Station): Int {
    return if (current.connections.any { it.station == goal }) 0 else 1
}


fun reconstructPath(node: PathNode): List<Station> {
    val path = mutableListOf<Station>()
    var currentNode: PathNode? = node
    while (currentNode != null) {
        path.add(currentNode.station)
        currentNode = currentNode.parent
    }
    return path.reversed() // Reverse the path to get start-to-goal order
}
