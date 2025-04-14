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
    
    newMessageDiv.textContent = message;
    messagesDiv.insertBefore(newMessageDiv, messagesDiv.firstChild); // 新しいメッセージを上に追加
}

