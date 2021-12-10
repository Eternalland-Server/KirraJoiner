plugins {
    java
    id("io.izzel.taboolib") version "1.31"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    description {
        dependencies {
            name("KirraCoreBukkit")
        }
    }
    install("common")
    install("common-5")
    install("platform-bukkit")
    classifier = null
    version = "6.0.5-9"
}

repositories {
    maven {
        credentials {
            username = "a5phyxia"
            password = "zxzbc13456"
        }
        url = uri("https://maven.ycraft.cn/repository/maven-snapshots/")
    }
    mavenCentral()
}

dependencies {
    compileOnly("net.sakuragame.eternal:KirraCore-Bukkit:1.0.9-SNAPSHOT@jar")
    compileOnly("ink.ptms.core:v11200:11200:all@jar")
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}