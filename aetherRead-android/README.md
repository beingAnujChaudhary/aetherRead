# aetherRead Android

> **Phase 3 — Native Android App** · Status: 🟢 In Progress (Week 1 Completed)

Native Android PDF reader with Jetpack Compose, offline-first architecture, and cross-platform sync support.

## Links

- **Download APK**: [aetherRead-app-debug.apk](https://raw.githubusercontent.com/beingAnujChaudhary/aetherRead/main/aetherRead-android/apk/aetherRead-app-debug.apk)
- **Android Repo (MIT)**: [aetherRead-android](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-android)
- **Web Repo (MIT)**: [aetherRead-web](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-web)
- **Project Page**: [aetherRead Demo](https://beinganujchaudhary.web.app/projects/aetherRead.html)

## Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| PDF Engine | PdfRenderer |
| Local DB | Room + Hilt DI |
| Networking | Retrofit + OkHttp (Planned) |
| Layouts | WindowSizeClass (phone + tablet) (Planned) |
| Architecture | MVVM + Clean Architecture |

## Features (Phase 3)

- [x] Native PDF rendering with `PdfRenderer` and Jetpack Compose `LazyColumn`
- [x] Offline-first with Room DB (Recent PDFs saving)
- [x] Clean Architecture structure with Hilt DI
- [x] Edge-to-edge UI with custom Material 3 Dark Theme
- [ ] Tablet layout with `NavigationRail`
- [ ] Haptic feedback on annotations
- [ ] Adaptive icons + dynamic color (Android 12+)

## Project Structure

```text
aetherRead-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/atherread/
│   │   │   ├── AetherReadApplication.kt
│   │   │   ├── MainActivity.kt
│   │   │   ├── core/
│   │   │   │   ├── theme/          # Material 3 theme colors & types
│   │   │   │   ├── navigation/     # NavGraph & routes
│   │   │   │   ├── di/             # Hilt modules
│   │   │   │   ├── utils/          
│   │   │   │   ├── constants/      
│   │   │   │   └── extensions/     
│   │   │   ├── data/
│   │   │   │   ├── local/room/     # Room DAOs, entities, database
│   │   │   │   ├── repository/     # Repository implementations
│   │   │   │   └── models/         
│   │   │   ├── domain/
│   │   │   │   ├── models/         
│   │   │   │   ├── repository/     
│   │   │   │   └── usecases/       
│   │   │   └── features/
│   │   │       ├── home/           # Home Screen & ViewModel
│   │   │       ├── reader/         # PDF Reader Screen & ViewModel
│   │   │       ├── ai/             # AI Integration placeholders
│   │   │       └── (search, bookmarks, collections, settings, tools)
│   │   ├── res/
│   │   │   └── values/             
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   └── libs.versions.toml          # Version catalog
├── build.gradle.kts
└── settings.gradle.kts
```

## License

AGPL-3.0 — see [LICENSE](./LICENSE)

## Author

[**beingAnujChaudhary**](https://beinganujchaudhary.web.app/) · [beinganujchaudhary.web.app](https://beinganujchaudhary.web.app) · IIT Madras BS Data Science
