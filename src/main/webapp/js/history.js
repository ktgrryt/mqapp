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


function addMessageToHistory(message, type = 'default', headers = null) {
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
    
    // メッセージテキストを追加
    const messageText = document.createElement('span');
    messageText.textContent = message;
    newMessageDiv.appendChild(messageText);

    // ヘッダー情報を追加（初期状態は非表示）
    if (headers) {
        const headerDiv = document.createElement('div');
        headerDiv.classList.add('message-headers');
        headerDiv.style.display = 'none'; // 初期状態で非表示に設定

        // ヘッダー情報をフォーマットして追加
        headerDiv.innerHTML = `
            <p><strong>MessageID:</strong> ${headers.messageId}</p>
            <p><strong>Timestamp:</strong> ${headers.timestamp}</p>
            <p><strong>CorrelationID:</strong> ${headers.correlationId}</p>
        `;
        newMessageDiv.appendChild(headerDiv);

        // メッセージをクリックするとヘッダー情報を表示/非表示に切り替える
        newMessageDiv.addEventListener('click', () => {
            headerDiv.style.display = headerDiv.style.display === 'none' ? 'block' : 'none';
        });
    }
    
    // タイムスタンプを追加
    newMessageDiv.appendChild(timestamp);
    
    messagesDiv.insertBefore(newMessageDiv, messagesDiv.firstChild); // 新しいメッセージを上に追加
}
