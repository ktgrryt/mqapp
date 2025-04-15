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
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTPエラー: ${response.status}`);
        }
        return response.text();
    })
    .then(data => {
        // サーバーから返されたデータをパース（例: ヘッダー情報を含むJSON形式を仮定）
        const parsedData = JSON.parse(data);
    
        // メッセージ履歴に追加（ヘッダー情報を渡す）
        addMessageToHistory(`受信: ${parsedData.message}`, 'response', {
            messageId: parsedData.messageId,
            timestamp: parsedData.timestamp,
            correlationId: parsedData.correlationId
        });
    })
    
    .catch(error => {
        // エラー表示を赤色に
        addMessageToHistory("受信エラー: " + error.message, 'error');
    });
    
}
