document.addEventListener('DOMContentLoaded', () => {
    const homeButton = document.getElementById('homeButton');

    // 監聽首頁按鈕的點擊事件
    homeButton.addEventListener('click', () => {
        window.location.href = 'index.html'; // 替換為您的首頁 URL
    });
    
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();
        
        var username = document.getElementById('account').value;
        var password = document.getElementById('password').value;
        
        fetch('login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'account': username,
                'password': password
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                // 存儲 JWT
                localStorage.setItem('jwtToken', data.token); // 确保这里使用 'jwtToken'
				console.log('Stored JWT Token:', localStorage.getItem('jwtToken')); // 打印存储的 JWT
                // 重定向到首頁
                window.location.href = 'index.html';
            } else {
                // 顯示錯誤訊息
                alert(data.error);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
});
