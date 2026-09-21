import { useState } from "react";
import { loginUser, registerUser } from "../services/axios";

export default function LoginPage({ onLoginSuccess }) {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
  });
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      let userData;
      if (isLogin) {
        userData = await loginUser(formData.email, formData.password);
      } else {
        userData = await registerUser(
          formData.name,
          formData.email,
          formData.password,
        );
      }
      onLoginSuccess(userData);
    } catch (err) {
      setError(err.response?.data?.message || "Authentication failed");
    }
  };

  const handleOAuth = (provider) => {
    // Direct browser navigation to Spring Security OAuth endpoint
    window.location.href = `${import.meta.env.VITE_API_URL}/oauth2/authorization/${provider}`;
  };

  return (
    <div className="login-wrapper">
      <div className="login-card">
        <div className="login-header">
          <h2>AI Support Agent</h2>
          <p>{isLogin ? "Sign in to your account" : "Create a new account"}</p>
        </div>

        {error && (
          <p
            style={{
              color: "#f85149",
              marginBottom: "1rem",
              fontSize: "0.85rem",
            }}
          >
            {error}
          </p>
        )}

        <form onSubmit={handleSubmit} className="auth-form">
          {!isLogin && (
            <div className="form-group">
              <label>Full Name</label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
                placeholder="Alex"
                required={!isLogin}
              />
            </div>
          )}

          <div className="form-group">
            <label>Email Address</label>
            <input
              type="email"
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
              placeholder="alex@cognizant.com"
              required
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              value={formData.password}
              onChange={(e) =>
                setFormData({ ...formData, password: e.target.value })
              }
              placeholder="••••••••"
              required
            />
          </div>

          <button type="submit" className="submit-btn">
            {isLogin ? "Sign In" : "Register"}
          </button>
        </form>

        <div className="auth-toggle">
          <p>
            {isLogin ? "Don't have an account? " : "Already have an account? "}
            <button
              type="button"
              className="text-btn"
              onClick={() => setIsLogin(!isLogin)}
            >
              {isLogin ? "Sign up" : "Log in"}
            </button>
          </p>
        </div>

        <div className="divider">
          <span>or continue with</span>
        </div>

        <div className="login-actions">
          <button
            type="button"
            className="oauth-btn google-btn"
            onClick={() => handleOAuth("google")}
          >
            Google
          </button>
          <button
            type="button"
            className="oauth-btn github-btn"
            onClick={() => handleOAuth("github")}
          >
            GitHub
          </button>
        </div>
      </div>
    </div>
  );
}
