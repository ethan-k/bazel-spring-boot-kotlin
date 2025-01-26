load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary", "kt_jvm_library")
load("@rules_kotlin//kotlin:core.bzl", "kt_kotlinc_options", "define_kt_toolchain", "kt_compiler_plugin")
load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar")
load("@aspect_bazel_lib//lib:tar.bzl", "tar")
load("@rules_oci//oci:defs.bzl", "oci_image", "oci_push", "oci_load")
load("@rules_java//java:defs.bzl", "java_binary")


package(default_visibility = ["//visibility:public"])

kt_compiler_plugin(
    name = "allopen_plugin",
    compile_phase = True,
    id = "org.jetbrains.kotlin.allopen",
    options = {
         "preset": "spring",
    },
    stubs_phase = True,
    deps = [
        "@rules_kotlin//kotlin/compiler:allopen-compiler-plugin",
    ],
)

kt_compiler_plugin(
    name = "no_arg_plugin",
    compile_phase = True,
    id = "org.jetbrains.kotlin.noarg",
    options = {
        "annotation": "plugin.noarg.NoArgConstructor",
    },
    stubs_phase = True,
    deps = [
        "@rules_kotlin//kotlin/compiler:noarg-compiler-plugin",
    ],
)

kt_kotlinc_options(
    name = "kt_kotlinc_options",
    jvm_target = "17",
    x_jsr_305 = "strict",
    x_enable_incremental_compilation = True,
)

kt_jvm_library(
    name = "demoapp-lib",
    srcs = glob(["src/main/kotlin/**/*.kt"]),
    resources = glob(["src/main/resources/**"]),
    deps = [
        "@maven//:org_jetbrains_kotlin_kotlin_reflect",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_starter_web",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_boot_spring_boot_starter_websocket",
    ],
    visibility = ["//visibility:public"],
    plugins = [":allopen_plugin", ":no_arg_plugin"],
    kotlinc_opts = ":kt_kotlinc_options",
)

# This will create the fat JAR
java_binary(
    name = "demoapp",
    main_class = "com.example.demo.ApplicationKt",
    runtime_deps = [":demoapp-lib"],
    deploy_manifest_lines = [
        "Main-Class: com.example.demo.ApplicationKt",
    ],
)

tar(
    name = "layer",
    srcs = [":demoapp_deploy.jar"],
)

oci_image(
    name = "image",
    base = "@amazon_corretto_arm64",
    entrypoint = [
        "java",
        "-jar",
        "services/demoapp/demoapp_deploy.jar",  
    ],
    tars = [":layer"],
    exposed_ports = ["8080"],
)

oci_push(
    name = "push_local",
    image = ":image",
    repository = "localhost:5001/demoapp",
        remote_tags = ["latest"]
)

oci_load(
    name = "load",
    image = ":image",
    repo_tags = ["localhost:5001/demoapp:latest"],
)
