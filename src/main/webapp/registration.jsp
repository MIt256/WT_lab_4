<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="text"/>
<fmt:setLocale value="${language}" />
<!doctype html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<form action="registration" method="post">
    <div>
        <h2><fmt:message key="registration.title"/></h2>
        <div>
            <label>
                <input name="username" type="text" placeholder='<fmt:message key="registration.placeholder.username"/>'>
            </label>
        </div>
        <div>
            <label>
                <input name="password" type="password" placeholder='<fmt:message key="registration.placeholder.password"/>'>
            </label>
        </div>
    </div>
    <div>
        <input type="submit" value='<fmt:message key="registration.signUp"/>'>
        <a href="login">
            <input type="button" value='<fmt:message key="registration.signIn"/>'>
        </a>
    </div>
</form>
</body>
</html>