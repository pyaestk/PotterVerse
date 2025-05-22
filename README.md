<h1>🧙‍♂️ Potter Verse Android App</h1>

<p><strong>Potter Verse</strong> is an Android application that provides users with detailed information about the Harry Potter universe. The app allows users to explore and discover released <strong>movies</strong>, <strong>books</strong>, and various <strong>characters</strong> from the magical world created by J.K. Rowling.</p>

<h2>✨ Features</h2>
<ol>
  <li>
    <strong>Home Screen with Tab Navigation:</strong> 
    The app opens with a top tab bar that lets users quickly navigate between three main categories: 
    <em>Movies</em>, <em>Books</em>, and <em>Characters</em>. Each tab presents a scrollable list of items fetched from the PotterDB API.
  </li>

  <li>
    <strong>Detail Pages:</strong> 
    Tapping on any movie, book, or character takes the user to a detail screen that displays more in-depth information such as release date, summary, traits, and more.
  </li>

  <li>
    <strong>"More Information" Button:</strong> 
    On each detail page, there is a button that opens the official Fandom page for that item in a web browser, providing extended lore and content beyond the app.
  </li>

  <li>
    <strong>Bookmarking:</strong> 
    Users can bookmark any movie, book, or character from the detail screen to save them for later. This enables quick access to favorite or important items.
  </li>

  <li>
    <strong>Bookmark Screen:</strong> 
    Accessible from the app's top menu or navigation bar, this screen displays all bookmarked items categorized by type. Users can unbookmark individual entries or clear all bookmarks at once.
  </li>

  <li>
    <strong>Search Functionality:</strong> 
    A search bar allows users to look up specific items. Search is dynamic and displays real-time results.
  </li>

  <li>
    <strong>Filtering Search Results:</strong> 
    Users can apply filters to limit search results to only <em>Books</em>, <em>Movies</em>, or <em>Characters</em>, making it easier to find relevant content quickly.
  </li>
</ol>


<h2>🛠️ Technologies Used</h2>
<table>
  <thead>
    <tr>
      <th>Tool</th>
      <th>Purpose</th>
    </tr>
  </thead>
  <tbody>
    <tr><td><strong>Kotlin</strong></td><td>Primary programming language</td></tr>
    <tr><td><strong>MVVM Architecture</strong></td><td>Separates UI, business logic, and data for maintainability</td></tr>
    <tr><td><strong>Retrofit</strong></td><td>For networking and API communication</td></tr>
    <tr><td><strong>Coroutines</strong></td><td>Asynchronous background tasks</td></tr>
    <tr><td><strong>ViewModel & LiveData</strong></td><td>For lifecycle-aware UI updates</td></tr>
    <tr><td><strong>RecyclerView</strong></td><td>Displays lists for movies, books, and characters</td></tr>
    <tr><td><strong>View Binding</strong></td><td>Safer and cleaner view access</td></tr>
    <tr><td><strong>Koin</strong></td><td>Dependency Injection for managing app components</td></tr>
  </tbody>
</table>


<h2>🔌 API</h2>
<p>The app uses the <a href="https://docs.potterdb.com" target="_blank"><strong>PotterDB API</strong></a> to fetch live data on:</p>
<ul>
  <li>Books</li>
  <li>Movies</li>
  <li>Characters</li>
</ul>

<h2>📲 Setup Instructions</h2>
<ol>
  <li><strong>Clone the repository:</strong>
    <pre><code>git clone https://github.com/pyaestk/PotterVerse.git</code></pre>
  </li>
  <li><strong>Open in Android Studio:</strong>
    <ul>
      <li>Open Android Studio.</li>
      <li>Click on "Open an Existing Project" and select the <code>PotterVerse</code> directory.</li>
    </ul>
  </li>
  <li><strong>Build the project:</strong>
    <ul>
      <li>Make sure you have the latest Android SDK and Kotlin plugin installed.</li>
      <li>Let Gradle sync and download dependencies.</li>
    </ul>
  </li>
  <li><strong>Run the app:</strong>
    <ul>
      <li>Connect an Android device or use an emulator.</li>
      <li>Click the <strong>Run</strong> button or use <code>Shift + F10</code>.</li>
    </ul>
  </li>
</ol>

<h2>📁 Project Structure</h2>
<pre><code>PotterVerse/
├── data/           # Network models, API service, repository
├── ui/             # Activities, Fragments, Adapters
├── viewmodel/      # ViewModels for UI
├── utils/          # Helper classes (e.g. Constants, Extensions)
└── model/          # Data classes
</code></pre>

<h2>📎 GitHub Repository</h2>
<p>Check out the full source code:  
👉 <a href="https://github.com/pyaestk/PotterVerse" target="_blank"><strong>https://github.com/pyaestk/PotterVerse</strong></a></p>
