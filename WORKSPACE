load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.5"

RULES_JVM_EXTERNAL_SHA = "b17d7388feb9bfa7f2fa09031b32707df529f26c91ab9e5d909eb1676badd9a6"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

LOMBOK_RULES_VERSION = "0.0.2"

LOMBOK_RULES_SHA = "d28a6bb946be9780637df5b3e9acd12873ca8bbdceecd1f6d41859ac23a0c30b"

http_archive(
    name = "rules_lombok_java_library",
    sha256 = LOMBOK_RULES_SHA,
    strip_prefix = "rules_lombok_java_library-%s" % LOMBOK_RULES_VERSION,
    urls = ["https://github.com/bookingcom/rules_lombok_java_library/archive/refs/tags/v%s.tar.gz" % LOMBOK_RULES_VERSION],
)

# load("@rules_lombok_java_library//:deps.bzl", "lombok_java_library_dependencies")

# lombok_java_library_dependencies()

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "ch.qos.logback:logback-classic:1.4.5",
        "ch.qos.logback:logback-core:1.4.5",
        "com.fasterxml:classmate:1.5.1",
        "com.fasterxml.jackson.core:jackson-annotations:2.14.1",
        "com.fasterxml.jackson.core:jackson-core:2.14.1",
        "com.fasterxml.jackson.core:jackson-databind:2.14.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.14.1",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.1",
        "com.fasterxml.jackson.module:jackson-module-parameter-names:2.14.1",
        "com.jayway.jsonpath:json-path:2.7.0",
        "com.ongres.scram:client:2.1",
        "com.ongres.scram:common:2.1",
        "com.ongres.stringprep:saslprep:1.1",
        "com.ongres.stringprep:stringprep:1.1",
        "com.vaadin.external.google:android-json:0.0.20131108.vaadin1",
        "commons-codec:commons-codec:1.15",
        "io.micrometer:micrometer-commons:1.10.2",
        "io.micrometer:micrometer-core:1.10.2",
        "io.micrometer:micrometer-observation:1.10.2",
        "io.netty.incubator:netty-incubator-codec-classes-quic:0.0.33.Final",
        "io.netty.incubator:netty-incubator-codec-native-quic:0.0.33.Final",
        "io.netty:netty-buffer:4.1.85.Final",
        "io.netty:netty-codec:4.1.85.Final",
        "io.netty:netty-codec-dns:4.1.85.Final",
        "io.netty:netty-codec-http2:4.1.85.Final",
        "io.netty:netty-codec-http:4.1.85.Final",
        "io.netty:netty-codec-socks:4.1.85.Final",
        "io.netty:netty-common:4.1.85.Final",
        "io.netty:netty-handler:4.1.85.Final",
        "io.netty:netty-handler-proxy:4.1.85.Final",
        "io.netty:netty-resolver:4.1.85.Final",
        "io.netty:netty-resolver-dns:4.1.85.Final",
        "io.netty:netty-resolver-dns-classes-macos:4.1.85.Final",
        "io.netty:netty-resolver-dns-native-macos:4.1.85.Final",
        "io.netty:netty-transport:4.1.85.Final",
        "io.netty:netty-transport-classes-epoll:4.1.85.Final",
        "io.netty:netty-transport-native-epoll:4.1.85.Final",
        "io.netty:netty-transport-native-unix-common:4.1.85.Final",
        "io.projectreactor.addons:reactor-pool:1.0.0",
        "io.projectreactor.netty.incubator:reactor-netty-incubator-quic:0.1.0",
        "io.projectreactor.netty:reactor-netty:1.1.0",
        "io.projectreactor.netty:reactor-netty-core:1.1.0",
        "io.projectreactor.netty:reactor-netty-http:1.1.0",
        "io.projectreactor:reactor-core:3.5.0",
        "io.projectreactor:reactor-test:3.5.0",
        "io.r2dbc:r2dbc-pool:1.0.0.RELEASE",
        "io.r2dbc:r2dbc-postgresql:0.8.13.RELEASE",
        "io.r2dbc:r2dbc-spi:1.0.0.RELEASE",
        "jakarta.activation:jakarta.activation-api:2.1.0",
        "jakarta.annotation:jakarta.annotation-api:2.1.1",
        "jakarta.validation:jakarta.validation-api:3.0.2",
        "jakarta.xml.bind:jakarta.xml.bind-api:4.0.0",
        "net.bytebuddy:byte-buddy:1.12.19",
        "net.bytebuddy:byte-buddy-agent:1.12.19",
        "net.minidev:accessors-smart:2.4.8",
        "net.minidev:json-smart:2.4.8",
        "org.apache.logging.log4j:log4j-api:2.19.0",
        "org.apache.logging.log4j:log4j-to-slf4j:2.19.0",
        "org.apache.tomcat.embed:tomcat-embed-el:10.1.1",
        "org.apiguardian:apiguardian-api:1.1.2",
        "org.assertj:assertj-core:3.23.1",
        "org.hamcrest:hamcrest:2.2",
        "org.hdrhistogram:HdrHistogram:2.1.12",
        "org.hibernate.validator:hibernate-validator:8.0.0.Final",
        "org.jboss.logging:jboss-logging:3.5.0.Final",
        "org.junit.jupiter:junit-jupiter:5.9.1",
        "org.junit.jupiter:junit-jupiter-api:5.9.1",
        "org.junit.jupiter:junit-jupiter-engine:5.9.1",
        "org.junit.jupiter:junit-jupiter-params:5.9.1",
        "org.junit.platform:junit-platform-commons:1.9.1",
        "org.junit.platform:junit-platform-engine:1.9.1",
        "org.latencyutils:LatencyUtils:2.0.3",
        "org.mockito:mockito-core:4.8.1",
        "org.mockito:mockito-junit-jupiter:4.8.1",
        "org.objenesis:objenesis:3.2",
        "org.opentest4j:opentest4j:1.2.0",
        "org.ow2.asm:asm:9.1",
        "org.projectlombok:lombok:1.18.24",
        "org.reactivestreams:reactive-streams:1.0.4",
        "org.skyscreamer:jsonassert:1.5.1",
        "org.slf4j:jul-to-slf4j:2.0.4",
        "org.slf4j:slf4j-api:2.0.4",
        "org.springframework.boot:spring-boot:3.0.0",
        "org.springframework.boot:spring-boot-actuator:3.0.0",
        "org.springframework.boot:spring-boot-actuator-autoconfigure:3.0.0",
        "org.springframework.boot:spring-boot-autoconfigure:3.0.0",
        "org.springframework.boot:spring-boot-devtools:3.0.0",
        "org.springframework.boot:spring-boot-starter:3.0.0",
        "org.springframework.boot:spring-boot-starter-actuator:3.0.0",
        "org.springframework.boot:spring-boot-starter-data-r2dbc:3.0.0",
        "org.springframework.boot:spring-boot-starter-json:3.0.0",
        "org.springframework.boot:spring-boot-starter-logging:3.0.0",
        "org.springframework.boot:spring-boot-starter-reactor-netty:3.0.0",
        "org.springframework.boot:spring-boot-starter-test:3.0.0",
        "org.springframework.boot:spring-boot-starter-validation:3.0.0",
        "org.springframework.boot:spring-boot-starter-webflux:3.0.0",
        "org.springframework.boot:spring-boot-test:3.0.0",
        "org.springframework.boot:spring-boot-test-autoconfigure:3.0.0",
        "org.springframework.data:spring-data-commons:3.0.0",
        "org.springframework.data:spring-data-r2dbc:3.0.0",
        "org.springframework.data:spring-data-relational:3.0.0",
        "org.springframework:spring-aop:6.0.2",
        "org.springframework:spring-beans:6.0.2",
        "org.springframework:spring-context:6.0.2",
        "org.springframework:spring-core:6.0.2",
        "org.springframework:spring-expression:6.0.2",
        "org.springframework:spring-jcl:6.0.2",
        "org.springframework:spring-jdbc:6.0.2",
        "org.springframework:spring-r2dbc:6.0.2",
        "org.springframework:spring-test:6.0.2",
        "org.springframework:spring-tx:6.0.2",
        "org.springframework:spring-web:6.0.2",
        "org.springframework:spring-webflux:6.0.2",
        "org.xmlunit:xmlunit-core:2.9.0",
        "org.yaml:snakeyaml:1.33",
    ],
    repositories = [
        "m2Local",
        "https://repo1.maven.org/maven2",
        "https://repo.spring.io/snapshot",
        "https://repo.spring.io/milestone",
    ],
)
