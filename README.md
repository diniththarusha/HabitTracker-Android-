# Personal Wellness Assistant 🌿

> An offline-first Android application designed to help users build healthier daily habits, track moods, and stay hydrated—all without relying on external servers.

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue.svg?style=for-the-badge)

## 📖 Overview

**The Personal Wellness App** is a native Android application developed using **Kotlin** and **Android Studio**. It acts as a comprehensive companion for personal well-being, focusing on privacy and local data ownership.

Unlike many health apps that require internet connectivity and cloud accounts, this application is architected to be **100% offline**. All data regarding habits, moods, and hydration logs are stored securely on the device, ensuring complete privacy.

---

## 📸 Screenshots

Here is a glimpse of the application's user interface:

| Home Dashboard | Habit Tracker | Mood & Hydration |
|:---:|:---:|:---:|
| <img src="screenshots/home.png" width="250" alt="Home Screen"> | <img src="screenshots/habits.png" width="250" alt="Habit Tracker"> | <img src="screenshots/mood.png" width="250" alt="Mood Tracker"> |


---

## 📱 Key Features

### 🧘 Habit Management
* **Custom Habit Creation:** Users can define and customize their own daily habits to build a healthier routine.
* **Daily Tracking:** Simple interface to check off habits as they are completed.
* **Routine Building:** Helps structure the day by organizing tasks and goals.

### 🎭 Mood Tracker
* **Daily Check-ins:** A quick and intuitive way for users to log their current emotional state.
* **Mood History:** Allows users to look back at previous entries to reflect on their well-being over time.

### 💧 Smart Hydration
* **Intake Logging:** Easily record water consumption throughout the day.
* **Smart Reminders:** Automated notifications tailored to remind users to drink water at optimal intervals.

### 🔒 Privacy & Architecture
* **100% Offline:** The app functions perfectly without an internet connection.
* **Local Storage Only:** No external databases or cloud servers are used. All personal data remains securely stored on the user's device.
* **Material Design:** Built with Android's native design principles for a clean and accessible user experience.

---

## 🛠️ Tech Stack

* **Language:** Kotlin
* **Development Environment:** Android Studio
* **User Interface:** XML & Material Design Components
* **Storage:** Local Data Persistence (Shared Prefs / Internal Storage)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Version Control:** Git & GitHub

---

## 💻 Getting Started

Follow these steps to get the project up and running on your local machine.

### Prerequisites
* **Android Studio** (Latest version recommended)
* **Android SDK** (API Level 26 or higher recommended)
* **Java Development Kit (JDK)** (Version 11 or higher)

### Installation

1. **Clone the repository**
   Open your terminal/command prompt and run:
   ```bash
   git clone [https://github.com/your-username/your-repo-name.git](https://github.com/your-username/your-repo-name.git)
   
2. **Open in Android Studio**

Launch Android Studio.

Select "Open" or "Open an Existing Project".

Navigate to the folder where you cloned the repository and select it.

3. **Sync Gradle**

Once the project opens, Android Studio should automatically start syncing the Gradle files.

If it doesn't, go to File > Sync Project with Gradle Files.

4. **Run the Application**

Connect your physical Android device via USB or create a Virtual Device (AVD).

Click the green Run button (▶️) in the toolbar or press Shift + F10.

**⚠️ Troubleshooting**
If you encounter build errors:

Clean Project: Go to ```Build > Clean Project.```

Rebuild Project: Go to ```Build > Rebuild Project.```

Invalid Cache: Go to ```File > Invalidate Caches / Restart.```

**👨‍💻 Author**

Dinith Tharusha
Undergraduate – Information Technology

Passionate about web and software development

**📄 License**

This project is for educational purposes.
