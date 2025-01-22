import React, { useState } from "react";
import { createUser } from "../api";

const CreateUser = () => {
  const [address, setAddress] = useState("");
  const [response, setResponse] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await createUser(address);
    setResponse(result);
  };

  return (
    <div>
      <h2>Create User</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          placeholder="Enter user address"
        />
        <button type="submit">Create</button>
      </form>
      {response && <p>Response: {response}</p>}
    </div>
  );
};

export default CreateUser;