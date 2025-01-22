import React, { useState } from "react";
import { validateBlockchain } from "../api";

const ValidateBlockchain = () => {
  const [validation, setValidation] = useState("");

  const handleValidate = async () => {
    const result = await validateBlockchain();
    setValidation(result);
  };

  return (
    <div>
      <h2>Validate Blockchain</h2>
      <button onClick={handleValidate}>Validate</button>
      {validation && <p>{validation}</p>}
    </div>
  );
};

export default ValidateBlockchain;