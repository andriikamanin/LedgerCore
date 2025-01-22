import React, { useState } from "react";
import { getBalance } from "../api";

const Balance = () => {
  const [user, setUser] = useState("");
  const [balance, setBalance] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await getBalance(user);
    setBalance(result);
  };

  return (
    <div>
      <h2>Check Balance</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={user}
          onChange={(e) => setUser(e.target.value)}
          placeholder="Enter user address"
        />
        <button type="submit">Check</button>
      </form>
      {balance && <p>Balance: {balance}</p>}
    </div>
  );
};

export default Balance;