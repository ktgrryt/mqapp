const historyHTML = `
    <div id="messages">
        <div id="messages-header">
            <h2>メッセージ送受信の履歴：</h2>
        </div>
        <div id="messages-list">
            <!-- メッセージがここに追加される -->
        </div>
    </div>
`;

function addMessageToHistory(message, type = 'default') {
    const messagesDiv = document.getElementById('messages-list');
    const newMessageDiv = document.createElement('div');
    newMessageDiv.classList.add('message');
    
    // メッセージの種類に応じてクラスを追加
    if (type === 'request') {
        newMessageDiv.classList.add('message-request');
    } else if (type === 'response') {
        newMessageDiv.classList.add('message-response');
    } else if (type === 'error') {
        newMessageDiv.classList.add('message-error'); // エラー用のクラス
    }
    
    // タイムスタンプを作成
    const timestamp = document.createElement('span');
    timestamp.classList.add('timestamp');
    const now = new Date();
    
    // ミリ秒まで表示するためのフォーマット作成
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    const milliseconds = now.getMilliseconds().toString().padStart(3, '0');
    
    // フォーマット例: HH:mm:ss.SSS
    timestamp.textContent = `${hours}:${minutes}:${seconds}.${milliseconds}`;
    
    // メッセージとタイムスタンプを追加
    newMessageDiv.textContent = message;
    newMessageDiv.appendChild(timestamp); // タイムスタンプをメッセージに追加
    
    messagesDiv.insertBefore(newMessageDiv, messagesDiv.firstChild); // 新しいメッセージを上に追加
}
