package homeworks.homework1.task1

data class NetworkData(
    val computersData: MutableList<ComputerNetworkData>,
    val viruses: MutableSet<Virus>
)
