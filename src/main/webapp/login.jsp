<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="text"/>
<fmt:setLocale value="${language}" />
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
    </select>
</form>
<form action="login" method="post">
    <div>
        <h2><fmt:message key="login.title"/></h2>
        <div>
            <label>
                <input name="username" type="text" placeholder='<fmt:message key="login.placeholder.username"/>'/>
            </label>
        </div>
        <div>
            <label>
                <input name="password" type="password" placeholder='<fmt:message key="login.placeholder.password"/>'>
            </label>
        </div>
    </div>
    <div>
        <input type="submit" value='<fmt:message key="login.signIn"/>'>
        <a href="registration">
            <input type="button" value='<fmt:message key="login.signUp"/>'>
        </a>
    </div>
</form>
</body>
</html>
