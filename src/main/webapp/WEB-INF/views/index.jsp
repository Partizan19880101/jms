<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

    <!-- Access the bootstrap Css like this,
    Spring boot will handle the resource mapping automcatically -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>

    <!--
    <spring:url value="/css/main.css" var="springCss" />
    <link href="" rel="stylesheet" />
    -->
    <c:url value="/css/main.css" var="jstlCss"/>
    <link href="" rel="stylesheet"/>
    <style>
.hide{
display : none;
}


    </style>

</head>
<body>

<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills float-right">
                <li class="nav-item">
                    <a href="#" class="nav-link active">
                        Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">
                        About
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
            </ul>
        </nav>
        <h3 class="text-muted">Goods Order Form</h3>
    </div>
    <div class="jumbotron row">
        <div class="col-lg-6">
            <img src="${pageContext.request.contextPath}/images/random_books.png" style="width: 350px;" class="rounded"
                 alt="picture of random books"/>
        </div>
        <div class="col-lg-6">
            <h1 class="display-3">Goods store </h1>
            <p class="lead">Place goods orders here to warehouse for shipping</p>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="row marketing">
            <div class="col-lg-12">

                <div id="jmsMessageAlert" class="alert alert-success hide">
                    <strong>Success!</strong> <span id="message"></span>
                </div>
                <h4>Customer Id</h4>
                <div class="input-group">
                    <select class="form-control" id="customerId">
                        <c:forEach var="item" items="${customers}">
                            <option value="${item.customerId}">${item.customerId} - ${item.fullName}</option>
                        </c:forEach>
                    </select>
                    <!--input type="text" id="customerId" class="form-control" placeholder="Customer Account Number" aria-describedby="basic-addon2"-->
                </div>
                <br/>
                <h4>Goods</h4>
                <select class="form-control" id="goodsId">
                    <div class="input-group">
                        <c:forEach var="item" items="${goods}">
                            <option value="${item.goodsId}">${item.goodsId} - ${item.title}</option>
                            <h3>Please, enter quantity of goods</h3>
                        </c:forEach>
                        <input placeholder="Quantity" value="${item.quantity}">
                </select>
                <br/>
                <h4>Order Status</h4>
                <select class="form-control" id="orderStateId">
                    <option value="NEW">NEW</option>
                    <option value="UPDATE">UPDATE</option>
                    <option value="DELETE">CANCEL</option>
                </select>
                <br/>
                <button class="btn btn-primary" id="addToOrderId" onclick="processOrder();" type="button">Add to Order
                </button>

            </div>

        </div>
    </div>
    <footer class="footer">
        <p>&copy; Sample App</p>
    </footer>

</div>
<script>

function processOrder(){
var randomStoreId = Math.floor(Math.random() * 100000);
var randomOrderId = Math.floor(Math.random() * 100000);
var goodsId = $('#goodsId').val();
var customerId = $('#customerId').val();
var orderStateId = $('#orderStateId').val();
//alert(randomOrderId + " - " + customerId + " - " + goodsId);
jQuery.get('${pageContext.request.contextPath}/process/store/'+randomStoreId+'/order/'+randomOrderId+'/'+customerId+'/'+goodsId+'/'+orderStateId+"/",
function(data, status){
$("#jmsMessageAlert").removeClass('hide');
$("#jmsMessageAlert span").text(data);
});
}


</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>

</body>

</html>
