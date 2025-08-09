<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculator</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>
    <!-- Beautiful Heading -->
    <h1 class="heading">Simple Calculator Using JAVA</h1>

    <!-- Calculator Form -->
    <form action="calculate" method="post" class="calculator">
        <input type="text" name="display" readonly
               value="<%= request.getAttribute("result") != null ? request.getAttribute("result") : "" %>" />

        <div class="buttons">
            <button type="submit" name="btn" value="+">+</button>
            <button type="submit" name="btn" value="-">-</button>
            <button type="submit" name="btn" value="*">*</button>
            <button type="submit" name="btn" value="/">/</button>

            <button type="submit" name="btn" value="7">7</button>
            <button type="submit" name="btn" value="8">8</button>
            <button type="submit" name="btn" value="9">9</button>

            <button type="submit" name="btn" value="4">4</button>
            <button type="submit" name="btn" value="5">5</button>
            <button type="submit" name="btn" value="6">6</button>

            <button type="submit" name="btn" value="1">1</button>
            <button type="submit" name="btn" value="2">2</button>
            <button type="submit" name="btn" value="3">3</button>

            <button type="submit" name="btn" value="0">0</button>
            <button type="submit" name="btn" value="CE">CE</button>
            <button type="submit" name="btn" value="=">=</button>
        </div>
    </form>
</body>
</html>
