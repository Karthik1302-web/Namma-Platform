# Namma-Platform 🚉

**Namma-Platform** is a Kannada-first local railway station guide designed specifically for rural passengers at small stations in Karnataka. Built with **Kotlin** and **MVVM Architecture**, the app provides offline access to train schedules, platform information, and real-time route tracking.

---

## ✨ Features

- **Kannada-First UI**: Entirely localized interface for ease of use by rural passengers.
- **Offline Access**: Works completely without internet using a local JSON database (`trains.json`).
- **Professional TTS**: Integrated Kannada Text-To-Speech for station-style announcements ("ಗಮನಿಸಿ...").
- **Smart Station Selection**: Quick guide for major rural junctions like Hassan, Mysuru, Bengaluru City, and Mandya.
- **Interactive Route Tracking**: Timeline-style view of stops with "You are here" highlights.
- **Coach Sequence**: Color-coded horizontal guide to help passengers find their coaches (GEN, Ladies, Sleeper, etc.).

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Components**: RecyclerView, ConstraintLayout, CardView, Adaptive Icons.
- **Data Parsing**: Gson (for local JSON assets).
- **TTS**: Android TextToSpeech API (kn-IN Locale).
- **Minimum SDK**: API 28 (Android 9.0)

---

## 🎨 UI Design

- **Primary Colors**: Deep Navy (#0D1B2A) and Golden Yellow (#FFD700).
- **Iconography**: High-contrast icons for maps, time, and announcements.
- **Adaptive Icon**: Custom-designed light-blue icon for modern Android devices.

---

## 🚀 Installation & Testing

1. Clone the repository:
   ```bash
   git clone https://github.com/Karthik1302-web/Namma-Platform.git
   ```
2. Open the project in **Android Studio**.
3. Build and Run on an emulator or physical device.
4. **Testing Edge Cases**:
   - The app handles empty station data gracefully with custom messages.
   - Works perfectly in Airplane Mode.

---

## 🤝 Contributing

This project aims to bridge the digital gap for rural commuters. Suggestions for new stations or feature improvements are welcome!

---

*Developed with ❤️ for Karnataka.*
