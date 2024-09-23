<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新菜單</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/validate.js/0.13.1/validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<style>
	.description {
		padding-left: 5px;
        color: #ADADAD;
        font-size: 0.9em;
    }
    .error {
    	padding-left: 5px;
        color: red;
        font-size: 0.8em;
    }
</style>
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<jsp:useBean id = "menu" scope = "request" class = "com.TogoOrder.bean.MenuBean"/>
	
    <div class="content" id="content">
        <h2>更新菜單資訊</h2>
        	
            <form action="/EEIT187-6/MenuController/update" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                <input type="hidden" name="foodId" value="${menu.foodId}" />
                <p>名稱: <input type="text" name="foodName" value="${menu.foodName}" /><span class="error" id="foodNameError"></span></p>
                <p>圖片: <input type="file" name="foodPicture" accept="image/*" /></p>
                <p>價格: <input type="text" name="foodPrice" value="${menu.foodPrice}" /><span class="error" id="foodPriceError"></span></p>
                <p>種類: <input type="text" name="foodKind" value="${menu.foodKind}" /><span class="error" id="foodKindError"></span></p>
                <p>庫存: <input type="text" name="foodStock" value="${menu.foodStock}" /><span class="error" id="foodStockError"></span></p>
                <p>狀態: <select name="foodStatus" style="width: 40px">
	           				<option value="1" ${menu.foodStatus == 1 ? 'selected' : ''}>1</option>
        					<option value="2" ${menu.foodStatus == 2 ? 'selected' : ''}>2</option>
               			</select><span class="description">(1=正常供應, 2=停售)</span><br><p>
                <p>說明: <textarea name="foodDescription" rows="4" cols="50"></textarea><br><p>
                <input type="submit" value="保存更新" />
        	</form>
    
    </div>
<script>

document.addEventListener("DOMContentLoaded", function() {
    const constraints = {
        foodName: {
            presence: {
                allowEmpty: false,
                message: "餐點名稱不得為空白"
            }
        },
        foodKind: {
            presence: {
                allowEmpty: false,
                message: "餐點種類不得為空白"
            }
        },
        foodPrice: {
            numericality: {
                onlyInteger: true,
                greaterThan: 0,
                message: "餐點價格必須為正整數"
            }
        },
        foodStock: {
            numericality: {
                onlyInteger: true,
                greaterThanOrEqualTo: 0,
                message: "庫存必須為非負整數"
            }
        }
    };

    function validateField(input, fieldName) {
        const value = input.value;
        const errors = validate({ [fieldName]: value }, { [fieldName]: constraints[fieldName] });
        const errorSpan = document.getElementById(fieldName+'Error');

        if (errors && errors[fieldName]) {
            // 使用 constraints 中的 message 來顯示錯誤信息
            if (constraints[fieldName].presence){
                errorSpan.textContent = constraints[fieldName].presence.message;
            } else {
	            errorSpan.textContent = constraints[fieldName].numericality.message;  	
            }
            
        } else {
            errorSpan.textContent = '';
        }
    }
    
	const foodName = document.querySelector('input[name="foodName"]');
    const foodKind = document.querySelector('input[name="foodKind"]');
    const foodPrice = document.querySelector('input[name="foodPrice"]');
    const foodStock = document.querySelector('input[name="foodStock"]');
	
    foodName.addEventListener('blur', function() {
        validateField(this, 'foodName');
    });

    foodKind.addEventListener('blur', function() {
        validateField(this, 'foodKind');
    });
    foodPrice.addEventListener('blur', function() {
        validateField(this, 'foodPrice');
    });
    foodStock.addEventListener('blur', function() {
        validateField(this, 'foodStock');
    });
    
    function validateForm() {
        let isValid = true;
        ['foodName', 'foodKind', 'foodPrice', 'foodStock'].forEach(field => {
            const input = document.querySelector('input[name='+field+']');
            validateField(input, field);
            
            if (document.getElementById(field+'Error').textContent !== '') {
                isValid = false;
            }
        });
        return isValid;
    }
    document.querySelector('form').addEventListener('submit', function(event) {
        event.preventDefault(); // 阻止表單默認提交

        if (validateForm()) {
            const formData = new FormData(this); // 使用FormData收集表單

            fetch(this.action, { //  用表單action屬性作為URL
                method: 'POST',
                body: formData
            }).then(data => {
                Swal.fire({
                    icon: 'success',
                    title: '新增成功',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    window.location.href = '/EEIT187-6/MenuController/getAll'; 
                });
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire({
                    icon: 'error',
                    title: '提交失敗',
                    text: error.message
                });
            });
        }
    });
    
});

</script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('order');
    });
</script>
</body>
</html>