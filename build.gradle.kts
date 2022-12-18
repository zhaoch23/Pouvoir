plugins {
    `java-library`
    id("org.gradle.java")
    id("io.izzel.taboolib") version "1.51"
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"
}

tasks.dokkaJavadoc.configure {
    outputDirectory.set(File("C:\\Users\\Administrator\\Desktop\\Doc\\pouvoir"))
    suppressObviousFunctions.set(false)
    suppressInheritedMembers.set(true)
}
val code: String? by project
val special = arrayOf("release", "SNAPSHOT", "alpha", "beta", "rc", "gamma")

task("versionPlus") {
    val file = file("version.properties")
    val properties = org.jetbrains.kotlin.konan.properties.loadProperties(file.path)
    val subVersionStr = properties.getProperty("subVersion").toString()
    var subVersion = 0
    if (subVersionStr !in special) subVersion = subVersionStr.toInt()
    if (code == null) {
        properties["subVersion"] = (++subVersion).toString()
        properties.store(file.outputStream(), null)
    }
    project.version = project.version.toString() + "-$subVersion"
}
task("buildCode") {
    if (code == null) return@task
    val origin = project.version.toString()
    project.version = "$origin-api"
}

taboolib {
    if (project.version.toString().contains("-api")) {
        options("skip-kotlin-relocate")
    }
    description {
        contributors {
            name("Glom_")
        }
        dependencies {
            name("PlaceholderAPI").optional(true).loadafter(true)
            name("RandomItem").optional(true).loadbefore(true)
            name("AttributeSystem").optional(true).loadbefore(true)
            name("MythicMobs").optional(true).loadafter(true)
            name("DecentHolograms").optional(true)
            name("Adyeshach").optional(true)
        }
    }

    install(
        "common",
        "common-5",
        "module-ai",
        "module-chat",
        "module-configuration",
        "module-database",
        "module-database-mongodb",
        "module-effect",
        "module-kether",
        "module-metrics",
        "module-navigation",
        "module-nms",
        "module-lang",
        "module-porticus",
        "module-nms-util",
        "module-ui",
        "module-ui-receptacle",
        "expansion-javascript"
    )
    install(
        "platform-bukkit"
    )


    classifier = null
    version = "6.0.10-31"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.6.10")
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11901:11901-minimize:mapped")
    compileOnly("org.codehaus.groovy:groovy-jsr223:3.0.11")
    compileOnly("ink.ptms.core:v11200:11200-minimize")
    compileOnly("com.google.code.gson:gson:2.9.0")

    compileOnly("public:MythicMobs5:5.0.4")
    compileOnly("public:MythicMobs:1.0.1")
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


