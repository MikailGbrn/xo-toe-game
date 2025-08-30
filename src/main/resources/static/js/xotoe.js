 function resetBoard() {
            location.reload();
        }

        function changeSize() {
            window.location.href = '/';
        }

        function makeMove(row, col) {
        fetch("/make-move", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ row: row, col: col })
        })
        .then(res => res.json())
        .then(data => {
            renderBoard(data.board);
            window.gameState = data;

            checkWinner();
        });
    }

    function checkWinner() {
        const statusDiv = document.getElementById("game-status");
        statusDiv.className = "status-banner"; // reset classes

        if (window.gameState && window.gameState.winner) {
            statusDiv.textContent = "Winner: " + window.gameState.winner;
            statusDiv.classList.add("winner");
            disableBoard();
        } else if (window.gameState && window.gameState.draw) {
            statusDiv.textContent = "It's a draw!";
            statusDiv.classList.add("draw");
            disableBoard();
        } else {
            statusDiv.textContent = "Next turn: " + window.gameState.nextPlayer;
        }
    }

    function disableBoard() {
        document.querySelectorAll("#board button").forEach(btn => {
            btn.disabled = true;
        });
    }

    function renderBoard(board) {
        const boardDiv = document.getElementById("board");
        boardDiv.innerHTML = ""; // clear old board

        board.forEach((row, r) => {
            const rowDiv = document.createElement("div");
            rowDiv.classList.add("row", `row-${r}`);

            row.forEach((cell, c) => {
                const cellDiv = document.createElement("div");
                cellDiv.classList.add("cell", `cell-${c}`);

                if (cell === " ") {
                    const btn = document.createElement("button");
                    btn.textContent = " ";
                    btn.onclick = () => makeMove(r, c);
                    cellDiv.appendChild(btn);
                } else {
                    const span = document.createElement("span");
                    span.textContent = cell;
                    cellDiv.appendChild(span);
                }

                rowDiv.appendChild(cellDiv);
            });

            boardDiv.appendChild(rowDiv);
        });
    }