<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
    <h2> player rejected your invitation please choose another one</h2>
	<h2>Online Users</h2>

	<c:if test="${not empty players}">

		<ul>
			<c:forEach var="playerEmail" items="${players}">
				<li>${playerEmail}</li>
			</c:forEach>
		</ul>

	</c:if>
	<form method="POST" action="invitePlayer">
	<div>
        <div>
            <h2>please email of a player you want to start game with</h2>
             
        </div>
    </div>
	<div>
    <table>
        <tr>
            <td>User email :</td>
            <td><input type="email" id="email" name="email"/></td>
        </tr>

        </table>
        <tr>
            <td colspan="2"><input type="submit"></td>
        </tr>
    </table>
	</div>
	</form>
</body>
</html>