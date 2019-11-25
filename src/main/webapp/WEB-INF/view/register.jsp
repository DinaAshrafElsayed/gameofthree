<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<body>
<html lang="en">
<form method="POST" action="register">
	<div>
        <div>
            <h1>Welcome to game of three</h1>
            <h2>please enter your email to register</h2>
             
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