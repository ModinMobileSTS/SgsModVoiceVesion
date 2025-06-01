plugins {
    id("com.android.application")
}

android {
    namespace = "template.mod"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        versionName = "1.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        release {
            @Suppress("UnstableApiUsage")
            vcsInfo.include = false
        }
    }

    applicationVariants.all {
        val outputFileName = "三国杀Mods手机版-$versionName.jar"
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName = outputFileName
        }
    }
}

dependencies {
    compileOnly(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    //noinspection GradleDependency
    compileOnly("com.badlogicgames.gdx:gdx:1.9.10")
}
