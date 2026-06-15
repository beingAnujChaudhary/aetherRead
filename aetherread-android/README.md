# aetherRead Android

> **Phase 3 — Native Android App** · Status: 🔜 Upcoming

Native Android PDF reader with Jetpack Compose, offline-first architecture, and cross-platform sync support.

## Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| PDF Engine | AndroidPdfViewer / PdfRenderer |
| Local DB | Room + Hilt DI |
| Networking | Retrofit + OkHttp |
| Layouts | WindowSizeClass (phone + tablet) |
| Architecture | MVVM + Clean Architecture |

## Planned Features (Phase 3)

- [ ] Native PDF rendering with memory safety
- [ ] Tablet layout with `NavigationRail`
- [ ] Offline-first with Room DB
- [ ] Haptic feedback on annotations
- [ ] Edge-to-edge UI with `WindowInsets`
- [ ] Adaptive icons + dynamic color (Android 12+)

## Project Structure (Planned)

```
aetherread-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/beinganujchaudhary/aetherread/
│   │   │   ├── aetherReadApplication.kt
│   │   │   ├── MainActivity.kt
│   │   │   ├── data/
│   │   │   │   ├── local/          # Room DAO, entities, database
│   │   │   │   ├── remote/         # Retrofit API client
│   │   │   │   └── repository/     # Repository implementations
│   │   │   ├── domain/
│   │   │   │   ├── model/          # Domain models
│   │   │   │   ├── repository/     # Repository interfaces
│   │   │   │   └── usecase/        # Business logic use cases
│   │   │   └── ui/
│   │   │       ├── library/        # Library screen + ViewModel
│   │   │       ├── reader/         # PDF reader screen + ViewModel
│   │   │       ├── annotation/     # Annotation composables
│   │   │       ├── theme/          # Material 3 theme + ComfortEngine
│   │   │       └── navigation/     # NavGraph + destinations
│   │   ├── res/
│   │   │   ├── values/             # Strings, colors, styles
│   │   │   └── drawable/           # Icons, vector assets
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   └── libs.versions.toml          # Version catalog
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## License

AGPL-3.0 — see [LICENSE](./LICENSE)

## Author

**Anuj Chaudhary** · [beinganujchaudhary.web.app](https://beinganujchaudhary.web.app) · IIT Madras BS Data Science
