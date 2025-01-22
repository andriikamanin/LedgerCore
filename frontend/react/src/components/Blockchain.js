import React, { useEffect, useState } from "react";
import { getBlockchain } from "../api";

const Blockchain = () => {
  const [blockchain, setBlockchain] = useState("");

  useEffect(() => {
    const fetchBlockchain = async () => {
      const result = await getBlockchain();
      setBlockchain(result);
    };

    fetchBlockchain();
  }, []);

  return (
    <div>
      <h2>Blockchain</h2>
      <pre>{blockchain}</pre>
    </div>
  );
};

export default Blockchain;