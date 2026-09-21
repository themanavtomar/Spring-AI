import { useState } from "react";
import { Send } from "lucide-react";

export default function MessageInput({ onSendMessage, isLoading }) {
  const [input, setInput] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (input.trim() && !isLoading) {
      onSendMessage(input);
      setInput("");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="input-form">
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Ask a support question..."
        disabled={isLoading}
      />
      <button type="submit" disabled={isLoading || !input.trim()}>
        <Send size={20} />
      </button>
    </form>
  );
}
