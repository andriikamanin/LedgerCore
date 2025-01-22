import React, { useState, useEffect } from "react";

function Home() {
    const [userAddress, setUserAddress] = useState("");
    const [userBalance, setUserBalance] = useState("");
    const [checkBalanceResult, setCheckBalanceResult] = useState("");
    const [transactions, setTransactions] = useState([]);
    const [blockchain, setBlockchain] = useState("");
    const [validateResult, setValidateResult] = useState("");
    const [transaction, setTransaction] = useState({ sender: "", receiver: "", amount: "" });
    const [transactionResult, setTransactionResult] = useState("");

    // Create User
    const handleCreateUser = async () => {
        const data = { address: userAddress };
        const res = await fetch("http://localhost:8080/api/createUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
        const result = await res.text();
        alert(result);
    };

    // Check Balance
    const handleCheckBalance = async () => {
        const res = await fetch(`http://localhost:8080/api/balance?user=${userBalance}`);
        const result = await res.text();
        setCheckBalanceResult(result);
    };

    // Create Transaction
    const handleCreateTransaction = async () => {
        const res = await fetch("http://localhost:8080/api/transaction", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(transaction),
        });
        const result = await res.text();
        setTransactionResult(result);
    };

    // View Transactions
    const fetchTransactions = async () => {
        const res = await fetch("http://localhost:8080/api/transactions");
        const result = await res.json();
        setTransactions(result);
    };

    // View Blockchain
    const fetchBlockchain = async () => {
        const res = await fetch("http://localhost:8080/api/chain");
        const result = await res.text();
        setBlockchain(result);
    };

    // Validate Blockchain
    const validateBlockchain = async () => {
        const res = await fetch("http://localhost:8080/api/validate");
        const result = await res.text();
        setValidateResult(result);
    };

    useEffect(() => {
        fetchTransactions();
        fetchBlockchain();
    }, []);

    return (
        <div className="container">
            <h1>Blockchain Dashboard</h1>

            {/* Create User */}
            <section className="card">
                <h2>Create User</h2>
                <input
                    type="text"
                    placeholder="Enter User Address"
                    value={userAddress}
                    onChange={(e) => setUserAddress(e.target.value)}
                />
                <button onClick={handleCreateUser}>Create User</button>
            </section>

            {/* Check Balance */}
            <section className="card">
                <h2>Check Balance</h2>
                <input
                    type="text"
                    placeholder="Enter User"
                    value={userBalance}
                    onChange={(e) => setUserBalance(e.target.value)}
                />
                <button onClick={handleCheckBalance}>Check Balance</button>
                {checkBalanceResult && <p>Balance: {checkBalanceResult}</p>}
            </section>

            {/* Create Transaction */}
            <section className="card">
                <h2>Create Transaction</h2>
                <input
                    type="text"
                    placeholder="Sender"
                    value={transaction.sender}
                    onChange={(e) => setTransaction({ ...transaction, sender: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Receiver"
                    value={transaction.receiver}
                    onChange={(e) => setTransaction({ ...transaction, receiver: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="Amount"
                    value={transaction.amount}
                    onChange={(e) => setTransaction({ ...transaction, amount: e.target.value })}
                />
                <button onClick={handleCreateTransaction}>Submit Transaction</button>
                {transactionResult && <p>{transactionResult}</p>}
            </section>

            {/* View Transactions */}
            <section className="card">
                <h2>View Transactions</h2>
                <ul>
                    {transactions.map((transaction, index) => (
                        <li key={index}>
                            <strong>Sender:</strong> {transaction.sender}, <strong>Receiver:</strong> {transaction.receiver}, <strong>Amount:</strong> {transaction.amount}
                        </li>
                    ))}
                </ul>
            </section>

            {/* View Blockchain */}
            <section className="card">
                <h2>View Blockchain</h2>
                <pre>{blockchain}</pre>
            </section>

            {/* Validate Blockchain */}
            <section className="card">
                <h2>Validate Blockchain</h2>
                <button onClick={validateBlockchain}>Validate</button>
                {validateResult && <p>{validateResult}</p>}
            </section>
        </div>
    );
}

export default Home;