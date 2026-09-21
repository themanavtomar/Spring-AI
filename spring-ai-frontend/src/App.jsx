import { useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Header from "./components/Header";
import ChatArea from "./components/ChatArea";
import MessageInput from "./components/MessageInput";
import LoginPage from "./components/LoginPage";
import { sendMessageToBackend } from "./services/axios";
import "./index.css";

const ChatInterface = ({ user, onLogout }) => {
  const [messages, setMessages] = useState([
    {
      text: `Hello ${user?.name || "there"}! How can I help you today?`,
      sender: "ai",
      modelUsed: "System",
    },
  ]);
  const [useLocalModel, setUseLocalModel] = useState(true);
  const [isLoading, setIsLoading] = useState(false);

  const handleSendMessage = async (userText) => {
    setMessages((prev) => [...prev, { text: userText, sender: "user" }]);
    setIsLoading(true);

    const aiResponseText = await sendMessageToBackend(
      userText,
      useLocalModel,
      user?.email,
    );

    setMessages((prev) => [
      ...prev,
      {
        text: aiResponseText,
        sender: "ai",
        modelUsed: useLocalModel ? "DeepSeek (Local)" : "Gemini (Cloud)",
      },
    ]);
    setIsLoading(false);
  };

  return (
    <div className="app-container">
      <Header
        useLocalModel={useLocalModel}
        setUseLocalModel={setUseLocalModel}
        user={user}
        onLogout={onLogout}
      />
      <main className="chat-container">
        <ChatArea messages={messages} isLoading={isLoading} />
        <MessageInput onSendMessage={handleSendMessage} isLoading={isLoading} />
      </main>
    </div>
  );
};

export default function App() {
  const [user, setUser] = useState(() => {
    const params = new URLSearchParams(window.location.search);
    const email = params.get("email");
    const name = params.get("name");

    if (email) {
      const oauthUser = {
        name: name || email.split("@")[0],
        email: email,
        profession: "Software Engineer",
        company: "Enterprise",
        age: 21,
        avatar: `https://api.dicebear.com/7.x/avataaars/svg?seed=${name || email}`,
      };
      localStorage.setItem("chat_user", JSON.stringify(oauthUser));
      window.history.replaceState({}, document.title, window.location.pathname);
      return oauthUser;
    }

    const saved = localStorage.getItem("chat_user");
    return saved ? JSON.parse(saved) : null;
  });

  const handleLoginSuccess = (userData) => {
    setUser(userData);
    localStorage.setItem("chat_user", JSON.stringify(userData));
  };

  const handleLogout = () => {
    localStorage.removeItem("chat_user");
    setUser(null);
    window.location.href = "/login";
  };

  return (
    <Router>
      <Routes>
        <Route
          path="/login"
          element={
            user ? (
              <Navigate to="/" replace />
            ) : (
              <LoginPage onLoginSuccess={handleLoginSuccess} />
            )
          }
        />
        <Route
          path="/"
          element={
            user ? (
              <ChatInterface user={user} onLogout={handleLogout} />
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
      </Routes>
    </Router>
  );
}
