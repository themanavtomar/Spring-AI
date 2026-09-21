import { useState } from "react";
import { LogOut } from "lucide-react";

export default function ProfileMenu({ user, onLogout }) {
  const [isOpen, setIsOpen] = useState(false);

  const handleLogoutClick = () => {
    setIsOpen(false);
    if (typeof onLogout === "function") {
      onLogout();
    }
  };

  return (
    <div className="profile-container">
      <img
        src={
          user?.avatar || "https://api.dicebear.com/7.x/avataaars/svg?seed=User"
        }
        alt="Profile"
        className="profile-logo"
        onClick={() => setIsOpen(!isOpen)}
        title="View Profile"
      />

      {isOpen && (
        <div className="profile-dropdown">
          <div className="profile-header">
            <img src={user?.avatar} alt="Avatar" className="profile-large" />
            <div className="profile-titles">
              <h3>{user?.name || "User"}</h3>
              <p className="email">{user?.email}</p>
            </div>
          </div>

          <div className="profile-details">
            <p>
              <strong>Age:</strong> {user?.age || 21}
            </p>
            <p>
              <strong>Profession:</strong>{" "}
              {user?.profession || "Java Backend Engineer"}
            </p>
            <p>
              <strong>Company:</strong> {user?.company || "Cognizant"}
            </p>
          </div>

          <div className="profile-actions">
            <button
              className="action-btn logout"
              onClick={handleLogoutClick}
              style={{ width: "100%", justifyContent: "center" }}
            >
              <LogOut size={16} /> Logout
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
