document.addEventListener('DOMContentLoaded', () => {
	// 首頁按鍵
	const homeButton = document.getElementById('homeButton');
	// 登入按鍵
	const loginButton = document.getElementById('loginButton');
	// 縣市選單
	const citySelect = document.getElementById('citySelect');
	// 鄉鎮選單
	const districtSelect = document.getElementById('districtSelect');
	// 地址
	const addressInput = document.getElementById('addressInput');
	// 表單
	const form = document.querySelector('form');

	// 填充縣市選單
	data.forEach((city, index) => {
		const option = document.createElement('option');
		option.value = index;
		option.textContent = city.name;
		citySelect.appendChild(option);
	});

	// 監聽縣市選單變化，更新地區選單
	citySelect.addEventListener('change', () => {
		districtSelect.innerHTML = '<option value="">地區</option>';
		const selectedCityIndex = citySelect.value;
		if (selectedCityIndex !== "") {
			const selectedCity = data[selectedCityIndex];
			selectedCity.districts.forEach(district => {
				const option = document.createElement('option');
				option.value = district.zip;
				option.textContent = district.name;
				districtSelect.appendChild(option);
			});
		}
	});

	// 收集表單數據
	form.addEventListener('submit', (event) => {
		event.preventDefault(); // 防止默認提交
		// 組合完整地址
		const city = citySelect.options[citySelect.selectedIndex].text;
		const district = districtSelect.options[districtSelect.selectedIndex].text;
		const detailedAddress = addressInput.value;
		const fullAddress = `${city}${district}${detailedAddress}`;

		// 更新 address 欄位的值
		document.getElementById('address').value = fullAddress;
		console.log("Detailed Address:", address);
		const formData = new FormData(form);


		// 發送 POST 請求到伺服器
		fetch('/EEIT187-6/register', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: new URLSearchParams(formData).toString()
		})
			.then(response => {
				// 檢查 HTTP 響應狀態碼
				if (!response.ok) {
					throw new Error('Network response was not ok.');
				}
				return response.json(); // 解析 JSON 響應
			})
			.then(result => {
				if (result.token) {
					// 存儲 JWT
					localStorage.setItem('jwtToken', result.token);
					console.log('Stored JWT Token:', localStorage.getItem('jwtToken'));
					// 重定向到首頁
					window.location.href = 'index.html';
				} else {
					// 顯示錯誤訊息
					alert(result.error || 'An error occurred.');
				}
			})
			.catch(error => {
				console.error('Error:', error);
			});
	});

	homeButton.addEventListener('click', () => {
		window.location.href = 'index.html';
	});

	loginButton.addEventListener('click', () => {
		window.location.href = 'login.html';
	});
});
