<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新訂單項目資訊</title>
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
<jsp:include page="../HomePage.html"></jsp:include>
	<jsp:useBean id = "togoItem" scope = "request" class = "com.TogoOrder.bean.TogoItemBean"/>
	
    <div class="content" id="content">
        <h2>更新訂單項目資訊</h2>
        	
            <form action="/EEIT187-6/TogoItemController/update" method="get" onsubmit="return validateForm()">
                <input type="hidden" name="togoId" value="${togoItem.togoId}" />  
                <p>餐點編號: <input type="text" name="foodId" value="${togoItem.foodId}" readonly/></p>
				<p>餐點名稱: <input type="text" name="foodName" value="${togoItem.foodName}" readonly/></p>
                <p>單價: <input type="text" name="foodPrice" value="${togoItem.foodPrice}" readonly/></p>
                <p>數量: <input type="text" name="amount" value="${togoItem.amount}" /><span class="error" id="amountError"></span></p>
                <input type="submit" value="保存更新" />
        	</form>  
    </div>
<script>
	document.addEventListener("DOMContentLoaded", function() {
		const constraints = {	
		        amount: {
		            numericality: {
		            	onlyInteger: true,
		            	greaterThan: 0,
		            	message: "數量格式錯誤，只能為數字" 
		            }
		        },
		    };
		
		function validateField(input, fieldName) {
	        const value = input.value;
	        const errors = validate({ [fieldName]: value }, { [fieldName]: constraints[fieldName] });
	        const errorSpan = document.getElementById(fieldName +'Error');
	          
	        if (errors && errors[fieldName]) {
	            // 使用 constraints 中的 message 來顯示錯誤信息
	            if (constraints[fieldName].numericality){
	            	errorSpan.textContent = constraints[fieldName].numericality.message;
	            }
	        } else {
	            errorSpan.textContent = '';
	        }
	    }
		
		const amount = document.querySelector('input[name="amount"]');
		
		amount.addEventListener('blur', function() {
	        validateField(this, 'amount');
	    });
		
		function validateForm() {
	        let isValid = true;
	        ['amount'].forEach(field => {
	            const input = document.querySelector('input[name='+field+']');
	            validateField(input, field);
	            if (document.getElementById(field +'Error').textContent !== '') {
	                isValid = false;
	            }
	        });
	        return isValid;
	    }
		
		// 更新表單
        document.querySelector('form').addEventListener('submit', function(event) {
            event.preventDefault(); // 阻止表單默認提交

            if (validateForm()) {
                const params = new URLSearchParams(new FormData(this)).toString();
                
                fetch(this.action + '?' + params, {
                    method: 'GET'
                })
                .then(response => {
                    if (!response.ok) throw new Error('Network response was not ok');
                    return response.text();
                })
                .then(() => {
                    Swal.fire({
                        icon: 'success',
                        title: '成功更新!',
                        text: '訂單明細已經成功更新。',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        window.location.href = '/EEIT187-6/TogoItemController/get?' + params;
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