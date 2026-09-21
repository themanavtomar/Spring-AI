import ProfileMenu from "./ProfileMenu";

export default function Header({
  useLocalModel,
  setUseLocalModel,
  user,
  onLogout,
}) {
  return (
    <header className="app-header">
      <div className="header-brand">
        <h2>Support AI</h2>
      </div>
      <div className="header-controls">
        <button
          className={`model-toggle ${useLocalModel ? "local" : "cloud"}`}
          onClick={() => setUseLocalModel(!useLocalModel)}
        >
          {useLocalModel ? "DeepSeek (Local)" : "Gemini (Cloud)"}
        </button>
        {/* Ensure onLogout is explicitly passed here */}
        <ProfileMenu user={user} onLogout={onLogout} />
      </div>
    </header>
  );
}
