import { useEffect, useRef } from "react";
import ChatMessage from "./ChatMessage";

export default function ChatArea({ messages, isLoading }) {
  const endOfMessagesRef = useRef(null);

  // Auto-scroll to bottom when new messages arrive
  useEffect(() => {
    endOfMessagesRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages, isLoading]);

  return (
    <div className="chat-area">
      {messages.map((msg, index) => (
        <ChatMessage
          key={index}
          text={msg.text}
          sender={msg.sender}
          modelUsed={msg.modelUsed}
        />
      ))}
      {isLoading && <div className="loading-indicator">AI is typing...</div>}
      <div ref={endOfMessagesRef} />
    </div>
  );
}
