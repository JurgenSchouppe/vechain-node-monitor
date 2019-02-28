package network.arkane.monitor.ethereummonitor

import network.arkane.provider.clients.BlockClient
import network.arkane.provider.core.model.blockchain.Block
import network.arkane.provider.core.model.clients.Revision
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.Status
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class VechainNodeHealthIndicator : AbstractReactiveHealthIndicator() {

    private val latestBlock: Optional<Block>
        get() = try {
            Optional.ofNullable(
                    BlockClient.getBlock(Revision.BEST))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Optional.empty()
        }

    override fun doHealthCheck(builder: Health.Builder): Mono<Health> {
        try {
            val block = latestBlock
            if (block.isPresent) {
                val date = Date(block.get().timestamp * 1000)
                val blockTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                return if (blockTime.plus(10, ChronoUnit.MINUTES).isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
                    Mono.just(builder.status(Status.DOWN)
                            .withDetail("vechainnode", "last block is older than 30 minutes")
                            .build())
                } else {
                    block.map {
                        Mono.just(builder.up().withDetail("vechainnode", "latest block is " + it.number).build())
                    }.get()
                }
            } else {
                return Mono.just(
                        builder.down()
                                .withDetail("vechainnode.down", "Unable to fetch status for ethereum node").build())
            }
        } catch (ex: Exception) {
            return Mono.just(
                    builder.down()
                            .withDetail("vechainnode.exception", ex.message).build())
        }
    }
}