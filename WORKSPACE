load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "6.6"
RULES_JVM_EXTERNAL_SHA = "3afe5195069bd379373528899c03a3072f568d33bd96fe037bd43b1f590535e7"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazel-contrib/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG)
)


http_archive(
    name = "rules_kotlin",
    sha256 = "dd32f19e73c70f32ccb9a166c615c0ca4aed8e27e72c4a6330c3523eafa1aa55",
    url = "https://github.com/bazelbuild/rules_kotlin/releases/download/v2.1.0/rules_kotlin-v2.1.0.tar.gz",
)

http_archive(
    name = "rules_oci",
    sha256 = "1bd16e455278d523f01326e0c3964cd64d7840a7e99cdd6e2617e59f698f3504",
    strip_prefix = "rules_oci-2.2.0",
    url = "https://github.com/bazel-contrib/rules_oci/releases/download/v2.2.0/rules_oci-v2.2.0.tar.gz",
)

###################################
# rules_jvm_external for Maven deps
###################################

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")
rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")
rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")


# ###################################
# # Kotlin rules
# ###################################

load("@rules_kotlin//kotlin:repositories.bzl", "kotlin_repositories")

kotlin_repositories()

load("@rules_kotlin//kotlin:core.bzl", "kt_register_toolchains")

kt_register_toolchains()

KOTLIN_VERSION = "2.1.0"
SPRING_BOOT_VERSION = "3.4.2"

maven_install(
    artifacts = [
        "org.jetbrains.kotlin:kotlin-reflect:%s" % KOTLIN_VERSION,
        "org.springframework.boot:spring-boot-starter-web:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-reactor-netty:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-websocket:%s" % SPRING_BOOT_VERSION,
        "org.jetbrains.kotlin:kotlin-stdlib:%s" % KOTLIN_VERSION,
        "org.jetbrains.kotlin.plugin.allopen:org.jetbrains.kotlin.plugin.allopen.gradle.plugin:%s" % KOTLIN_VERSION,
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)

# ###################################
# # OCI rules
# ###################################

load("@rules_oci//oci:dependencies.bzl", "rules_oci_dependencies")

rules_oci_dependencies()

load("@rules_oci//oci:repositories.bzl", "oci_register_toolchains")

oci_register_toolchains(name = "oci")

load("@rules_oci//oci:pull.bzl", "oci_pull")

# oci_pull(
#     name = "distroless_java",
#     image = "gcr.io/distroless/java17",
#     tag = "latest"
# )

oci_pull(
    name = "amazon_corretto",
    image = "amazoncorretto",
    digest = "sha256:e519450563825985ba7d3e91ca1c4cfd01fb810ca38763bb1ad131203bc21ab3",
)


oci_pull(
    name = "amazon_corretto_arm64",
    image = "amazoncorretto",
    digest = "sha256:91567c85a4e7081d804298ce45e1c01ce013e879639c07a52df8030814e8d7cd",
)