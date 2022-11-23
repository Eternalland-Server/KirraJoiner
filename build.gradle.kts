plugins {
    java
    id("io.izzel.taboolib") version "1.34"
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
}

taboolib {
    description {
        dependencies {
            name("KirraCoreBukkit")
        }
    }
    install("common")
    install("common-5")
    install("module-lang")
    install("module-configuration")
    install("module-chat")
    install("module-database")
    install("platform-bukkit")
    install("module-nms")
    install("module-nms-util")
    install("module-ui-receptacle")
    install("expansion-command-helper")
    classifier = null
    version = "6.0.8-9"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.sakuragame:DataManager-Bukkit-API:1.3.2-SNAPSHOT@jar")
    compileOnly("net.sakuragame:DungeonSystem-Client-API:1.1.3-SNAPSHOT@jar")
    compileOnly("net.sakuragame.eternal:KirraCore-Bukkit:2.0.0-SNAPSHOT@jar")
    compileOnly("net.sakuragame.eternal:JustMessage:1.0.4-SNAPSHOT@jar")
    compileOnly("com.taylorswiftcn:UIFactory:1.0.2-SNAPSHOT@jar")
    compileOnly("ink.ptms.core:v11200:11200")
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