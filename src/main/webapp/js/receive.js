const receiveFormHTML = `
    <form id="recvForm">
        <h2>メッセージ受信</h2>
        <label for="queueSelectRecv">受信するキューを選択:</label>
        <select id="queueSelectRecv" required>
            <option value="/mqapp/api/recv">ローカルキュー（DEV.QUEUE.1）</option>
        </select>
        <button type="button" onclick="receiveMessage()">受信</button>
    </form>
`;


function receiveMessage() {
    const queueEndpoint = document.getElementById('queueSelectRecv').value;

    // リクエスト実行メッセージを履歴に追加 (グレー表示)
    addMessageToHistory("メッセージの受信処理を開始しました", 'request');

    fetch(queueEndpoint)
    .then(response => response.text())
    .then(data => {
        // 受信結果を履歴に追加 (色付き表示)
        addMessageToHistory("受信: " + data, 'response');
    })
    .catch(error => {
        // エラー表示も色付きにして区別可能に
        addMessageToHistory("受信エラー: " + error.message, 'response');
    });
}
