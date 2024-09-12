document.addEventListener('DOMContentLoaded', () => {
	const authButton = document.getElementById('authButton');
	const memberCenterButton = document.getElementById('memberCenterButton');
	const registerButton = document.getElementById('registerButton');
	const welcomeMessage = document.getElementById('welcomeMessage') || document.createElement('p');
	welcomeMessage.id = 'welcomeMessage';
	document.querySelector('main').prepend(welcomeMessage);
	const jwtToken = localStorage.getItem('jwtToken'); // 確保讀取 'jwtToken'
	const isLoggedIn = !!jwtToken;

	function updateAuthButton() {
		if (isLoggedIn) {
			registerButton.style.display = 'none';

			authButton.textContent = '登出';
			authButton.classList.add('logout-button');
			authButton.classList.remove('login-button');
		} else {
			authButton.textContent = '登入';
			authButton.classList.add('login-button');
			authButton.classList.remove('logout-button');
		}
	}



	function base64UrlDecode(base64Url) {
		// Convert Base64Url to Base64
		let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');

		// Add padding if necessary
		const padding = '='.repeat((4 - (base64.length % 4)) % 4);
		base64 += padding;

		// Decode Base64
		const decodedBytes = new Uint8Array(atob(base64).split('').map(char => char.charCodeAt(0)));
		const decoder = new TextDecoder('utf-8');
		return decoder.decode(decodedBytes);
	}

	function displayWelcomeMessage() {
		if (isLoggedIn) {
			try {
				if (!jwtToken || jwtToken.split('.').length !== 3) {
					throw new Error('Invalid JWT format');
				}

				const payload = jwtToken.split('.')[1]; // 提取 payload 部分
				console.log('Base64Url Payload:', payload); // 打印 Base64Url 编码的 payload 部分

				const decodedPayload = base64UrlDecode(payload); // 解碼 Base64Url
				console.log('Decoded Payload:', decodedPayload); 

				const parsedPayload = JSON.parse(decodedPayload); // 解析 JSON
				console.log('Parsed Payload:', parsedPayload); 

				const memberName = parsedPayload.memberName || '訪客';
				welcomeMessage.textContent = `歡迎, ${memberName}`
			} catch (e) {
				console.error('Error parsing JWT:', e);
				welcomeMessage.textContent = '歡迎, 訪客';
			}
		} else {
			welcomeMessage.textContent = '';
		}
	}

	updateAuthButton();
	displayWelcomeMessage();

	memberCenterButton.addEventListener('click', () => {
		window.location.href = 'memberCenter.html';
	});

	registerButton.addEventListener('click', () => {
		window.location.href = 'register.html';
	});

	authButton.addEventListener('click', () => {
		if (isLoggedIn) {
			// 執行操作
			localStorage.removeItem('jwtToken');
			window.location.href = 'login.html';
		} else {
			// 執行登入操作 (重定向到登入页面)
			window.location.href = 'login.html';
		}
	});
});
