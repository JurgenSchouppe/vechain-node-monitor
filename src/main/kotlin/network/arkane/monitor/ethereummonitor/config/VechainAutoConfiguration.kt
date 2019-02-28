package network.arkane.monitor.ethereummonitor.config

import network.arkane.provider.core.model.blockchain.NodeProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

@Configuration
class VechainAutoConfiguration {

    companion object Logging {
        val log = LoggerFactory.getLogger(javaClass)
    }

    constructor(@Value("\${network.arkane.vechain.endpoint.url}") vechainProvider: String) {
        log.info("starting the vechain provider: -> {}", vechainProvider)
        if (StringUtils.isEmpty(vechainProvider)) {
            throw IllegalArgumentException("Providing a vechain node is necessary (vechain.node)")
        }
        val nodeProvider = NodeProvider.getNodeProvider()
        nodeProvider.setProvider(vechainProvider)
        nodeProvider.setTimeout(10000)
    }
}