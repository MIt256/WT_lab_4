<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="text"/>
<fmt:setLocale value="${language}" />
<html lang="${language}">
<head>
    <title><fmt:message key="rooms.title"/></title>
</head>
<body>
<script>

    function send(params) {
        const http = new XMLHttpRequest();
        const url = 'rooms';
        http.open('POST', url, true);
        http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        http.onreadystatechange = () => {
            document.location.reload();
        };
        http.send(params);
    }

    function rent(roomId) {
        send('rent=' + roomId)
    }

    function unrent(roomId) {
        send('unrent=' + roomId)
    }

    function logout() {
        send('logout');
    }
</script>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="nl" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<table>
    <tr>
        <th><fmt:message key="rooms.rent.title"/></th>
        <th><fmt:message key="rooms.label.title"/></th>
        <th><fmt:message key="rooms.hasKitchen.title"/></th>
        <th><fmt:message key="rooms.hasBath.title"/></th>
    </tr>
    <jsp:useBean id="roomsList" scope="request" type="java.util.List<by.varyvoda.matvey.servlethotel.entity.hotel.Room>"/>
    <jsp:useBean id="username" scope="request" type="java.lang.String"/>
    <c:forEach items="${roomsList}" var="room">
        <tr>
            <td>
                    <с:choose>
                        <c:when test="${room.reservation == null}">
                            <button type="button" onclick="rent(${room.id})"><fmt:message key="rooms.rent"/></button>
                        </c:when>
                        <c:when test="${room.reservation != null && room.reservation.user.username.equals(username)}">
                            <button type="button" onclick="unrent(${room.id})"><fmt:message key="rooms.unrent"/></button>
                        </c:when>
                        <c:when test="${room.reservation != null && !room.reservation.user.username.equals(username)}">
                            <fmt:message key="rooms.busy"/>
                        </c:when>
                    </с:choose>
            </td>
            <td>${room.label}</td>
            <td>${room.hasKitchen}</td>
            <td>${room.hasBath}</td>
        </tr>
    </c:forEach>
    <button onclick="logout()"><fmt:message key="rooms.logout"/></button>
</table>
</body>
</html>
