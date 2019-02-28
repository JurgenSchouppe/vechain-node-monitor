package network.arkane.monitor.ethereummonitor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EthereummonitorApplication

fun main(args: Array<String>) {
	runApplication<EthereummonitorApplication>(*args)
}
