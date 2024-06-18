package dev.leoferreira.wishlist.test.config;

import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker;
import de.flapdoodle.reverse.transitions.Start;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedMongoConfig {

    @Bean(destroyMethod = "close")
    public TransitionWalker.ReachedState<RunningMongodProcess> mongodProcessReachedState() {
        return Mongod.builder()
                .net(Start.to(Net.class)
                        .initializedWith(Net.defaults().withPort(27018))
                )
                .build()
                .start(Version.Main.V4_4);
    }
}
