import axios from "axios";

const api = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export const registerUser = async (name, email, password) => {
  const response = await api.post("/auth/register", {
    name,
    email,
    password,
  });

  return response.data;
};

export const loginUser = async (email, password) => {
  const response = await api.post("/auth/login", {
    email,
    password,
  });

  return response.data;
};

export const sendMessageToBackend = async (prompt, useLocalModel, email) => {
  const endpoint = useLocalModel ? "/support/local" : "/support/cloud";

  try {
    const response = await api.post(endpoint, {
      prompt,
      email,
    });

    return response.data;
  } catch (error) {
    console.error("API Error:", error);

    return (
      error.response?.data?.message ||
      "Sorry, I encountered an error communicating with the backend."
    );
  }
};

export default api;
