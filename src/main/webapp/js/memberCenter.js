document.addEventListener('DOMContentLoaded', () => {
    const homeButton = document.getElementById('homeButton');
    const queryProfileButton = document.getElementById('queryProfileButton');
    const profileArea = document.getElementById('profileArea');
    const updatePasswordButton = document.getElementById('updatePasswordButton');
    const updatePasswordArea = document.getElementById('updatePasswordArea');
    const authButton = document.getElementById('authButton');
    const jwtToken = localStorage.getItem('jwtToken'); // 確保讀取 'jwtToken'
    const isLoggedIn = !!jwtToken;
	
	console.log(jwtToken)

    // 更新登入登出按鈕顯示
    function updateAuthButton() {
        if (isLoggedIn) {
            authButton.textContent = '登出';
            authButton.classList.add('logout-button');
            authButton.classList.remove('login-button');
        } else {
            authButton.textContent = '登入';
            authButton.classList.add('login-button');
            authButton.classList.remove('logout-button');
        }
    }

    // 點擊首頁按鈕事件
    homeButton.addEventListener('click', () => {
        window.location.href = 'index.html'; // 替換為您的首頁 URL
    });

    // 處理點擊“變更密碼”按鈕事件
    updatePasswordButton.addEventListener('click', () => {
		profileArea.classList.remove('show');
		updatePasswordArea.classList.add('show');

        // 顯示更新密碼表單
        updatePasswordArea.innerHTML = `
            <form id="updatePasswordForm">
                <label for="oldPassword">請輸入舊密碼:</label><br>
                <input type="password" id="oldPassword" name="oldPassword" required><br><br>
                
                <label for="newPassword">請輸入新密碼:</label><br>
                <input type="password" id="newPassword" name="newPassword" required><br><br>
                
                <label for="confirmPassword">再次輸入新密碼:</label><br>
                <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>
                
                <button type="submit">更新密碼</button>
            </form>
        `;

        // 處理密碼更新表單提交事件
        const updatePasswordForm = document.getElementById('updatePasswordForm');
        updatePasswordForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const oldPassword = document.getElementById('oldPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (newPassword !== confirmPassword) {
                alert('新密碼不一致，請重新輸入。');
                return;
            }

            // 發送更新密碼請求
            fetch('/EEIT187-6/update-password', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    oldPassword: oldPassword,
                    newPassword: newPassword
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    alert('密碼更新成功。');
                    updatePasswordArea.innerHTML = ''; // 清空更新密碼區域
                } else {
                    alert('密碼更新失敗，請檢查舊密碼是否正確。');
                }
            })
            .catch(error => {
                console.error('更新密碼時出錯:', error);
                alert('密碼更新失敗，請稍後再試。');
            });
        });
    });

    // 設置登入登出按鈕點擊事件
    function setupAuthButton() {
        authButton.addEventListener('click', () => {
            if (isLoggedIn) {
                // 清除 JWT token，實現登出
                localStorage.removeItem('jwtToken');
                window.location.href = 'login.html';
            } else {
                window.location.href = 'login.html';
            }
        });
    }

    // 點擊“查詢資料”按鈕事件
    queryProfileButton.addEventListener('click', () => {
		// 顯示“查詢會員資料”區域，隱藏“變更密碼”區域
		updatePasswordArea.classList.remove('show');
		profileArea.classList.add('show');

        // 查詢用戶資訊
        queryUserProfile();
    });

    // 查詢會員資訊
    function queryUserProfile() {
        if (!isLoggedIn) {
            alert('請先登入。');
            window.location.href = 'login.html'; // 重定向到登入頁面
            return;
        }
        
        fetch('/EEIT187-6/member-info', {
            method: 'GET',
			headers: {
			    'Authorization': `Bearer ${jwtToken}`,
			    'Content-Type': 'application/json'
			},
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
			if (data.memberName && data.email && data.address && data.phone && data.registerDate) {
			    displayUserProfile(data); // 顯示用戶資料
			} else {
			    console.error('返回的資料不包含預期的屬性:', data);
			    profileArea.innerHTML = '<p>無法顯示用戶資料，請稍後再試。</p>';
			} // 顯示用戶資料
        })
        .catch(error => {
			console.log('JWT Token:', jwtToken);
            console.error('查詢用戶資料時出錯:', error);
            profileArea.innerHTML = '<p>無法顯示用戶資料，請稍後再試。</p>';
        });
    }

    // 顯示會員資料
    function displayUserProfile(data) {
        if (!data || !data.memberId) {
            profileArea.innerHTML = '<p>資料無效。</p>';
            return;
        }
        console.log(data.registerDate);
        profileArea.innerHTML = `
            <h2>會員資訊</h2>
            <p><strong>姓名:</strong> ${data.memberName}</p>
            <p><strong>電子郵件:</strong> ${data.email}</p>
            <p><strong>地址:</strong> ${data.address}</p>
            <p><strong>電話:</strong> ${data.phone}</p>
            <p><strong>註冊日期:</strong> ${data.registerDate}</p>
        `;
    }

    // 初始化函數
    function init() {
        updateAuthButton();
        setupAuthButton();
    }
    init();
});
