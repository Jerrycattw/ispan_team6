<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新外帶資訊</title>
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
	<jsp:useBean id = "togo" scope = "request" class = "com.TogoOrder.bean.TogoBean"/>
	
    <div class="content" id="content">
        <h2>更新外帶資訊</h2>
        	
            <form action="/EEIT187-6/TogoController/update" method="get" onsubmit="return validateForm()">
                <input type="hidden" name="togoId" value="${togo.togoId}" />
                <input type="hidden" name="togoCreateTime" value="${togo.formattedtogoCreateTime}" />
                <input type="hidden" name="restaurantId" value="${togo.restaurantId}" />
                <p>會員編號: <input type="text" name="memberId" value="${togo.memberId}" /><span class="error" id="memberIdError"></span></p>
				<p>姓名: <input type="text" name="tgName" value="${togo.tgName}" /><span class="error" id="tgNameError"></span></p>
                <p>電話: <input type="text" name="tgPhone" value="${togo.tgPhone}" /><span class="error" id="tgPhoneError"></span></p>
                <p>總金額: <input type="text" name="totalPrice" value="${togo.totalPrice}" readonly /></p>
                <p>租借單號: <input type="text" name="rentId" value="${togo.rentId}" /><span class="error" id="rentIdError"></span></p>
                <p>訂單狀態: <select name="togoStatus" style="width: 40px">
	           				<option value="1" ${togo.togoStatus == 1 ? 'selected' : ''}>1</option>
        					<option value="2" ${togo.togoStatus == 2 ? 'selected' : ''}>2</option>
        					<option value="3" ${togo.togoStatus == 3 ? 'selected' : ''}>3</option>
               			</select><span class="description">(1=未取餐, 2=已取餐, 3=取消訂單)</span><br><p>
                <p>訂單備註: <textarea name="togoMemo" rows="4" cols="50"></textarea><br><p>
                <input type="submit" value="保存更新" />
        	</form> 
    </div>
<script>

	document.addEventListener("DOMContentLoaded", function() {
        showSidebar('order');
        
		const constraints = {
			memberId: {
	            numericality: {
	            	onlyInteger: true,
	            	greaterThanOrEqualTo: 0,
	            	message: "會員編號格式錯誤，只能為數字" 
	            	}
	        },
	        tgName: {
	            presence: {
	            	allowEmpty: false,
	            	message: "姓名不能為空白" 
	            	}
	        },
	        tgPhone: {
	            format: {
	            	onlyInteger: true,
	                pattern: /^09\d{8}$/,
	                message: "電話號碼格式錯誤"
	            }
	        },
	        rentId: {
	            numericality: {
	            	onlyInteger: true,
	            	greaterThanOrEqualTo: 0,
	            	message: "租借單號格式錯誤，只能為數字" 
	            }
	        }
		};	
		function validateField(input, fieldName) {
			
	        const value = input.value;
	        const errors = validate({ [fieldName]: value }, { [fieldName]: constraints[fieldName] });
	        const errorSpan = document.getElementById(fieldName +'Error');
			
	        if (errors && errors[fieldName]) {
	            // 使用 constraints 中的 message 來顯示錯誤信息
	            if (constraints[fieldName].format){
	                errorSpan.textContent = constraints[fieldName].format.message;
	                
	            } else if (constraints[fieldName].presence) {
		            errorSpan.textContent = constraints[fieldName].presence.message;           	
	            } else{
	            	errorSpan.textContent = constraints[fieldName].numericality.message;
	            }
	        } else {
	            errorSpan.textContent = '';
	        }
	    }
		const memberId = document.querySelector('input[name="memberId"]');
		const tgName = document.querySelector('input[name="tgName"]');
		const tgPhone = document.querySelector('input[name="tgPhone"]');
		const rentId = document.querySelector('input[name="rentId"]');
		
		memberId.addEventListener('blur', function() {
	        validateField(this, 'memberId');
	    });
		tgName.addEventListener('blur', function() {
	        validateField(this, 'tgName');
	    });
		tgPhone.addEventListener('blur', function() {
	        validateField(this, 'tgPhone');
	    });
		rentId.addEventListener('blur', function() {
	        validateField(this, 'rentId');
	    });
		
		function validateForm() {
	        const isValid = true;
	        ['memberId', 'tgName', 'tgPhone', 'rentId'].forEach(field => {
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
	            const params = new URLSearchParams(new FormData(this)).toString(); // 將FormData轉換為URL

	            fetch(this.action + '?' + params, { // 用表單action屬性作為URL
	                method: 'GET'
	            }).then(response => {
	                if (!response.ok) throw new Error('Network response was not ok');
	                return response.text();
	            }).then(data => {
	                Swal.fire({
	                    icon: 'success',
	                    title: '新增成功',
	                    showConfirmButton: false,
	                    timer: 1500
	                }).then(() => {
	                    window.location.href = '/EEIT187-6/TogoController/getAll'; 
	                });
	            }).catch(error => {
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
</body>
</html>