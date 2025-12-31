# Employee List (Jetpack Compose + Hilt + Clean Architecture)

This module implements an Employee List feature using **Jetpack Compose**, **Hilt**, **Kotlin Flow/StateFlow**, and a simple **ViewState architecture** with support for:
- Full-screen **loading**
- Full-screen **empty**
- Full-screen **error + retry**
- Loaded list with **Material3 Pull-to-Refresh**

---

## ✨ Features
### UI
- Employee list screen built with **Jetpack Compose**
- Employee row item:
  - Rounded **Coil** image
  - Name + team as two vertical texts
- List rendering using **LazyColumn**
- `PullToRefreshBox` (Material3) to refresh the list when loaded

### Architecture
- Clean separation of layers:
  - **DataSource**: provides employee list responses
  - **UseCase**: maps response → domain list safely
  - **ViewModel**: converts Flow results into a single `StateFlow<EmployeeListViewState>`
  - **UI**: consumes ViewState only (stateless `Screen`)

### State management
- **Single source of truth** via `StateFlow<EmployeeListViewState>`
- Refresh/retry implemented with a **SharedFlow trigger**
- Lifecycle-safe collection in Compose using `collectAsStateWithLifecycle()`

---
