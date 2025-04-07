# 🍛 TajMahal – Android Restaurant Review App

**TajMahal** is an Android app allowing users to view and submit reviews for a restaurant.  
It features a clean UI, dynamic content, and review filtering, all built with **MVVM architecture**, **LiveData**, and **Hilt**.

---

## 📱 Features

- ✅ Display restaurant details (name, type, hours, address, etc.)
- 🌟 View list of user reviews with name, rating, comment & profile picture
- 📝 Submit a new review (with input validation)
- 📊 Average rating calculation & star distribution
- ⬆️ New reviews appear at the top of the list
- 🔁 LiveData updates the UI instantly
- ☕ Built with clean architecture principles

---

## 🎥 Live Demo – Add & View Reviews

Here's a quick look at the TajMahal app in action:

![TajMahal App Demo](demo.gif)

---

## 🧱 Tech Stack

| Tool/Library            | Purpose                            |
|-------------------------|------------------------------------|
| **Java**                | Main programming language          |
| **Android ViewModel**   | Lifecycle-aware UI logic           |
| **LiveData / MutableLiveData** | Data observation and reactivity |
| **RecyclerView**        | Review list rendering              |
| **Hilt (Dagger)**       | Dependency injection               |
| **Glide**               | Image loading                      |
| **JUnit**               | Unit testing                       |

---

## 🧪 Tests

- Unit tests for:
  - `Review` and `User` models
  - ViewModel logic
  - LiveData updates
  - Review validation
- Test reports generated in:
  ```
  app/build/reports/tests/testDebugUnitTest/index.html
  ```

---

## 🚀 Getting Started

### 📌 Prerequisites
- Android Studio Flamingo or higher
- Gradle 8+
- Emulator or Android 8.0+ device

### 🛠️ Build Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/tajmahal-app.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and Run the app.

---

## 🧑‍💻 Project Structure

```
com.openclassrooms.tajmahal
├── data
│   ├── repository
│   	├── RestaurantRepository
│   ├── service
│   	├── RestaurantApi
│     └── RestaurantFakeApi
├── di
│   └── AppModule
├── domain.model
│   ├── Restaurant
│   ├── Review
│   └── User
├── ui
│   ├── adapters
│   	└── ReviewsAdapter
│   ├── restaurant
│   	├── DetailsFragment
│   	├── DetailsReviewState
│   	└── DetailsViewModel
│   ├── review
│   	├── ReviewsFragment
│   	└── ReviewsViewModel
│   └── MainActivity
├── TajMahalApplication (DI entry point)
```

---

## 🧪 Example Test

```java
@Test
public void testAddReview_ShouldReflectInLiveData() {
    ReviewsViewModel viewModel = new ReviewsViewModel(new RestaurantRepository(new RestaurantFakeApi()));
    User user = new User("Bob", "0606060606", "bob@email.com", "url");
    viewModel.addReview("Excellent food!", 5, user);
    assertEquals("Bob", viewModel.getReviews().getValue().get(0).getUsername());
}
```

---

## 🙌 Author

Made by **Arno**  
_“Learning Android, one review at a time.”_

---

## 📄 License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
