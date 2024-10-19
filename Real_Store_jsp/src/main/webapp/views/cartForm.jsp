<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Cart Form</title>
    <style>
       body {
           font-family: Arial, sans-serif;
           margin: 20px;
       }
       table {
           width: 100%;
           border-collapse: collapse;
           margin-top: 20px;
       }
       th, td {
           border: 1px solid #ddd;
           padding: 8px;
           text-align: left;
           font-size: 16px;
       }
       th {
           background-color: #f2f2f2;
       }
       tr:nth-child(even) {
           background-color: #f9f9f9;
       }
       tr:hover {
           background-color: #ddd;
       }
       .remove-btn {
           background-color: #ff4d4d;
           color: white;
           padding: 5px 10px;
           border: none;
           cursor: pointer;
       }
    </style>
</head>
<body>
    <h1>Cart Form</h1>

    <table id="productTable">
        <thead>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Stock Quantity</th>
                <th>Confirm to Cart</th>
                <th>Remove</th>
            </tr>
        </thead>
        <tbody>
            <!-- Loop through the productList to render products in the cart -->
            <c:forEach var="product" items="${selectedProductList}">
                <tr>
                    <td>${product.productId}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>
                        <!-- Stock Quantity Input -->
                        <input type="number" id="stockQuantity_${product.productId}" name="stockQuantity_${product.productId}" min="1" value="1" required>
                    </td>
                    <td>
                        <!-- Form to add product to cart -->
                        <form action="checkQuantity" onsubmit="return updateStockQuantity(${product.productId}, this)">
                            <!-- Hidden fields for the current product and user details -->

                            <input type="hidden" name="userId" value="${userId}">
                            <input type="hidden" name="stockQuantity" id="hiddenStockQuantity_${product.productId}">
                            <input type="hidden" name="productId" value="${product.productId}">

                            <!-- Pass the selected product IDs -->
                            <c:forEach var="selectedProduct" items="${productIds}">
                                <input type="hidden" name="productIds" value="${selectedProduct}">
                            </c:forEach>

                            <c:forEach var="entry" items="${productPriceMap1.entrySet()}">
                                <input type="hidden" name="productPriceMap1[${entry.key}]" value="${entry.value}">
                            </c:forEach>


                            <!-- Disable confirm button if this productId matches the confirmed one -->
                            <button type="submit" <c:if test="${product.productId == productId}">disabled</c:if>>Confirm</button>
                        </form>
                    </td>

                    <td>
                       <form action="removeCartProduct">
                            <c:forEach var="selectedProduct" items="${productList}">
                                <input type="hidden" name="selectedProducts" value="${selectedProduct.productId}">
                            </c:forEach>
                            <input type="hidden" name="productId" value="${product.productId}">
                            <input type="hidden" name="userId" value="${userId}">
                            <button type="submit" <c:if test="${product.productId == productId}">disabled</c:if>>Remove</button>
                       </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>



    <script>
        // JavaScript function to update stock quantity in hidden field before submitting form
        function updateStockQuantity(productId, form) {
            var enteredQuantity = document.getElementById('stockQuantity_' + productId).value;
            document.getElementById('hiddenStockQuantity_' + productId).value = enteredQuantity;

            // Mocking the checkQuantity response as true (you would replace this with actual server-side logic)
            var checkQuantity = true;

            if (checkQuantity) {
                // Disable the confirm button and the remove button for the corresponding product
                form.querySelector('button[type="submit"]').disabled = true;  // Disable Confirm button
                form.closest('tr').querySelector('.remove-btn').disabled = true;  // Disable Remove button
            }

            return checkQuantity;  // Proceed with the form submission if true
        }

        // JavaScript function to remove product row from the table
        function removeProduct(row) {
            var table = document.getElementById('productTable');
            table.deleteRow(row.rowIndex);
        }
    </script>

</body>
</html>
