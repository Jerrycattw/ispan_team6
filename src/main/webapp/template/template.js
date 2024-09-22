function showSidebar(menu) {
            const sidebar = document.getElementById('sidebar');
            let menuItems = '';
            
            switch(menu) {
                case 'member':
                    menuItems = `
						<a href="/EEIT187-6/Admin/addAdmin.jsp">新增管理員</a>
						<a href="/EEIT187-6/Admin/AdminController/findAllAdmins">查詢管理員</a>
	                    <a href="/EEIT187-6/Admin/searchMembers.jsp">查詢會員資料</a>
                    `;
						//<a href="/EEIT187-6/Admin/AdminController/allAdminsAndPermissions">修改管理員權限</a>
                    break;
                case 'store':
                    menuItems = `
					<a href="/EEIT187-6/Product/AddProduct.html">新增商品</a>
					<a href="/EEIT187-6/ProductController/SearchAllProduct">查詢所有商品</a>
					<a href="/EEIT187-6/ShoppingController/ShowAddOrder">新增訂單</a>
					<a href="/EEIT187-6/ShoppingController/SearchAllShopping">查詢所有訂單</a>
                    `;
                    break;
                case 'order':
                    menuItems = `
						<a href="/EEIT187-6/MenuController/getAllMenu">菜單管理</a>
						<a href="/EEIT187-6/Togo/GetMenu.html">菜單查詢</a>
						<a href="/EEIT187-6/TogoController/getAll">訂單管理</a>
						<a href="/EEIT187-6/Togo/GetTogo.html">訂單查詢</a>
                    `;
                    break;
                case 'rental':
                    menuItems = `
						<a href="/EEIT187-6/Tableware/getAll">環保用具管理</a>
						<a href="/EEIT187-6/TablewareStock/getAll">環保用具庫存管理</a>
						<a href="/EEIT187-6/Rent/getAll">租借訂單管理</a>
						<a href="/EEIT187-6/RentItem/getAll">租借訂單項目管理</a>
                    `;
                    break;
                case 'reservation':
                    menuItems = `
                         <a href="/EEIT187-6/reserve/AddRestaurant">新增餐廳</a>
                         <a href="/EEIT187-6/reserve/GetListRestaurants">查詢餐廳</a>
                         <a href="/EEIT187-6/Restaurant/getAll">查詢所有餐廳</a>
                         <a href="/EEIT187-6/TableType/getAllType">桌位種類管理</a>
                         <a href="/EEIT187-6/Reserve/getAllRestaurantName">新增訂位</a>
                         <a href="/EEIT187-6/Reserve/listName">查詢訂位</a>
                         <a href="/EEIT187-6/Reserve/getAll">查詢所有訂位</a>
                    `;
                    break;
                case 'points':
                    menuItems = `
						<a href="/EEIT187-6/Point/GetPointSet">點數設定</a>
	                    <a href="/EEIT187-6/Point/GetAllPoints">點數管理</a>
	                    <a href="/EEIT187-6/coupon/Home.html">優惠券管理</a>
                    `;
                    break;
            }

            sidebar.innerHTML = menuItems;
        }