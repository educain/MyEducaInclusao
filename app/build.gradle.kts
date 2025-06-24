plugins {
    alias(libs.plugins.kotlin.android)
    id("com.android.application")
    // Adicione esta linha: // Este comentário parece se referir à linha abaixo dele
    id("com.google.gms.google-services")
}

android {
    compileSdk = 35 // <<< IMPORTANTE: Adicione ou verifique esta linha. Use o nível de API desejado.
    namespace = "com.example.myeducainclusao" // É bom ter isso definido também.

    defaultConfig {
        applicationId = "com.example.myeducainclusao"
        minSdk = 24 // Ou a sua minSdk desejada
        targetSdk = 34 // Geralmente igual ao compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        android {
            // ...
            buildFeatures {
                viewBinding = true
            }
        }
        // ...
    }
    // It's good practice to add these for Kotlin projects
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
} // This closing brace for the 'android' block was likely where the parser expected a '}' if dependencies were misplaced.

dependencies {
    implementation(libs.androidx.navigation.fragment) // This was outside the main dependencies block initially
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics-ktx") // Versão gerenciada pelo BoM
    implementation("com.google.firebase:firebase-auth-ktx")    // Versão gerenciada pelo BoM
    implementation("com.google.firebase:firebase-firestore-ktx") // Versão gerenciada pelo BoM
    dependencies {
        // ... outras dependências

        // Dependências do Jetpack Compose
        implementation("androidx.compose.ui:ui:1.6.0") // Substitua pela versão mais recente
        implementation("androidx.compose.material3:material3:1.2.0") // Ou material, se estiver usando Material 2. Substitua pela versão mais recente
        implementation("androidx.compose.ui:ui-tooling-preview:1.6.0") // Substitua pela versão mais recente
        implementation("androidx.compose.foundation:foundation:1.6.0") // ESSENCIAL: Substitua pela versão mais recente

        // Para integração com Activities
        implementation("androidx.activity:activity-compose:1.9.0") // Substitua pela versão mais recente

        // Para integração com ViewModels
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0") // Substitua pela versão mais recente


        // Outras dependências do seu projeto...
        // implementation(libs.androidx.navigation.fragment) // Já presente no seu arquivo
        // ...
    }
    // ... outras dependências ...
    implementation("androidx.core:core-ktx:1.16.0") // Corrected version format
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0") // Para componentes de UI do Material Design
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // Para ViewModel
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0") // Para Navigation Component //Ensure this version is compatible, 2.7.7 is common
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation(libs.support.annotations)
    implementation(libs.androidx.runtime.android)
    implementation(libs.firebase.database)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit.junit)      // Para Navigation Component // This was duplicated
}