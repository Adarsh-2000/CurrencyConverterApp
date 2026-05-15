# CurrencyConverterApp 💱

A modern Android Currency Converter application built using **Clean Architecture + MVVM** with **Jetpack Compose**.
The app provides real-time currency conversion with a clean UI, offline support, and scalable architecture.

---

# 📱 Features

* 🌍 Real-time currency conversion
* 🔄 Convert between multiple currencies
* 📡 API integration for live exchange rates
* 🧠 MVVM Architecture
* 🧱 Clean Architecture
* 🎨 Jetpack Compose UI
* ⚡ Kotlin Coroutines & Flow
* 💉 Dependency Injection
* 🗂️ Repository Pattern
* 📦 Modular & scalable project structure
* 🔁 Background workers support
* 🌙 Modern Material UI

---

# 🏗️ Architecture

This project follows:

* **Clean Architecture**
* **MVVM Pattern**
* **Repository Pattern**

### Architecture Layers

```text
presentation -> domain -> data
```

### Explanation

#### Presentation Layer

Contains:

* UI Screens
* ViewModels
* UI State
* Compose Components

#### Domain Layer

Contains:

* UseCases
* Repository Interfaces
* Business Logic
* Domain Models

#### Data Layer

Contains:

* API Services
* DTOs
* Repository Implementations
* Local/Remote Data Sources

---

# 📂 Project Structure

```text
CurrencyConverterApp2/
│
├── data/
│   ├── remote/
│   ├── repository/
│   └── mapper/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
│
├── presentation/
│   ├── screens/
│   ├── components/
│   ├── state/
│   └── viewModel/
│
├── ui/
├── worker/
├── common/
├── utils/
│
├── CurrencyApplication.kt
├── MainActivity.kt
└── CurrencyConverterScreen.kt
```

---

# 🛠️ Tech Stack

| Technology         | Usage                     |
| ------------------ | ------------------------- |
| Kotlin             | Main Programming Language |
| Jetpack Compose    | UI Development            |
| MVVM               | Architecture Pattern      |
| Clean Architecture | Project Structure         |
| Coroutines & Flow  | Asynchronous Programming  |
| Retrofit           | API Calls                 |
| Hilt/Dagger        | Dependency Injection      |
| Room Database      | Local Storage             |
| WorkManager        | Background Tasks          |
| Material 3         | UI Components             |

---

# 🧪 Future Improvements

* 📈 Currency trend charts
* ⭐ Favorite currencies
* 🌐 Offline caching
* 🔔 Exchange rate alerts
* 🌎 Multi-language support
* 📊 Historical exchange rates
